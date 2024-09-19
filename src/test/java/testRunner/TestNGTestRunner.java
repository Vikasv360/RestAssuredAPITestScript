package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/features",
		plugin = "json:target/jsonReports/cuscumber-report.json",
		glue = "stepDefinitions",
		dryRun = false,
		monochrome = true
		)

public class TestNGTestRunner extends AbstractTestNGCucumberTests{

}
