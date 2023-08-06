package com.kristileka.shopping.cart.command.stateful

import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

/**
  * This function will Scan and insert an item to the cart
 *
 * @param itemId The item who be scanned and inserted form the cart
  * @param checkout the checkoutContext
  * @param applicationContext and the applicationContext needed by the StatefulCommand to update the system
  */
case class Scan(itemId: String, checkout: Checkout)(implicit val applicationContext: ApplicationContext)
    extends StatefulCommand {
  override def action()(implicit applicationContext: ApplicationContext): Option[Checkout] =
    Option(checkout.scan(itemId))
}
