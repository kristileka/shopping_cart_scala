package com.kristileka.shopping.cart.application

import com.kristileka.shopping.cart.env.{ EnvironmentMode, TEST }
import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.Rule

/**
  * The Global initializer trait that has the Environment and the baseline for items and rules
  */
trait GlobalTest {
  def items: Seq[Item]
  def rules: Seq[Rule]
  val environment: EnvironmentMode = TEST
}
