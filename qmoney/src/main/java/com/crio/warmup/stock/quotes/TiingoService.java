
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {

  private RestTemplate restTemplate;
  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
    // TODO Auto-generated method stub
    System.out.println(restTemplate);
    String jsonString= restTemplate.getForObject(buildUri(symbol, from, to),String.class);
    ObjectMapper objMapper= new ObjectMapper();
    objMapper.registerModule(new JavaTimeModule());
    Candle[] listOfCandles= objMapper.readValue(jsonString, TiingoCandle[].class);
    System.out.println(listOfCandles);
    return Arrays.asList( listOfCandles);

    // return null;
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

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.

}
