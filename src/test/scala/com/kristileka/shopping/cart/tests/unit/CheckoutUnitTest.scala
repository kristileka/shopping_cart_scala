package com.kristileka.shopping.cart.tests.unit

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.command.stateful.Total
import com.kristileka.shopping.cart.command.stateless.CreateCart
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout
import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount }
import org.scalatest.BeforeAndAfterEach
import org.scalatest.featurespec.AnyFeatureSpec

import java.util.UUID

class CheckoutUnitTest extends AnyFeatureSpec with BeforeAndAfterEach {
  implicit val applicationContext: ApplicationContext = ApplicationContext(
    Seq(Item("MUG", 4, "Triggerise Mug"),
        Item("TSHIRT", 21, "Triggerise Tshirt"),
        Item("USBKEY", 10, "Triggerise USB Key")),
    Seq(QuantityDiscount("MUG", 2, 1), BulkDiscount("TSHIRT", 3, 0.3)),
    Map.empty
  )
  override def beforeEach(): Unit =
    super.beforeEach()

  Feature("Checkout Unit Test") {
    Scenario("Cart creation and add items") {
      var checkout   = Checkout(UUID.randomUUID(), Seq())
      val addingItem = "MUG"
      checkout = checkout.scan(addingItem)
      assert(checkout.items.size == 1)
      checkout = checkout.scan(addingItem).scan(addingItem).scan(addingItem)
      assert(checkout.items.size == 4)
      checkout.items.foreach(item => assert(item.id == addingItem))
    }

    Scenario("Cart creation and remove items") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = "MUG"
      checkout = checkout.scan(item).scan(item).scan(item)
      assert(checkout.items.size == 3)
      checkout = checkout.remove(item)
      assert(checkout.items.size == 2)
      checkout = checkout.remove("random")
      assert(checkout.items.size == 2)
      checkout = checkout.remove(item).remove(item).remove(item)
      assert(checkout.items.isEmpty)
      checkout = checkout.remove(item).remove(item).remove(item)
      assert(checkout.items.isEmpty)
    }

    Scenario("Regular total") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = applicationContext.items.filter(_.id == "USBKEY").head
      checkout = checkout.scan(item.id).scan(item.id).scan(item.id)
      assert(checkout.items.size == 3)
      val total = checkout.total
      assert(total == item.price * 3)
    }

    Scenario("Discounted total - Quantity discount") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = applicationContext.items.filter(_.id == "MUG").head
      checkout = checkout.scan(item.id).scan(item.id).scan(item.id)
      assert(checkout.items.size == 3)
      var total = checkout.total
      assert(total == item.price * 2)
      assert(checkout.items.size == 3)
      checkout = checkout.scan(item.id)
      total = checkout.total
      assert(total == item.price * 2)
      assert(checkout.items.size == 4)
      checkout = checkout.scan(item.id)
      total = checkout.total
      assert(total == item.price * 3)
      assert(checkout.items.size == 5)
    }

    Scenario("Discounted total - Bulk discount") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = applicationContext.items.filter(_.id == "TSHIRT").head
      checkout = checkout.scan(item.id).scan(item.id).scan(item.id)
      assert(checkout.items.size == 3)
      val total = checkout.total
      assert(total == (item.price * BigDecimal(3.0) * BigDecimal(0.7)))
    }
  }
}
