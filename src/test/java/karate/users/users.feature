@regression
Feature: Reqres Users API

  Background:
    * url baseUrl
    * call read('classpath:karate/common/auth-setup.feature')

  # ── GET ──────────────────────────────────────────────────────────────

  @smoke
  Scenario: Get list of users returns 200 with valid structure
    Given path '/users'
    And param page = 1
    When method GET
    Then status 200
    And match response.data == '#[] #object'
    And match response.page == 1
    And match response.per_page == '#number'
    And match response.total == '#number'

  @smoke
  Scenario: Get single user returns correct data shape
    Given path '/users/2'
    When method GET
    Then status 200
    And match response.data.id == 2
    And match response.data.email == '#string'
    And match response.data.first_name == '#string'
    And match response.data.last_name == '#string'
    And match response.data.avatar == '#string'

  @negative
  Scenario: Get non-existent user returns 404
    Given path '/users/999'
    When method GET
    Then status 404
    And match response == {}

  # ── POST ─────────────────────────────────────────────────────────────

  @smoke
  Scenario Outline: Create user with different roles returns 201
    Given path '/users'
    And request { name: '<name>', job: '<job>' }
    When method POST
    Then status 201
    And match response.name == '<name>'
    And match response.job == '<job>'
    And match response.id == '#string'
    And match response.createdAt == '#string'

    Examples:
      | name           | job             |
      | Jake Rafferty  | QA Engineer     |
      | John Locke     | SDET            |
      | Jane Doe       | Test Architect  |

  # ── PUT ──────────────────────────────────────────────────────────────

  @smoke
  Scenario Outline: Update user job title returns 200
    Given path '/users/2'
    And request { name: '<name>', job: '<job>' }
    When method PUT
    Then status 200
    And match response.name == '<name>'
    And match response.job == '<job>'
    And match response.updatedAt == '#string'

    Examples:
      | name           | job             |
      | Jake Rafferty  | Senior SDET     |
      | John Locke     | Test Architect  |

  # ── PATCH ────────────────────────────────────────────────────────────

  @smoke
  Scenario: Patch user returns 200
    Given path '/users/2'
    And request { job: 'Lead QA' }
    When method PATCH
    Then status 200
    And match response.job == 'Lead QA'
    And match response.updatedAt == '#string'

  # ── DELETE ───────────────────────────────────────────────────────────

  @smoke
  Scenario: Delete user returns 204
    Given path '/users/2'
    When method DELETE
    Then status 204

  # ── NEGATIVE ─────────────────────────────────────────────────────────

  @negative
  Scenario: Create user with empty body returns 201 with null fields
    Given path '/users'
    And request {}
    When method POST
    Then status 201

  @negative
  Scenario Outline: Get user page boundaries
    Given path '/users'
    And param page = <page>
    When method GET
    Then status 200
    And match response.data == '#[]'

    Examples:
      | page |
      | 0    |
      | 1    |
      | 2    |
      | 999  |