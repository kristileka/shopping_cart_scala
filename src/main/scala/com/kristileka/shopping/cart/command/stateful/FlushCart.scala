package com.kristileka.shopping.cart.command.stateful

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

/**
  * This command will delete the cart from the system in memory
 *
 * @param checkout The checkout which will be sent and deleted
  * @param applicationContext The application context where the cart will be deleted too
  */
case class FlushCart(checkout: Checkout)(implicit applicationContext: ApplicationContext) extends Command {
  override def execute(): Option[Checkout] = {
    applicationContext.flushCart(
      checkout.id
    )
    None
  }
}
