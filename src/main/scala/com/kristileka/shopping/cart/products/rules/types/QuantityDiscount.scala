package com.kristileka.shopping.cart.products.rules.types

import com.kristileka.shopping.cart.products.rules.Rule

/**
  * This is a type of discount that can be set for a product. This means you will get an y amount of item for free
  * if you buy an x amount for the product this rule applies to
  * @param itemId THe itemId this rule will belong to
  * @param requiredAmount The requiredAmount this rule will apply to
  * @param freeAmount And the number of products that are free if the required amount will be fulfilled
  */
case class QuantityDiscount(itemId: String, requiredAmount: Int, freeAmount: Int) extends Rule {
  override def apply[T <: RuleItem](items: Seq[T]): BigDecimal = {
    val relevantItems  = items.filter(_.id == itemId)
    val discountGroups = relevantItems.size / requiredAmount
    (discountGroups * relevantItems.headOption
      .map(_.price)
      .getOrElse(BigDecimal(0))) * BigDecimal(freeAmount)
  }

  override val description: String =
    s"- If you buy more then $requiredAmount products of type $itemId you will get $freeAmount for free"
}
