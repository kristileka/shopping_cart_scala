package com.kristileka.shopping.cart.command.stateless

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.products.Checkout

/**
  * This is a helper command that will show the list of all possible commands that can be performed
 *
  * @param checkout the checkoutContext return back if created
  */
case class Help()(implicit checkout: Option[Checkout]) extends Command {
  override def execute(): Option[Checkout] = {
    show(
      prepareHelpString()
    )
    checkout
  }

  private def prepareHelpString(): String =
    """
      | => CART OPERATION
      |     CC:
      |       Create a new Cart maintaining the current one in memory.
      |       COMMAND => cc
      |     LOAD:
      |       Load a cart from in memory with cart Id.
      |       COMMAND => load <:cartId>
      |     FLUSH:
      |       Delete current cart from memory.
      |       COMMAND => flush
      | => STATEFUL
      |     SCAN:
      |       Scan a product in cart using
      |       COMMAND => scan <:itemId>
      |     REMOVE:
      |       remove a product from cart
      |       COMMAND => remove <:itemId>
      |     TOTAL:
      |       get the total price of the cart
      |       COMMAND => total
      |     INVENTORY:
      |       get the list of items of that cart
      |       COMMAND => inventory
      | => STATELESS
      |     ITEMS:
      |       List all possible items
      |       COMMAND => items
      |     DISCOUNTS
      |       List all discounts
      |       COMMAND => discounts
      |     HELP: 
      |       Show possible commands
      |       COMMAND => help
      |
      |""".stripMargin

}
