package com.kristileka.shopping.cart.command.stateless

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

/**
  * This Command will list the discounts from the system and get back the cart if that is going to be needed
 *
 * @param applicationContext The applicationContext to get the discounts from
  */
case class Discount()(implicit checkout: Option[Checkout], applicationContext: ApplicationContext) extends Command {
  override def execute(): Option[Checkout] = {
    applicationContext.rules.foreach(rule => show(rule.description))
    checkout
  }
}
