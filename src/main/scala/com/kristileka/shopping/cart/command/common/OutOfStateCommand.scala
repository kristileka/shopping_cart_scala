package com.kristileka.shopping.cart.command.common

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.products.Checkout

/**
  * This is an object class that will be thrown when an operation is done wrongly
  */
object OutOfStateCommand extends Command {
  override def execute(): Option[Checkout] = {
    show("No cart was found please load or create a new cart")
    None
  }
}
