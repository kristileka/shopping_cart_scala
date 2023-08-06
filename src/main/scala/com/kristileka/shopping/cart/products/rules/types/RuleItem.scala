package com.kristileka.shopping.cart.products.rules.types

/**
  * This is a trait that represents what a rule will mandatory have. The Id of the Item and the Price
  */
trait RuleItem {
  val id: String
  val price: BigDecimal
}
