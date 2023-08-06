package com.kristileka.shopping.cart.generator

import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.Rule
import com.kristileka.shopping.cart.products.rules.types.{ BulkDiscount, QuantityDiscount }
import org.scalacheck.{ Arbitrary, Gen }

/**
  * This class generates the Arbitrary values for different data
  * needed through tests to add them different each time
  */
object ArbitraryGenerators {

  //Primitive Generators

  val genBigDecimal: Gen[BigDecimal] = Gen.choose(BigDecimal(0.1), BigDecimal(1.0))

  val getInt: Gen[Int] = Gen.choose(1, 5)

  //Item Generators

  val genItem: Gen[Item] = for {
    id    <- Gen.alphaStr
    price <- Gen.posNum[Int]
    name  <- Gen.alphaStr
  } yield Item(id, price, name)
  implicit val arbItem: Arbitrary[Item] = Arbitrary(genItem)

  //Items Generator

  val genItemList: Gen[Seq[Item]]                = Gen.listOf(genItem)
  implicit val arbItemList: Arbitrary[Seq[Item]] = Arbitrary(genItemList)

  //BulkDiscount Generator
  val genBulkRule: Gen[BulkDiscount] = for {
    id             <- Gen.alphaStr
    requiredAmount <- Arbitrary(getInt).arbitrary
    discount       <- Arbitrary(genBigDecimal).arbitrary
  } yield BulkDiscount(id, requiredAmount, discount)

  val genBulkRuleList: Gen[Seq[BulkDiscount]]                = Gen.listOf(genBulkRule)
  implicit val arbBulkRuleList: Arbitrary[Seq[BulkDiscount]] = Arbitrary(genBulkRuleList)

  //QuantityDiscount Generator
  val genQuantityDiscount: Gen[QuantityDiscount] = for {
    id             <- Gen.alphaStr
    requiredAmount <- Arbitrary(getInt).arbitrary
    freeAmount     <- Arbitrary(getInt).arbitrary
  } yield QuantityDiscount(id, requiredAmount, freeAmount)

  val genQuantityRuleList: Gen[Seq[QuantityDiscount]]                = Gen.listOf(genQuantityDiscount)
  implicit val arbQuantityRuleList: Arbitrary[Seq[QuantityDiscount]] = Arbitrary(genQuantityRuleList)

  //Rules Generator

}
