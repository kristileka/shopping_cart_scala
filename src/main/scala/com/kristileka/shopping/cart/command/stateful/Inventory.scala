package com.kristileka.shopping.cart.command.stateful

import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout
import com.kristileka.shopping.cart.products.item.ItemFormat

/**
  * This Command will print the inventory the checkoutContext hold
  * @param checkout the checkout Context that will be send
  * @param applicationContext The necessary checkoutContext
  */
case class Inventory(checkout: Checkout)(implicit val applicationContext: ApplicationContext) extends StatefulCommand {
  override def action()(implicit applicationContext: ApplicationContext): Option[Checkout] = {
    println("Cart Inventory")
    println(ItemFormat.printHead)
    println(ItemFormat.printLine)
    checkout.items.foreach(item => {
      println(item.print)
    })
    println(ItemFormat.printLine)
    Option(checkout)
  }
}
