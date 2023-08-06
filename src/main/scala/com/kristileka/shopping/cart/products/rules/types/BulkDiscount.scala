package com.kristileka.shopping.cart.products.rules.types

import com.kristileka.shopping.cart.products.rules.Rule

/**
  * This is a type of discount possible to a product. If you get a certain number of products
  * you will get a discount of them if they reach a required amount for it
  * @param itemId The id of the item this rule belongs to
  * @param requiredAmount the required amount for the rule to apply
  * @param discount The discount value that is going to be recieved in BigDcimal value 0.1 - 1.0
  */
case class BulkDiscount(itemId: String, requiredAmount: Int, discount: BigDecimal) extends Rule {
  override def apply[T <: RuleItem](items: Seq[T]): BigDecimal = {
    val relevantItems = items.filter(_.id == itemId)
    if (relevantItems.size >= requiredAmount)
      relevantItems.map(item => item.price * discount).sum
    else BigDecimal(0)
  }

  override val description: String =
    s"- If you buy more then $requiredAmount products of type $itemId you will get discount of ${discount * 100}%"
}
