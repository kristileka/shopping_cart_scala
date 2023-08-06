package com.kristileka.shopping.cart.command.stateless

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout
import com.kristileka.shopping.cart.products.item.{ Item, ItemFormat }

/**
  * This command will list the items possible to be scanned ore removed
  * @param checkout The checkout context that will be returned back
  * @param applicationContext The ApplicationContext to get the items from
  */
case class Items()(implicit checkout: Option[Checkout], applicationContext: ApplicationContext) extends Command {
  override def execute(): Option[Checkout] = {
    println(ItemFormat.printLine)
    println(ItemFormat.printHead)
    println(ItemFormat.printLine)
    applicationContext.items.foreach { item =>
      println(item.print)
    }
    println(ItemFormat.printLine)
    checkout
  }

}
