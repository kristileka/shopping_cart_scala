package com.kristileka.shopping.cart.products.rules

import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount, RuleItem }

/**
  * This trait holds the data a rule needs to apply its function. it will need a function to apply the rules
  * and a description value to show the rule in shell
  */
trait Rule {
  def apply[T <: RuleItem](item: Seq[T]): BigDecimal
  val description: String
}

/**
  * This object will load rules from a RuleLoader class. It will match them accordingly and create the proper
  * Rule to add back if it can be possible
  */
object Rule {
  def fromRuleLoader(ruleLoader: RuleLoader): Option[Rule] =
    ruleLoader.ruleType match {
      case "QuantityDiscount" =>
        ruleLoader.freeAmount.map(
          freeAmount => QuantityDiscount(ruleLoader.itemId, ruleLoader.requiredAmount, freeAmount)
        )
      case "BulkDiscount" =>
        ruleLoader.discount.map(discount => BulkDiscount(ruleLoader.itemId, ruleLoader.requiredAmount, discount))
      case _ => None
    }
}
