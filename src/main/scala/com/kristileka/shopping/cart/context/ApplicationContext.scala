package com.kristileka.shopping.cart.context

import com.kristileka.shopping.cart.products.Checkout
import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.Rule

import java.util.UUID

/**
  * The ApplicationContext which would symboilize the system information such as items rules and carts that are in memory
  * @param items The list of items that are possible
  * @param rules The list of rules for those items
  * @param carts The carts that are in memory
  */
case class ApplicationContext(
    items: Seq[Item],
    rules: Seq[Rule],
    var carts: Map[UUID, Checkout]
) {

  /**
    * Update Cart in memory
    * @param checkout the cart to be updated
    */
  def updateCart(checkout: Checkout): Unit = {
    carts -= checkout.id
    addCart(checkout.id, checkout)
  }

  /**
    * Add Cart in memory
    *
    * @param checkout the cart to be added
    */
  def addCart(uuid: UUID, checkout: Checkout): Unit =
    carts += (uuid -> checkout)

  /**
    * Update Cart in memory
    *
    * @param uuid the cart uuid to be deleted
    */
  def flushCart(uuid: UUID): Unit =
    carts -= (uuid)
}
