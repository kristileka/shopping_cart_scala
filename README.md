# Scala Shopping Cart Application

This is a shopping cart application developed using pure vanilla Scala.
The application provides a CLI interface to simulate the shopping scearios
with the added complexity of discounts.

## Architecture

The architecture of the application is similar to en event driven.
A command is launched and then is found to then be created executed.
Commands are Stateless or Stateful. Meaning there are 3 different types.

1) Stateless which will not need a CheckoutContext and return what is current in state if any
2) Stateless which will create or find a CheckoutContext and return it
3) Stateful which will change a checkoutContext and update the system with the changes

## Testing

The application has been tested using both Unit and Integration. Which means
the app tests who the command will work on a test environment and who the checkout
will work in a Unit way.

## Prerequisites

- You need to have Java Virtual Machine (JVM) 11 installed on your machine.

## Installation

1. Clone the repository:
   git clone https://github.com/kristileka/shopping_cart_scala.git


2. Navigate to the project folder:
   cd shopping_cart_scala

## Usage

To run the application, use the following command:
sbt run

To run the tests, use the following command:
sbt test
