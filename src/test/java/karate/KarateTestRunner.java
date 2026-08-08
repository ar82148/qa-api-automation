package karate;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Updating with paraellel, HTML report, and tag support
public class KarateTestRunner {

    @Test
    void testAll() {
        Results results = Runner.path("classpath:karate")
                .tags("~@ignore")
                .outputHtmlReport(true)
                .parallel(1);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    @Test
    void testSmoke() {
        Results results = Runner.path("classpath:karate")
                .tags("@smoke", "~@ignore")
                .outputHtmlReport(true)
                .parallel(1);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}