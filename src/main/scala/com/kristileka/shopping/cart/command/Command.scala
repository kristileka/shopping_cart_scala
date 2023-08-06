package com.kristileka.shopping.cart.command

import com.kristileka.shopping.cart.command.common.{ EmptyCommand, OutOfStateCommand, UnknownCommand }
import com.kristileka.shopping.cart.command.stateful.{ FlushCart, Inventory, Remove, Scan, Total }
import com.kristileka.shopping.cart.command.stateless.{ CreateCart, Discount, Help, Items, LoadCart }
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.application.exception.ExitException
import com.kristileka.shopping.cart.products.Checkout

import java.util.UUID

/**
  * This is a trait that will have the Command baseLine. A execute Command which will bring back a CheckoutContex
  * and a Show Command to print info to the shell
  */
trait Command {
  def execute(): Option[Checkout]
  def show(param: String = ""): Unit = println(param)
}

/**
  * This is the object that will be finding the possible Commands and validate them if their state
  * is succesfuly
  */
object Command {
  private val CC        = "cc"
  private val LOAD      = "load"
  private val FLUSH     = "flush"
  private val SCAN      = "scan"
  private val INVENTORY = "inventory"
  private val TOTAL     = "total"
  private val REMOVE    = "remove"
  private val DISCOUNT  = "discount"
  private val ITEMS     = "items"
  private val EXIT      = "exit"
  val HELP              = "help"
  private val SPLITTER  = " "

  /**
    * The Command runner function which will find the best Command to run
    * @param input The input given by the user ot Test
    * @param checkoutContext The checkoutContext
    * @param applicationContext the ApplicationContext
    * @return the new CheckoutContext to be stored
    */
  def from(input: String)(implicit checkoutContext: Option[Checkout],
                          applicationContext: ApplicationContext): Command = {
    val tokens: Array[String] = input.split(SPLITTER)

    if (input.isEmpty || tokens.isEmpty) new EmptyCommand
    else if (CC.equals(tokens(0))) {
      CreateCart()
    } else if (LOAD.equals(tokens(0))) {
      LoadCart(UUID.fromString(tokens(1)))
    } else if (FLUSH.equals(tokens(0))) {
      checkoutContext.map(FlushCart(_)).getOrElse(OutOfStateCommand)
    } else if (SCAN.equals(tokens(0))) {
      checkoutContext.map(Scan(tokens(1), _)).getOrElse(OutOfStateCommand)
    } else if (REMOVE.equals(tokens(0))) {
      checkoutContext.map(Remove(tokens(1), _)).getOrElse(OutOfStateCommand)
    } else if (TOTAL.equals(tokens(0))) {
      checkoutContext.map(Total(_)).getOrElse(OutOfStateCommand)
    } else if (INVENTORY.equals(tokens(0))) {
      checkoutContext.map(Inventory(_)).getOrElse(OutOfStateCommand)
    } else if (ITEMS.equals(tokens(0))) {
      Items()
    } else if (DISCOUNT.equals(tokens(0))) {
      Discount()
    } else if (HELP.equals(tokens(0))) {
      Help()
    } else if (EXIT.equals(tokens(0))) {
      throw ExitException()
    } else new UnknownCommand
  }
}
