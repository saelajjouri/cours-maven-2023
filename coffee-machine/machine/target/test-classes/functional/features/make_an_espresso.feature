Feature: Make a espresso with a complete espresso machine
  A user want a espresso
  Scenario: A user plug the espresso machine and make a espresso Arabica
    Given a espresso machine with 0.10 l of min capacity, 3.0 l of max capacity, 600.0 l per h of water flow for the pump
    And a espresso "mug" with a capacity of 0.25
    When I plug the espresso machine to electricity
    And I add 1 liter of water in the water tank of the espresso machine
    And I add 0.5 liter of "ARABICA" in the bean tank of the espresso machine
    And I made a espresso "ARABICA"
    Then the espresso machine return a espresso mug not empty
    And a espresso volume equals to 0.25
    And a espresso "espressoMug" containing a espresso type "ARABICA"


  Scenario: A user plug the espresso machine and make a espresso
    Given a espresso machine with 0.10 l of min capacity, 3.0 l of max capacity, 600.0 l per h of water flow for the pump
    And a espresso "cup" with a capacity of 0.15
    When I plug the espresso machine to electricity
    And I add 1 liter of water in the water tank of the espresso machine
    And I add 0.5 liter of "ROBUSTA" in the bean tank of the espresso machine
    And I made a espresso "ROBUSTA"
    Then the espresso machine return a espresso mug not empty
    And a espresso volume equals to 0.15
    And a espresso "espressoCup" containing a espresso type "ROBUSTA"

  Scenario: A user plugs the espresso machine and try to make a espresso but the machine is out of order
    Given a espresso machine with 0.10 l of min capacity, 3.0 l of max capacity, 600.0 l per h of water flow for the pump that is out of order
    When I plug the espresso machine to electricity
    And a espresso "cup" with a capacity of 0.15
    When I plug the espresso machine to electricity
    And I add 1 liter of water in the water tank of the espresso machine
    And I add 0.5 liter of "ROBUSTA" in the bean tank of the espresso machine
    And I made a espresso "ROBUSTA"
    Then nothing is returned to the user because the espresso machine is out of order