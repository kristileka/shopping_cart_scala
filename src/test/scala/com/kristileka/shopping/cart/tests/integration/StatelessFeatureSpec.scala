package com.kristileka.shopping.cart.tests.integration

import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.Rule
import com.kristileka.shopping.cart.application.AppFeatureSpec
import org.scalacheck.Arbitrary.arbitrary
import com.kristileka.shopping.cart.generator.ArbitraryGenerators._
import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount }

class StatelessFeatureSpec extends AppFeatureSpec {

  Feature("Discount, Help, items") {
    Scenario("Discount") {
      running {
        checkoutContext = application.testRunner("discount")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
      }
    }
    Scenario("Help") {
      running {
        checkoutContext = application.testRunner("help")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
      }
    }

    Scenario("items") {
      running {
        checkoutContext = application.testRunner("items")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
      }
    }
  }

  override def items: Seq[Item] =
    arbitrary[Seq[Item]].sample.get

  override def rules: Seq[Rule] =
    arbitrary[Seq[BulkDiscount]].sample.get ++ arbitrary[Seq[QuantityDiscount]].sample.get
}
