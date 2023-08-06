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
        Given("An empty Checkout Context")
        checkoutContext = application.testRunner("cc")
        Then("You can fill it using the cc command")
        assert(applicationContext.carts.nonEmpty)
        And("The context will continue to be changed")
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.size == 2)
        And("The system will get the new carts")
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.size == 3)

      }
    }

    Scenario("Cart Loading") {
      running {
        Given("A cart to the system")
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.nonEmpty)
        checkoutContext = application.testRunner("cc")
        Then("Another cart is created")
        assert(applicationContext.carts.size == 2)
        val item = applicationContext.items.head
        And("An item is added to the cart")
        checkoutContext = application.testRunner(s"scan ${item.id}")
        assert(checkoutContext.isDefined)
        assert(checkoutContext.get.items.size == 1)
        val cartId = applicationContext.carts.head._1
        And("If we load the previous cart")
        checkoutContext = application.testRunner(s"load $cartId")
        assert(checkoutContext.isDefined)
        Then("The context has changed to the previous one with no items")
        assert(checkoutContext.get.items.isEmpty)
      }
    }

    Scenario("Cart Flushing") {
      running {
        When("A checkoutContext is given")
        checkoutContext = application.testRunner("cc")
        assert(applicationContext.carts.nonEmpty)
        Then("You can delete it by running flush")
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
