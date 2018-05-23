@BRMS @StateInformation @Complete @Ignore

Feature: Validate Loan According to State Rules
  I want to use the state rules to assign loan amounts
  I can only assign the given number of loans for a given state
  Therefore, I have to find the first N students who applied for the loan
  I would apply the rules against them and assign loan amounts

  Scenario: Validate Loan Amounts for MD
    Given a total of 4 loans for the state of MD
    And user 1 Sunil Samuel identified by 43234 from MD age 29 who is a FullTime student with an income of $42000
    And user 2 Paul Mathew identified by 53324 from MD age 17 who is a FullTime student with an income of $32000
    And user 3 John Tester identified by 63324 from MD age 32 who is a PartTime student with an income of $60000
    And user 4 Eric Tester identified by 75546 from MD age 16 who is a PartTime student with an income of $50000
    And user 5 John Kennedy identified by 86657 from MD age 29 who is a PartTime student with an income of $50000
    When I process loan amounts for states
    Then user 43234 is eligible for $15000
    And user 53324 is eligible for $20000
    And user 63324 is eligible for $10000
    And user 75546 is eligible for $5000
    And user 86657 is not eligible
