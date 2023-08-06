package com.kristileka.shopping.cart.tests.integration

import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.Rule
import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount }
import com.kristileka.shopping.cart.application.AppFeatureSpec
import org.scalacheck.Arbitrary.arbitrary
import com.kristileka.shopping.cart.generator.ArbitraryGenerators._

class StatefulFeatureSpec extends AppFeatureSpec {

  Feature("Inventory, Scan, Remove, Total") {
    Scenario("Inventory") {
      running {
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        checkoutContext = application.testRunner("inventory")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
      }
    }
    Scenario("Scan") {
      running {
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        val item = applicationContext.items.head
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.get.items.nonEmpty)
        assert(checkoutContext.get.items.count(_.id == item.id) == 1)
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.get.items.count(_.id == item.id) == 2)
      }
    }
    Scenario("Remove") {
      running {
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        val item = applicationContext.items.head
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.get.items.nonEmpty)
        assert(checkoutContext.get.items.count(_.id == item.id) == 1)
        checkoutContext = application.testRunner(s"remove ${item.id}")
        assert(checkoutContext.get.items.isEmpty)
        assert(checkoutContext.get.items.count(_.id == item.id) == 0)
      }
    }

    Scenario("Total") {
      running {
        checkoutContext = application.testRunner("total")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        checkoutContext = application.testRunner("total")
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
