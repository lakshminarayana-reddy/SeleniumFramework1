Feature: Upload Image Validation
  Scenario: Upload Image Validation
    Given User get data from excel sheet "UploadImage" for health checks
    Then Verify the image upload and get doc details