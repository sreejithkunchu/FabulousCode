Feature: Compare response of files


  Scenario: Compare response of files
    Given user fetch the response of end URl given in file1 'file1.csv'
    Then user fetch the response of end URl given in file2 'file2.csv'
    Then user compare the response of the above files