
package com.crio.warmup.stock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.crio.warmup.stock.portfolio.PortfolioManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class ModuleOneTest {

  // @Test
  // void mainReadFile() throws Exception {
  //   //given
  //   String filename = "assessments/trades.json";
  //   List<String> expected = Arrays.asList(new String[]{"MSFT", "CSCO", "CTS"});

  //   //when
  //   List<String> results = PortfolioManagerApplication
  //       .mainReadFile(new String[]{filename});

  //   //then
  //   Assertions.assertEquals(expected, results);
  // }

  // @Test
  // void mainReadFileEdgecase() throws Exception {
  //   //given
  //   String filename = "assessments/empty.json";
  //   List<String> expected = Arrays.asList(new String[]{});

  //   //when
  //   List<String> results = PortfolioManagerApplication
  //       .mainReadFile(new String[]{filename});

  //   //then
  //   Assertions.assertEquals(expected, results);
  // }
  @Test
  void buildUri() throws Exception {
    //given
    String filename = "assessments/trades.json";
    String expected = "https://api.tiingo.com/tiingo/daily/AAPL/prices?startDate=2019-12-01&endDate=2019-12-09&token=eb53fb80c7abfdf2b0482e9f8f072b9cbab71ce5";

    //when
    PortfolioManagerImpl port= (PortfolioManagerImpl)PortfolioManagerFactory.getPortfolioManager(new RestTemplate());
    List<Candle> results = port
        .getStockQuote("AAPL",LocalDate.parse("2019-12-01"),LocalDate.parse("2019-12-09"));

    //then
    Assertions.assertEquals(expected, results);
  }

}
