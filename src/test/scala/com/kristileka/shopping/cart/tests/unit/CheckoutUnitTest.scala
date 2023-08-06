package com.kristileka.shopping.cart.tests.unit

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.command.stateful.Total
import com.kristileka.shopping.cart.command.stateless.CreateCart
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout
import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount }
import org.scalatest.{ BeforeAndAfterEach, GivenWhenThen }
import org.scalatest.featurespec.AnyFeatureSpec

import java.util.UUID

class CheckoutUnitTest extends AnyFeatureSpec with BeforeAndAfterEach with GivenWhenThen {
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
      When("a checkout is created")
      var checkout   = Checkout(UUID.randomUUID(), Seq())
      val addingItem = "MUG"
      And("An item is scanned")
      checkout = checkout.scan(addingItem)
      Then("the item will be added to the new checkout")
      assert(checkout.items.size == 1)

      Then("if more items are added ")
      checkout = checkout.scan(addingItem).scan(addingItem).scan(addingItem)
      Then("more will be stored")
      assert(checkout.items.size == 4)
      checkout.items.foreach(item => assert(item.id == addingItem))
    }

    Scenario("Cart creation and remove items") {

      When("a checkout is created")
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = "MUG"
      And("an item is scanned")
      checkout = checkout.scan(item).scan(item).scan(item)
      assert(checkout.items.size == 3)
      Then("the item ill be added ")
      Then("if item is removed ")
      checkout = checkout.remove(item)
      assert(checkout.items.size == 2)
      Then("the checkout will have the item removed")

      And("if the item is misspelled or not found")
      checkout = checkout.remove("random")
      Then("no changes will be made")
      assert(checkout.items.size == 2)
      And("if the items are removed the cart can go empty")
      checkout = checkout.remove(item).remove(item).remove(item)
      assert(checkout.items.isEmpty)
      And("if more items are removed")
      checkout = checkout.remove(item).remove(item).remove(item)
      Then("the cart will remain empty and not on minus")
      assert(checkout.items.isEmpty)
    }

    Scenario("Regular total") {
      When("a checkout is created")
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = applicationContext.items.filter(_.id == "USBKEY").head
      And("an item is added that is not discounted")
      checkout = checkout.scan(item.id).scan(item.id).scan(item.id)
      assert(checkout.items.size == 3)
      Then("the total will be the items summed together")
      val total = checkout.total
      assert(total == item.price * 3)
    }

    Scenario("Discounted total - Quantity discount") {
      When("an item is added")
      var checkout = Checkout(UUID.randomUUID(), Seq())
      val item     = applicationContext.items.filter(_.id == "MUG").head
      And("the item has a discount")
      checkout = checkout.scan(item.id).scan(item.id).scan(item.id)
      Then("the total price will apply the discount")
      assert(checkout.items.size == 3)
      var total = checkout.total
      Then("If the item is updated and can handle 2 of the discounts")
      assert(total == item.price * 2)
      Then("the discount will be applied")
      assert(checkout.items.size == 3)
      checkout = checkout.scan(item.id)
      total = checkout.total
      And("will continue to have them")
      assert(total == item.price * 2)
      assert(checkout.items.size == 4)
      checkout = checkout.scan(item.id)
      total = checkout.total
      assert(total == item.price * 3)
      assert(checkout.items.size == 5)
    }

    Scenario("Discounted total - Bulk discount") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      When("an item is added and have another type of discount")
      val item = applicationContext.items.filter(_.id == "TSHIRT").head
      checkout = checkout.scan(item.id).scan(item.id).scan(item.id)
      Then("the discount will be applied to that item")
      assert(checkout.items.size == 3)
      val total = checkout.total
      assert(total == (item.price * BigDecimal(3.0) * BigDecimal(0.7)))
    }

    Scenario("No discount Type 1") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      When("Three items are given and no discount can be applied")
      val itemTshirt = applicationContext.items.filter(_.id == "TSHIRT").head
      val itemMug    = applicationContext.items.filter(_.id == "MUG").head
      val itemUsb    = applicationContext.items.filter(_.id == "USBKEY").head
      checkout = checkout.scan(itemTshirt.id).scan(itemMug.id).scan(itemUsb.id)
      Then("all the items will be summed Up")
      val total = checkout.total
      assert(total == 35.00)
    }

    Scenario("Discount type 2") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      When("three items are given and 2 of them are on 1 discount")
      val itemTshirt = applicationContext.items.filter(_.id == "TSHIRT").head
      val itemMug    = applicationContext.items.filter(_.id == "MUG").head
      Then("the discount will be applied will work succesfully")
      checkout = checkout.scan(itemMug.id).scan(itemMug.id).scan(itemTshirt.id)
      val total = checkout.total
      assert(total == 25.00)
    }

    Scenario("Discount type 3") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      When("5 items are given and 4 of them are in a discount")
      val itemTshirt = applicationContext.items.filter(_.id == "TSHIRT").head
      val itemMug    = applicationContext.items.filter(_.id == "MUG").head
      checkout = checkout.scan(itemTshirt.id).scan(itemTshirt.id).scan(itemTshirt.id).scan(itemTshirt.id)
      Then("only the 4 will be on discount the other will be regular")
      checkout = checkout.scan(itemMug.id)
      val total = checkout.total
      assert(total == 62.80)
    }
    Scenario("Discount type 4") {
      var checkout = Checkout(UUID.randomUUID(), Seq())
      When("7 items are given")
      val itemTshirt = applicationContext.items.filter(_.id == "TSHIRT").head
      val itemMug    = applicationContext.items.filter(_.id == "MUG").head
      val itemUsb    = applicationContext.items.filter(_.id == "USBKEY").head
      And("and 3 of them are on discount, and 2 of the others too")
      checkout = checkout.scan(itemTshirt.id).scan(itemTshirt.id).scan(itemTshirt.id)
      checkout = checkout.scan(itemMug.id).scan(itemMug.id).scan(itemMug.id).scan(itemUsb.id)
      Then("the disocunt will be applied accordingly")
      val total = checkout.total
      assert(total == 62.10)
    }
  }
}
