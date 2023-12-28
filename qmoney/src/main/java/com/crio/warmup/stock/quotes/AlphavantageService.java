
package com.crio.warmup.stock.quotes;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import com.crio.warmup.stock.dto.AlphavantageCandle;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
// import org.springframework.asm.TypeReference;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.cglib.core.Local;

import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class AlphavantageService implements StockQuotesService {

  private RestTemplate restTemplate;
  public AlphavantageService(){};
  protected AlphavantageService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  
  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException,StockQuoteServiceException {
    // TODO Auto-generated method stub
    Map<LocalDate,AlphavantageCandle> alphavantageMap;
    try{
      String alphavantageDailyResponseString= restTemplate.getForObject(buildUri(symbol),String.class);
    ObjectMapper objectMapper= new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    AlphavantageDailyResponse alphavantageDailyResponse= objectMapper.readValue(alphavantageDailyResponseString, new TypeReference<AlphavantageDailyResponse>() {});
    alphavantageMap= alphavantageDailyResponse.getCandles();}
    catch(JsonProcessingException e){
      throw new StockQuoteServiceException(e.getMessage(),e);
    }
    catch(Exception e){
      throw new StockQuoteServiceException(e.getMessage(),e);
    }
    List<AlphavantageCandle> alphaCandlesList= new ArrayList<>();
    // for(LocalDate ld:alphavantageMap.keySet()){

      // if (ld.isBefore(to) && ld.isAfter(from)){
      //   alphaCandlesList.add(alphavantageMap.get(ld));
      // }
      // else if (ld.isEqual(from) || ld.isEqual(to)) alphaCandlesList.add(alphavantageMap.get(ld));

    // }
    // if (!alphavantageMap.containsKey(to)) to=alphavantageMap.get();
    LocalDate tempDate=LocalDate.parse(from.toString());
    while(!tempDate.isEqual(to.plusDays(1))){
      if (alphavantageMap.containsKey(tempDate)){
       alphavantageMap.get(tempDate).setDate(tempDate);
       alphaCandlesList.add(alphavantageMap.get(tempDate));
      }
      // else break;
      tempDate= tempDate.plusDays(1);
    }
    List<Candle> candleList= new ArrayList<>();
    for(AlphavantageCandle aCandle:alphaCandlesList) candleList.add(aCandle);
    return candleList;
    

    // return null;
  }

  protected String buildUri(String symbol) {
  
    String SYMBOL=symbol;
    String APIKEY=  getToken();
    String uriTemplate = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+SYMBOL+"&outputsize=full&apikey="+APIKEY;
    System.out.println(uriTemplate);
    return uriTemplate;
    }

    protected static String getToken(){
      return "8IRIJL5RMO3VZRA3";
    }

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement the StockQuoteService interface as per the contracts. Call Alphavantage service
  //  to fetch daily adjusted data for last 20 years.

  //  Refer to documentation here: https://www.alphavantage.co/documentation/
  //  --
  //  The implementation of this functions will be doing following tasks:
  //    1. Build the appropriate url to communicate with third-party.
  //       The url should consider startDate and endDate if it is supported by the provider.
  //    2. Perform third-party communication with the url prepared in step#1
  //    3. Map the response and convert the same to List<Candle>
  //    4. If the provider does not support startDate and endDate, then the implementation
  //       should also filter the dates based on startDate and endDate. Make sure that
  //       result contains the records for for startDate and endDate after filtering.
  //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate
  //  IMP: Do remember to write readable and maintainable code, There will be few functions like
  //    Checking if given date falls within provided date range, etc.
  //    Make sure that you write Unit tests for all such functions.
  //  Note:
  //  1. Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  //  2. Run the tests using command below and make sure it passes:
  //    ./gradlew test --tests AlphavantageServiceTest
  //CHECKSTYLE:OFF
    //CHECKSTYLE:ON
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  1. Write a method to create appropriate url to call Alphavantage service. The method should
  //     be using configurations provided in the {@link @application.properties}.
  //  2. Use this method in #getStockQuote.
  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //   1. Update the method signature to match the signature change in the interface.
  //   2. Start throwing new StockQuoteServiceException when you get some invalid response from
  //      Alphavantage, or you encounter a runtime exception during Json parsing.
  //   3. Make sure that the exception propagates all the way from PortfolioManager, so that the
  //      external user's of our API are able to explicitly handle this exception upfront.
  //CHECKSTYLE:OFF

}

