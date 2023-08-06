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
        When("discount is run without state")
        checkoutContext = application.testRunner("discount")
        Then("the state will remain the same empty")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)

        Then("a state is created")
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)

        And("discount is run with state")
        checkoutContext = application.testRunner("discount")
        assert(checkoutContext.isDefined)
        Then("the state will be remain defined as well")
        assert(applicationContext.carts.nonEmpty)
      }
    }
    Scenario("Help") {
      running {
        When("Help is run without state")

        checkoutContext = application.testRunner("help")
        Then("the state will remain the same empty")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)

        Then("a state is created")
        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)

        And("help is run with state")
        checkoutContext = application.testRunner("help")
        assert(checkoutContext.isDefined)
        Then("the state will be remain defined as well")
        assert(applicationContext.carts.nonEmpty)
      }
    }

    Scenario("items") {
      running {

        When("items is run without state")
        checkoutContext = application.testRunner("items")
        Then("the state will remain the same empty")
        assert(checkoutContext.isEmpty)
        assert(applicationContext.carts.isEmpty)

        Then("a state is created")

        checkoutContext = application.testRunner("cc")
        assert(checkoutContext.isDefined)
        assert(applicationContext.carts.nonEmpty)

        And("items is run with state")
        checkoutContext = application.testRunner("help")
        assert(checkoutContext.isDefined)
        Then("the state will be remain defined as well")
        assert(applicationContext.carts.nonEmpty)
      }
    }
  }

  override def items: Seq[Item] =
    arbitrary[Seq[Item]].sample.get

  override def rules: Seq[Rule] =
    arbitrary[Seq[BulkDiscount]].sample.get ++ arbitrary[Seq[QuantityDiscount]].sample.get
}
