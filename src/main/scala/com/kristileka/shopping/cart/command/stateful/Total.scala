package com.kristileka.shopping.cart.command.stateful

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

/**
  * This Command will print the total the checkout will hold and bring the checkout back
 *
 * @param checkout The checkout context
  * @param applicationContext the ApplicationContext the StatefulCommand requires
  */
case class Total(checkout: Checkout)(implicit val applicationContext: ApplicationContext) extends StatefulCommand {

  override def action()(implicit applicationContext: ApplicationContext): Option[Checkout] = {
    show(s"Total price is ${checkout.total}")
    Option(checkout)
  }

}
