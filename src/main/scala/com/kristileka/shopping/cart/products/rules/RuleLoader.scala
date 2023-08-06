package com.kristileka.shopping.cart.products.rules

/**
  * This class holds the data all possible rules can have to apply.
  * @param ruleType The ruleType to be matched later when created
  * @param itemId The itemId who this rule will apply to
  * @param requiredAmount A required amount constraint for the rule to be in function
  * @param freeAmount A Optional FreeAmount of it, if the rule is Quantity Discount
  * @param discount  A Optional Discount Double of it, if the rule is Bulk Discount
  */
case class RuleLoader(
    ruleType: String,
    itemId: String,
    requiredAmount: Int,
    freeAmount: Option[Int],
    discount: Option[Double]
)
