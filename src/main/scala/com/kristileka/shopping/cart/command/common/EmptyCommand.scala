package com.kristileka.shopping.cart.command.common

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.products.Checkout

/**
  * This is an EmptyCommand class that will just bring back and not perform any operation
  * to the checkoutContext
 *
  * @param checkout The checkoutContext that will be sent and returned back by the Command
  */
case class EmptyCommand()(implicit checkout: Option[Checkout]) extends Command {
  override def execute(): Option[Checkout] = checkout
}
