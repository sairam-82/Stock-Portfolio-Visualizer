
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {



  private RestTemplate restTemplate;
  // private StockQuotesService stockQuotesService;
  
  
  private StockQuotesService stockQuotesService;
  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  public PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    this.stockQuotesService = stockQuotesService;
    // this.restTemplate=restTemplate;
  }
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF




  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) throws JsonProcessingException,StockQuoteServiceException
      {
        List<Candle> list;
      try {
        
        list=stockQuotesService.getStockQuote(symbol, from, to);
        return list;
      } catch (JsonProcessingException e) {
        //TODO: handle exception
        throw new StockQuoteServiceException(e.getMessage());
      }
      catch(StockQuoteServiceException e){
        throw new StockQuoteServiceException(e.getMessage(),e);
      }

   //  Candle[] listOfCandles= restTemplate.getForObject(buildUri(symbol, from, to),TiingoCandle[].class);
    //  return Arrays.asList(listOfCandles);
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String STARTDATE= startDate.toString();
    String ENDDATE= endDate.toString();
    String SYMBOL=symbol;
    String APIKEY=  "eb53fb80c7abfdf2b0482e9f8f072b9cbab71ce5";
    String uriTemplate = "https://api.tiingo.com/tiingo/daily/"+SYMBOL+"/prices?startDate="+STARTDATE+"&endDate="+ENDDATE+"&token="+APIKEY;
    System.out.println(uriTemplate);
      return uriTemplate;
  }


  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate)  {
    // TODO Auto-generated method stub
    
      // List<Candle> listCandles=fetchCandles(trade, endDate, getToken());
      List<AnnualizedReturn> listOfAnnualizedReturns= new ArrayList<>();

      for(PortfolioTrade trade:portfolioTrades){
        try{
          List<Candle> listOfQuotes= getStockQuote(trade.getSymbol(), trade.getPurchaseDate(), endDate);
          double buyPrice= listOfQuotes.get(0).getOpen();
          double sellPrice= listOfQuotes.get(listOfQuotes.size()-1).getClose();
          double totalReturns=(sellPrice-buyPrice)/buyPrice;
          double total_num_years= ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate)/365.2462;
          double inverseOfYear= (1.00 / total_num_years );
          double annualized_returns = Math.pow((1.00 + totalReturns),inverseOfYear) - 1.00;
          listOfAnnualizedReturns.add(new AnnualizedReturn(trade.getSymbol(), annualized_returns, totalReturns));}
          catch(Exception e){
            System.out.println(e.getMessage());
            
          }
      }
      listOfAnnualizedReturns.sort(getComparator());
      return listOfAnnualizedReturns;
      
    // return null;
  }
  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturnParallel(
      List<PortfolioTrade> portfolioTrades, LocalDate endDate, int numThreads)
      throws InterruptedException, StockQuoteServiceException {
    // TODO Auto-generated method stub
    ExecutorService executorService= Executors.newFixedThreadPool(numThreads);
    List<Future<AnnualizedReturn>> futureAnnualizedReturns= new ArrayList<>();
    List<CallableThread> callableThreads= new ArrayList<>();
    List<AnnualizedReturn> listOfAnnualizedReturns=new ArrayList<>();
    for(PortfolioTrade trade:portfolioTrades){
       callableThreads.add(new CallableThread(trade.getSymbol(),trade.getPurchaseDate(),endDate));
    }
    try{
         futureAnnualizedReturns= executorService.invokeAll(callableThreads);
         for(Future<AnnualizedReturn> futureAnnualisedReturn: futureAnnualizedReturns){
           listOfAnnualizedReturns.add(futureAnnualisedReturn.get());
         }
    }
    catch(InterruptedException e){
      throw new InterruptedException();
    }
    catch(Exception e){
      throw new StockQuoteServiceException(e.getMessage(),e);
    }
    return listOfAnnualizedReturns.stream().sorted(Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed()).collect(Collectors.toList());
  }


  // ¶TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.

  class CallableThread implements Callable<AnnualizedReturn>{
    
    LocalDate tradePurchaseDate;
    LocalDate tradeEndDate;
    String tradeSymbol;
    
    public CallableThread(){
      
    }
    
    public CallableThread(String tradeSymbol,LocalDate tradePurchaseDate, LocalDate tradeEndDate) {
      this.tradePurchaseDate = tradePurchaseDate;
      this.tradeEndDate = tradeEndDate;
      this.tradeSymbol = tradeSymbol;
    }
  
  
  
    @Override
    public AnnualizedReturn call() throws Exception {
      // TODO Auto-generated method stub
     try {
     List<Candle> listOfQuotes= stockQuotesService.getStockQuote(tradeSymbol,tradePurchaseDate,tradeEndDate);
     double buyPrice= listOfQuotes.get(0).getOpen();
         double sellPrice= listOfQuotes.get(listOfQuotes.size()-1).getClose();
         double totalReturns=(sellPrice-buyPrice)/buyPrice;
         double total_num_years= ChronoUnit.DAYS.between(tradePurchaseDate, tradeEndDate)/365.2462;
         double inverseOfYear= (1.00 / total_num_years );
         double annualized_returns = Math.pow((1.00 + totalReturns),inverseOfYear) - 1.00;
     return new AnnualizedReturn(tradeSymbol, annualized_returns, totalReturns) ;
      
     } catch (Exception e) {
      //TODO: handle exception
      throw new InterruptedException(e.getMessage());
    }
  
  }
  }
}


