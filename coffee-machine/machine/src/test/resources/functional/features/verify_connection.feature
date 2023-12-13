Feature: Verify the coffee machine connection to the electrical network
  Scenario: User plugs in the coffee machine
    Given the machine of coffee with 0.10 l of min capacity, 3.0 l of max capacity, 600.0 l per h of water flow for the pump
    And the coffee machine is not connected to the electrical network
    And the coffee machine is not out of order
    When the user plugs the machine into the electrical socket
    Then the coffee machine is correctly connected to the electrical network
