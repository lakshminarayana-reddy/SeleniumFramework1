$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("UploadImageValidation.feature");
formatter.feature({
  "line": 1,
  "name": "Upload Image Validation",
  "description": "",
  "id": "upload-image-validation",
  "keyword": "Feature"
});
formatter.before({
  "duration": 7098919800,
  "status": "passed"
});
formatter.scenario({
  "line": 2,
  "name": "Upload Image Validation",
  "description": "",
  "id": "upload-image-validation;upload-image-validation",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 3,
  "name": "User get data from excel sheet \"UploadImage\" for health checks",
  "keyword": "Given "
});
formatter.step({
  "line": 4,
  "name": "Verify the image upload and get doc details",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "UploadImage",
      "offset": 32
    }
  ],
  "location": "CommonStepDef.userGetDataFromExcelSheetForMTRECalculations(String)"
});
formatter.result({
  "duration": 756550900,
  "status": "passed"
});
formatter.match({
  "location": "UploadImageStepDef.verifyTheImageUploadAndGetDocDetails()"
});
formatter.result({
  "duration": 35451780900,
  "status": "passed"
});
formatter.after({
  "duration": 767102600,
  "status": "passed"
});
});