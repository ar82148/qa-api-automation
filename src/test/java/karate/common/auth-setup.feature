@ignore
Feature: Auth Setup

  Scenario: Setup auth header
    * def apiKey = java.lang.System.getenv('REQRES_PUBLIC_KEY') || karate.properties['REQRES_PUBLIC_KEY']
    * configure headers = { 'x-api-key': '#(apiKey)' }