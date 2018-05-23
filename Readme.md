<p align='right'>
<small>Sunil Samuel<br>
web_github@sunilsamuel.com<br>
http://www.sunilsamuel.com
</small>
</p>

**<h1 align='center'>[Code] BRMS :: Rules as KJar</h1>**

# Overview

This project is a sample BRMS (Drools) project that can be used to deploy into the kie-server.

## Introduction

This sample BRMS (Drools) project can be used as a starting point for your own requirements and to be extended to support your criteria.  This application uses Cucumber to test the BRMS (Drools) engine and to run as JUnit or Maven tests.

The `RulesEngineSupport.java` is a re-usable generic code that can help to test your DRL rules.  The application is written with the assumption that you have some understanding of the Drools engine and the Kie Server.  There is another Github application that is created to describe the deployment of this kjar into the kie server.  See the following project:

```
KieServer-KJar-Deployment
```

## Technology Stack

1. Drools (BRMS)
2. Cucumber

# Project Description

The application provides a scenario that uses a person's information to determine if this person is eligible for a loan and if they are, the amount of the loan.  This information is determined by the input from the user.

## Installation

The KJar should be installed into your maven .m2 repository directory so that it can be deployed into the Kie Server.  In order to install into the maven repository, use maven as follows in the `BRMS-Rules-KJar` directory:

```sh
mvn clean install
```

## Rule

This is a very simple application that uses the following rule to determine the loan amount for a person.  This application can be extended to be more realistic and more robust.

There are two types of rules we want to create.  The first one is already created for you.  The second set of rules is something that you should develop on your own.  The first set of rules assign eligible loan amount to a person as long as they are not in a set of special states that
follow a different set of rules (this is what you will be developing).

```
Criteria 1
 1. The user is not in a state with special state rules
 2. The user is 18 or over years old
 3. The user is a full-time student
 4. The user family income is less than $45,000
 THEN : Loan Amount = 5000
```

```
Criteria: 2
1. The user is not in a state with special state rules
2. The user is less than 18 years old
3. The user is a full-time student
4. The user family income is less than $45,000
THEN : Loan Amount = 8000
```

```
Criteria: 3
1. The user is not in a state with special state rules
2. The user is 18 or over years old
3. The user is a part-time student
4. The user family income is less than $55,000
THEN : Loan Amount = 3000
```

```
Criteria: 4
1. Otherwise, the user is not in a state with special state rules
2. the person is not eligible for a loan
```

## Query

The Drools Query can be used to retrieve the collection of all of the LoanAmount objects that were inserted into the knowledge session.

```sql
query "Query LoanAmount"
	loanAmount : List() from collect(LoanAmount())
end
```
## Next Steps

If you are up to it, there are a sample set of test cases that you could create rule to make them pass.  There is a file named `stateLoanEvaluate.drl`, that has a set of rules
to create.  In order to test the Test Driven Development (TDD), uncomment the `StateInformation.feature` file by removing the `@Ignore`  tag from the test cases.
Now, run `maven test` and you will see some of the tests failing.  This is because you have not written the rules yet. 

Go to the `stateLoanEvaluate.drl` file and create the BRMS rules as defined.  There will be four rules you will create.  Take a look at the model objects
to figure this out.  If you need some help, there is a completely working rules files inside brms->answer named `stateLoanEvaluateAnswer.drl`.  Try not to look 
at that file unless if you have to.

```
 *  RULE 1
 *  GIVEN the user is from MD and is one of the first N users to apply in order
 *        where N is the total number of loans available
 *  AND age >= 18
 *  AND collegeStatus == PartTime
 *  AND familyIncome <=80000
 *  THEN eligible for $10000 loan
```
```
 *  RULE 2
 *  GIVEN the user is from MD and is one of the first N users to apply in order
 *        where N is the total number of loans available
 *  AND age >= 18
 *  AND collegeStatus == FullTime
 *  AND familyIncome <=60000
 *  THEN eligible for $15000 loan
```
```
 *  RULE 3
 *  GIVEN the user is from MD and is one of the first N users to apply in order
 *        where N is the total number of loans available
 *  AND age < 18
 *  AND collegeStatus == FullTime
 *  AND familyIncome <=60000
 *  THEN eligible for $20000 loan
```
```
 *  RULE 4
 *  GIVEN the user is from MD and is one of the first N users to apply in order
 *        where N is the total number of loans available
 *  AND age < 18
 *  AND collegeStatus == PartTime
 *  AND familyIncome <=50000
 *  THEN eligible for $5000 loan
```

## Kie-Server Request

Once this is deployed into the kie server (see **KieServer-KJar-Deployment** project), the following JSON can be used to execute the rules against your data elements.

