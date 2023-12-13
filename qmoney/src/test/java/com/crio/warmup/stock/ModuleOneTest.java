
package com.crio.warmup.stock;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModuleOneTest {

  @Test
  void mainReadFile() throws Exception {
    //given
    String filename = "/home/crio-user/workspace/sairam-criodo-ME_QMONEY_V2/qmoney/src/test/resources/assessments/trades.json";
    List<String> expected = Arrays.asList(new String[]{"MSFT", "CSCO", "CTS"});

    //when
    List<String> results = PortfolioManagerApplication
        .mainReadFile(new String[]{filename});

    //then
    Assertions.assertEquals(expected, results);
  }

  @Test
  void mainReadFileEdgecase() throws Exception {
    //given
    String filename = "/home/crio-user/workspace/sairam-criodo-ME_QMONEY_V2/qmoney/src/test/resources/assessments/empty.json";
    List<String> expected = Arrays.asList(new String[]{});

    //when
    List<String> results = PortfolioManagerApplication
        .mainReadFile(new String[]{filename});

    //then
    Assertions.assertEquals(expected, results);
  }

}
