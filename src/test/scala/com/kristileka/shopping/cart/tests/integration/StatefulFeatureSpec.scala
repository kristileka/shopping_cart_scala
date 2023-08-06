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
        When("A checkoutContext is created")
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        Then("The inventory will keep it the same")
        checkoutContext = application.testRunner("inventory")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
      }
    }
    Scenario("Scan") {
      running {
        When("A checkoutContext is created")
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        val item = applicationContext.items.head
        And("Scan item is run")
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.get.items.nonEmpty)
        assert(checkoutContext.get.items.count(_.id == item.id) == 1)
        checkoutContext = application.testRunner(s"scan ${item.id}")
        Then("The CheckoutContext will update and add the item")
        assert(checkoutContext.get.items.count(_.id == item.id) == 2)
      }
    }
    Scenario("Remove") {
      running {
        When("A checkoutContext is created")
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        val item = applicationContext.items.head
        And("Scan item is run")
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.get.items.nonEmpty)
        assert(checkoutContext.get.items.count(_.id == item.id) == 1)
        Then("The CheckoutContext will update and add the item")

        And("If remove is done")
        checkoutContext = application.testRunner(s"remove ${item.id}")
        assert(checkoutContext.get.items.isEmpty)

        Then("The CheckoutContext will update and remove the item")
        assert(checkoutContext.get.items.count(_.id == item.id) == 0)
      }
    }

    Scenario("Total") {
      running {
        When("total is run without state")
        checkoutContext = application.testRunner("total")
        Then("the state will remain empty")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)
        When("cart is created")
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)
        And("total is run")
        checkoutContext = application.testRunner("total")
        assert(checkoutContext.isDefined)
        Then("the checkout will remain the same with before")
        assert(applicationContext.carts.nonEmpty)
      }
    }
  }

  override def items: Seq[Item] =
    arbitrary[Seq[Item]].sample.get

  override def rules: Seq[Rule] =
    arbitrary[Seq[BulkDiscount]].sample.get ++ arbitrary[Seq[QuantityDiscount]].sample.get
}
