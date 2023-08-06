package com.kristileka.shopping.cart.products

import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.item.Item

import java.util.UUID

/**
  * The Checkout class to hold the items and the Id of the Cart
  * @param id the id of the cart in memory
  * @param items  the list of items that are in that cart
  */
case class Checkout(
    id: UUID,
    items: Seq[Item]
) {

  /**
    * Scan new item and return back the Checkout with the new item added
    * @param itemId The itemId to scan from
    * @param applicationContext the applicationContext where to get the new item from
    * @return The checkout with the new item added
    */
  def scan(itemId: String)(implicit applicationContext: ApplicationContext): Checkout =
    this.copy(
      items = items :+ applicationContext.items.find(_.id.toUpperCase == itemId.toUpperCase).get
    )

  /**
    * Remove item and return back the Checkout with the new item removed
    *
    * @param itemId             The itemId to remove
    * @return The checkout with the new item added
    */
  def remove(itemId: String): Checkout = {
    val (before, after) = items.span(_.id != itemId)
    this.copy(
      items = before ++ after.drop(1)
    )
  }

  /**
    * Get the total of the cart by suming the items and removing the discount
    * @param applicationContext the applicationContext to be able to run discount with the rules
    * @return The BigDecimal value of the total price
    */
  def total(implicit applicationContext: ApplicationContext): BigDecimal =
    items.map(item => item.price).sum - discount

  /**
    * Get the discount value by applying the rules to the cart
    * @param applicationContext The applicationContext to get the rules and apply them
    * @return The discounted Value
    */
  def discount(implicit applicationContext: ApplicationContext): BigDecimal =
    applicationContext.rules.map(_.apply(items)).sum
}
