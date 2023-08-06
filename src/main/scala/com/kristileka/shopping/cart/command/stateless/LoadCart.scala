package com.kristileka.shopping.cart.command.stateless

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

import java.util.UUID

/**
  * This Command will load a cart from memory and bring that back to load
  * @param cartId The cart id to find in memory and load
  * @param applicationContext The ApplicationContext to find the cart from
  */
case class LoadCart(cartId: UUID)(
    implicit applicationContext: ApplicationContext
) extends Command {
  override def execute(): Option[Checkout] = applicationContext.carts.get(cartId)
}
