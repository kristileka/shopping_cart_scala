package com.kristileka.shopping.cart.command.stateless

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

import java.util.UUID

/**
  * This Command will create a new cart in memory
  * @param applicationContext The applicationContext to create the cart into
  */
case class CreateCart()(implicit applicationContext: ApplicationContext) extends Command {
  override def execute(): Option[Checkout] = {
    val cartId   = UUID.randomUUID()
    val checkout = Checkout(cartId, List())
    applicationContext.addCart(cartId, checkout)
    show(s"Cart created with id $cartId")
    Option(checkout)
  }
}
