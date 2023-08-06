package com.kristileka.shopping.cart.command.common

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.products.Checkout

/**
  * This is an UnknownCommand class which will just return back the checkout as received
  * and will be created when a Command wont be found
 *
  * @param checkout the checkout that will be given and sent back
  */
case class UnknownCommand()(implicit checkout: Option[Checkout]) extends Command {
  override def execute(): Option[Checkout] = {
    show("Command is unknown")
    checkout
  }
}
