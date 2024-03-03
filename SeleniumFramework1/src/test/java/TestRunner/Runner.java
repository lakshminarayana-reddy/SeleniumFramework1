package TestRunner;



import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        //****************Run 1st set of Tests
         //features={"src/test/java/features/Tests1"},

        //*************After some time(like 4-5mins)- Run second set of tests
        // features={"src/test/java/features/Tests2"},

        //************once second set of tests ran successfully - run 3rd set of tests
        //  features={"src/test/java/features/Tests3"},

        //************upload image takes more time to create the docid -you need to wait some time and run 4th set of tests
          features={"src/test/java/features/Tests4"},


//*******To run individual test - use below

//features="src\\test\\java\\features\\Tests1\\DatabaseChecks.feature",
//features="src\\test\\java\\features\\Tests1\\ApplicationChecks.feature",
// f features="src\\test\\java\\features\\Tests1\\ViewEORDocument.feature",
//  features="src\\test\\java\\features\\Tests1\\PIPLogChecks.feature",
 // features="src\\test\\java\\features\\Tests1\\CASSValidation.feature",
 //  features="src\\test\\java\\features\\Tests1\\MTRECalculationChecks.feature",
 //  features="src\\test\\java\\features\\Tests1\\3MWebservices.feature",
 // features="src\\test\\java\\features\\Tests1\\UploadImage.feature",

//To run below features - we need to run MTRECalculationChecks and 3MWebservices at first and wait for some time
// features="src\\test\\java\\features\\Tests2\\DraftPrintingChecks.feature",
// features="src\\test\\java\\features\\Tests2\\PPOValidations.feature",
//features="src\\test\\java\\features\\Tests2\\3MWebserviceValidations.feature",


//features="src\\test\\java\\features\\Tests3\\MoveDocToNurseReview.feature",
//features="src\\test\\java\\features\\Tests4\\UploadImageValidation.feature",


        glue="",
        plugin={"com.cucumber.listener.ExtentCucumberFormatter:Reports/cucumber-reports/report.html"},
        format={"pretty","html:test-output"}
)


public class Runner {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/config/extent-config.xml"));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
    }
}

//move to nursereview atlast
//ZZ1112237 ,ZZ0604200
