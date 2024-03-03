package stepdef;

import cucumber.api.java.en.Then;
import pages.CommonPageActions;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;
import utils.CommonActions;
import utils.Waits;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewImageStepDef {
    @Then("^Verify the image in claim file view$")
    public void verifyImageInClaimFileView(){
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++){
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            CommonPageActions commonPageActions = new CommonPageActions();
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            if(excelData.get(i).get("Env_Name").equals("CT")){
                homePage.navigateToRequiredLink_Internal("Claim File View");
            } else {
                homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            }
            MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();
            medicalBillReviewSolutionsPage.searchWithDocId((String) excelData.get(i).get("DocId"));
            medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
            medicalBillReviewSolutionsPage.clickOnFirstImageDocument();
            medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
            if(excelData.get(i).get("Env_Name").equals("QA")){
                medicalBillReviewSolutionsPage.switchToFirstWindow();
                medicalBillReviewSolutionsPage.verifyImageIsOpened();
            }else {
                medicalBillReviewSolutionsPage.verifyImageIsDownloaded((String) excelData.get(i).get("DocId"));
            }
            //medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
            System.out.println("Successfully verified View image Checks in "+excelData.get(i).get("Env_Name"));
            medicalBillReviewSolutionsPage.deleteAllCookies();
        }
    }
}
