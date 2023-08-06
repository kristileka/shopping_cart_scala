package com.kristileka.shopping.cart.command.stateful

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout

/**
  * This is a trait that will be wrapped around the Command class and will do a update
  * on the applicationContext in order to keep the new updates on state under the application
  * and on the System as well
  */
trait StatefulCommand extends Command {

  implicit def applicationContext: ApplicationContext

  def action()(implicit applicationContext: ApplicationContext): Option[Checkout]

  override def execute(): Option[Checkout] = {
    val result = action()
    result.foreach(applicationContext.updateCart)
    result
  }
}