```http
POST /kie-server/services/rest/server/containers/instances/brms-rules-kjar HTTP/1.1
Host: localhost:8080
content-type: application/json
accept: application/json
X-KIE-ContentType: JSON
Accept: application/json
Authorization: Basic [your authorization]
Cache-Control: no-cache

{
   "lookup":"defaultStatelessKieSession",
   "commands":[
      {
         "insert":{
            "object":{
               "com.sunilsamuel.brms.model.UserInformation":{
                  "firstName":"Sunil",
                  "lastName":"Samuel",
                  "identifier":2342342,
                  "age":18,
                  "collegeStatus":"PartTime",
                  "familyIncome":46000
               }
            }
         }
      },
      {
         "insert":{
            "object":{
               "com.sunilsamuel.brms.model.UserInformation":{
                  "firstName":"Joel",
                  "lastName":"Samuel",
                  "identifier":234234245,
                  "age":18,
                  "collegeStatus":"FullTime",
                  "familyIncome":43000
               }
            }
         }
      },
      {
         "fire-all-rules":""
      },
      {
         "query":{
            "name":"Query LoanAmount",
            "out-identifier":"loanAmount"
         }
      }
   ]
}
``` 

## Kie-Server Response

The response from the Kie-Server will include every LoanAmount object that was inserted by the rules.  Therefore, you can send multiple facts into the Drools engine and the `query` will provide the list of inserted objects. 

The Kie Server response is as follows:

```js
{
   "type":"SUCCESS",
   "msg":"Container brms-rules-kjar successfully called.",
   "result":{
      "execution-results":{
         "results":[
            {
               "key":"",
               "value":4
            },
            {
               "key":"loanAmount",
               "value":{
                  "org.drools.core.runtime.rule.impl.FlatQueryResults":{
                     "idFactHandleMaps":{
                        "type":"LIST",
                        "componentType":null,
                        "element":[
                           {
                              "type":"MAP",
                              "componentType":null,
                              "element":[
                                 {
                                    "key":"loanAmount",
                                    "value":{
                                       "org.drools.core.common.DisconnectedFactHandle":{
                                          "id":6,
                                          "identityHashCode":2069568795,
                                          "objectHashCode":-1629276672,
                                          "recency":6,
                                          "object":[
                                             {
                                                "com.sunilsamuel.brms.model.LoanAmount":{
                                                   "userIdentifier":2342342,
                                                   "loanAmount":3000,
                                                   "eligible":true
                                                }
                                             },
                                             {
                                                "com.sunilsamuel.brms.model.LoanAmount":{
                                                   "userIdentifier":234234245,
                                                   "loanAmount":5000,
                                                   "eligible":true
                                                }
                                             }
                                          ],
                                          "traitType":"NON_TRAIT",
                                          "external-form":"0:6:2069568795:-1629276672:6:null:NON_TRAIT:java.util.ArrayList"
                                       }
                                    }
                                 }
                              ]
                           }
                        ]
                     },
                     "idResultMaps":{
                        "type":"LIST",
                        "componentType":null,
                        "element":[
                           {
                              "type":"MAP",
                              "componentType":null,
                              "element":[
                                 {
                                    "key":"loanAmount",
                                    "value":[
                                       {
                                          "com.sunilsamuel.brms.model.LoanAmount":{
                                             "userIdentifier":2342342,
                                             "loanAmount":3000,
                                             "eligible":true
                                          }
                                       },
                                       {
                                          "com.sunilsamuel.brms.model.LoanAmount":{
                                             "userIdentifier":234234245,
                                             "loanAmount":5000,
                                             "eligible":true
                                          }
                                       }
                                    ]
                                 }
                              ]
                           }
                        ]
                     },
                     "identifiers":{
                        "type":"SET",
                        "componentType":null,
                        "element":[
                           "loanAmount"
                        ]
                     }
                  }
               }
            }
         ],
         "facts":[

         ]
      }
   }
}
```


# Cucumber Testing 

Cucumber (cukes) is great for testing the results of the Drools execution given your facts.  The `RulesEngineSupport.java` test support code can be used to extend the testing capabilities.  Please look at the `RuleEngine.java` that extends `RulesEngineSupport.java`.  These code can be used directly as is into your application to test your application.
 
 ## Feature File
 
 A sample cucumber feature file looks as follows:
 
```feature
@BRMS @UserInformation @Complete
Feature: Validate Loan Amount for User
  Validate the loan amount given the different values for the criteria including
  College Status, Age, and Family Income.

  Scenario Outline: Validate Loan Amount
    Given a user with the first name <first> and the last name <last>
    And is identified with <identifier>
    And the user age is <age>
    And the user college status is <status>
    And the user family income is <income>
    When I check the loan eligibility
    Then the loan amount is <amount>
    And the loan user identifier is <loanIdentifier>
    And the loan eligibility is <eligibility>

    Examples: 
      | first   | last   | identifier | age | status   | income | amount | loanIdentifier | eligibility |
      | Sunil   | Samuel |        123 |  14 | FullTime |  42000 |   8000 |            123 | true        |
      | Person  | Samuel |     421231 |  21 | PartTime |  54043 |   3000 |         421231 | true        |
      | Person2 | Samuel |      23432 |  21 | FullTime |  32000 |   5000 |          23432 | true        |
      | Person2 | Samuel |      23432 |  21 | FullTime |  32000 |   5000 |          23432 | true        |
      | Person3 | Samuel |     123455 |  21 | FullTime |  76000 |      0 |         123455 | false       |
```