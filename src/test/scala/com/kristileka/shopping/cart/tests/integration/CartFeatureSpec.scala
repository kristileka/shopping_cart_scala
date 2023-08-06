package com.kristileka.shopping.cart.tests.integration

import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.Rule
import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount }
import com.kristileka.shopping.cart.application.AppFeatureSpec
import org.scalacheck.Arbitrary.arbitrary
import com.kristileka.shopping.cart.generator.ArbitraryGenerators._

class CartFeatureSpec extends AppFeatureSpec {

  Feature("Cart Feature, creation, loading  and flushing") {
    Scenario("Cart creation") {
      running {
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.nonEmpty)
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.size == 2)
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.size == 3)

      }
    }

    Scenario("Cart Loading") {
      running {
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.nonEmpty)
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.size == 2)
        val item = applicationContext.items.head
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.isDefined)
        assert(checkoutContext.get.items.size == 1)
        val cartId = applicationContext.carts.head._1
        checkoutContext = application.testRunner(s"load $cartId")
        assert(checkoutContext.isDefined)
        assert(checkoutContext.get.items.isEmpty)
      }
    }

    Scenario("Cart Flushing") {
      running {
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.nonEmpty)
        checkoutContext = application.testRunner("flush")
        assert(applicationContext.carts.isEmpty)
      }
    }

  }

  override def items: Seq[Item] =
    arbitrary[Seq[Item]].sample.get

  override def rules: Seq[Rule] =
    arbitrary[Seq[BulkDiscount]].sample.get ++ arbitrary[Seq[QuantityDiscount]].sample.get
}
