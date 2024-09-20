# REST Assured API Testing using Cucumber BDD framework
## Overview

This repository contains an API testing framework built using REST Assured, integrated with BDD using Cucumber and TestNG. This approach allows for clear and concise test definitions, making it easier to understand and maintain tests.

## Features

- **REST Assured**: Simplifies the testing of RESTful APIs.
- **Cucumber**: Implements BDD to write tests in a human-readable format.
- **TestNG**: Provides a testing framework that integrates seamlessly with Cucumber.
- **Support for multiple environments**: Easily switch between different API endpoints.

## Prerequisites
To run the tests, ensure you have the following installed:

- **Java 11 or higher**
- **Maven**
- **Cucumber**
- **TestNG**
- **REST Assured**

## Writing Tests
1. **Project structure**:
- **\src\main\java** > Files package which consists of all the required request payload in the form of POJO classes ( for serialization ).
 
- **\src\test\java** >
  
  **Features package**: consists of feature file which has all API test scenarios.
  
  **resources package**: Consists of Utility class(), TestDataBuild class(), global property file.
  
  **stepDefinitions package**: Consists of Stepdefinition class which has all the working code.
  
  **testRunner package**: Consists of TestNGTestRunner class which extends AbstractTestNGCucumberTests abstract class(dependent on TestNG dependency)

2. **Dependencies used**:
- **Rest Assured** 
- **Hamcrest** 
- **Cucumber TestNG** 
- **Cucumber java**
- **Jackson databind**





