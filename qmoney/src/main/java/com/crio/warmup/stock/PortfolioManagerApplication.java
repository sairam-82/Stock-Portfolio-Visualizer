
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
// import com.crio.warmup.stock.dto.PortfolioTrade.TradeType;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.crio.warmup.stock.portfolio.PortfolioManagerImpl;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;

import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
// import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
// import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;

// import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.nio.file.Files;
import java.time.temporal.ChronoUnit;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.type.ReferenceType;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.cglib.core.Local;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication  {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
     List<String> list=debugOutputs();
     ObjectMapper objMapper=  getObjectMapper();
     if (args.length==0) return list;
    //  System.out.println(args[0]);
     File json= new File(args[0]);
     PortfolioTrade[] objList= objMapper.readValue(resolveFileFromResources(args[0]),PortfolioTrade[].class);
     List<String> listOfSymbols= new ArrayList<>();
     for(PortfolioTrade t:objList){
       listOfSymbols.add(t.getSymbol());
      //  System.out.println(t.getSymbol());
     }


     return listOfSymbols;
  }


  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.









  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    // System.out.println(filename);
    File file=Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
        // System.out.println(file);
    return file;
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/sairam-criodo-ME_QMONEY_V2/qmoney/bin/main/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@3bf7ca37";
     String functionNameFromTestFileInStackTrace = "mainReadFile";
     String lineNumberFromTestFileInStackTrace = "29";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }
  static class Pair{
    Candle tiingoCandle;
    PortfolioTrade trade;
    Pair(PortfolioTrade trade, Candle candle){
      this.tiingoCandle=candle;
      this.trade=trade;
    }
  }

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    LocalDate endDate=LocalDate.parse(args[1]);
    // System.out.println(args[0]+" "+endDate.toString());
    List<PortfolioTrade> tradesList= readTradesFromJson(args[0]);
    String tingoToken=getToken();
    
    List<Pair> closeAndTiingoCandlePair= new ArrayList<>();
    for(PortfolioTrade trade:tradesList){
      // String createdUrl= prepareUrl(trade, endDate, tingoToken);
      // List<TiingoCandle> tiingoCandleObjs= readFromApi(createdUrl);
      List<Candle> candlesList= fetchCandles(trade, endDate,tingoToken);
      Candle tiingoCandleObj=candlesList.get(candlesList.size()-1);
      closeAndTiingoCandlePair.add(new Pair(trade, tiingoCandleObj ));
    }
    
    closeAndTiingoCandlePair.sort((a,b)->{ return a.tiingoCandle.getClose().intValue()-b.tiingoCandle.getClose().intValue();});
    // readFromApi(createdUrl)
    List<String> sortedSymbolList= new ArrayList<>();
    for( Pair obj:closeAndTiingoCandlePair){
      sortedSymbolList.add(obj.trade.getSymbol());
    }
    return sortedSymbolList;
  }

  public static List<Candle> readFromApi(String url){
    RestTemplate restTemplate= new RestTemplate();
    Candle[] tiingoObj = restTemplate.getForObject(url,TiingoCandle[].class);       
    return Arrays.asList(tiingoObj);
  }

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    ObjectMapper objectMapper= getObjectMapper();
    // System.out.println(filename+" "+objectMapper);
    List<PortfolioTrade> tradesList= objectMapper.readValue(resolveFileFromResources(filename),new TypeReference<List<PortfolioTrade>>() {});
    
    return tradesList;
  }


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
     return "https://api.tiingo.com/tiingo/daily/"+trade.getSymbol()+"/prices?startDate="+trade.getPurchaseDate().toString()+"&endDate="+endDate.toString()+"&token="+token;
  }
  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.




  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  public static String getToken(){
    String token="eb53fb80c7abfdf2b0482e9f8f072b9cbab71ce5";
    return token;
  }
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
      return candles.get(0).getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
     return candles.get(candles.size()-1).getClose();
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
        String createdUrl= prepareUrl(trade, endDate, token);
        List<Candle> tiingoCandleObjs= readFromApi(createdUrl);
        return tiingoCandleObjs;
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {

     List<PortfolioTrade> sortedSymbols=readTradesFromJson(args[0]);
     List<AnnualizedReturn> annualizedReturns= new ArrayList<>();

     for(PortfolioTrade trade:sortedSymbols){
        List<Candle> listCandles=fetchCandles(trade, LocalDate.parse(args[1]),getToken());
      Double sell_value=getClosingPriceOnEndDate(listCandles);
      Double buy_value=getOpeningPriceOnStartDate(listCandles);
      annualizedReturns.add(calculateAnnualizedReturns(LocalDate.parse(args[1]),trade,buy_value,sell_value));
     }
     annualizedReturns.sort((a,b)->{
      double i=a.getAnnualizedReturn();
      double j= b.getAnnualizedReturn();
      if (i>j) return -1;
      if (i<j) return 1;
      else return 0;
     });
     return annualizedReturns;
      
      
     
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
      // List<Candle> listCandles=fetchCandles(trade, endDate, getToken());
      
      double totalReturns=(sellPrice-buyPrice)/buyPrice;
      double total_num_years= ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate)/365.2462;
      double inverseOfYear= (1.00 / total_num_years );
      double annualized_returns = Math.pow((1.00 + totalReturns),inverseOfYear) - 1.00;
      return new AnnualizedReturn(trade.getSymbol(), annualized_returns, totalReturns);
  }













  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());
   

//     printJsonObject(mainReadFile(args));
//  }
  //   printJsonObject(mainReadQuotes(new String[]{"trades.json","2019-12-12"}));
  // }
    // printJsonObject(mainCalculateSingleReturn(args));
    printJsonObject(mainCalculateReturnsAfterRefactor(args));
  }








  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static String readFileAsString(File file){
    return file.toString();

  }
  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
       RestTemplate restTemplate= new RestTemplate();
      //  args= new String[]{"trades.json","2019-01-12"};
       PortfolioManager portfolioManager=PortfolioManagerFactory.getPortfolioManager(restTemplate);
      //  File file =resolveFileFromResources(args[0]);
      //  String contents = readFileAsString(file);
      //  System.out.println(contents);
       List<PortfolioTrade> portfolioTrades= readTradesFromJson(args[0]);
       return portfolioManager.calculateAnnualizedReturn(portfolioTrades, LocalDate.parse(args[1]));

      //  return null;

 }


  // public static void main(String[] args) throws Exception {
  //   Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
  //   ThreadContext.put("runId", UUID.randomUUID().toString());




    
  // }
}

