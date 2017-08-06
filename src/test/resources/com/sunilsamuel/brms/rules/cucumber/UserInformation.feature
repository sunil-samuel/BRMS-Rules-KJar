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
