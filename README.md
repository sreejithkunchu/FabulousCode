# Cucumber-Spring-Bdd framework to compare 2 files having API responses

Required Software's and Plugins:

Java 1.8

Maven

Intellij

Intellij Gherkin Plugin

Intellij Cucumber Java Plugin

Intellij Lombok Plugin



Command to run from terminal:
If in case while running the sceanrio if VM crashs or if you see error like merror leak then kindly increase the -Xmx300m in POM.xml to some higher value say ("-Xmx500m")
Since it has now only one active profile, so Open terminal and navigate to the project where POM is located and run this maven command:
 mvn test

UnitCase: Unitcases are added to verify Utils and to run all cases from terminal navigate to the project where POM is located and run this maven command:
mvn -Dtest="com.fabulous.code.response.junit.**" test

Brief about the Approach:
Used Cucumber-Spring-Bdd framework to create feature file for reponse comparison. Its a 3 step feature, 1st 2 steps are to fetch urls from the both files and store it in object and 3rd step does the rest api call and get the response for both APis and does the comparion parallelly by invoking multi thread concept.
Logic for Comparator : converting entire reponse into List of Map of String and then comparing it.


