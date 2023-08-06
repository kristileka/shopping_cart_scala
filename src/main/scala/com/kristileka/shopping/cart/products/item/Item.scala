package com.kristileka.shopping.cart.products.item

import com.kristileka.shopping.cart.products.rules.types.RuleItem

/**
  * This class has the key elements of a product to be created.
  * @param id THe id of the product
  * @param price the price of the product
  * @param name the name of the product
  */
case class Item(
    id: String,
    price: BigDecimal,
    name: String
) extends RuleItem {
  def print: String = String.format("%-30s | %-30s | %-30s", id, name, price.toString)
}

/**
  * This class is a Formatter for the Item to print a header and a seperator for all 3 possible properties,
  * to make printing in the shell easier.
  */
object ItemFormat {
  def printHead: String =
    String.format("%-30s | %-30s | %-30s", "Id", "Name", "Price")

  def printLine: String =
    String.format("%-30s-|-%-30s-|-%-30s",
                  "------------------------------",
                  "------------------------------",
                  "------------------------------")
}
