package com.kristileka.shopping.cart.command.stateful

import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

/**
  * This function will remove an item from the cart
 *
 * @param itemId The item who will be removed form the cart
  * @param checkout the checkoutContexct
  * @param applicationContext and the applicationContexted needed by the StatefulCommand to update the system
  */
case class Remove(itemId: String, checkout: Checkout)(implicit val applicationContext: ApplicationContext)
    extends StatefulCommand {
  override def action()(implicit applicationContext: ApplicationContext): Option[Checkout] =
    Option(checkout.remove(itemId))
}
