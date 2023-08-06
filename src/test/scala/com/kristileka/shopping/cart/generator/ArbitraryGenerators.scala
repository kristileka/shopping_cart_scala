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

  /**
    * Generate Big Decimals from 0.1 to 1.0
    */
  val genBigDecimal: Gen[BigDecimal] = Gen.choose(BigDecimal(0.1), BigDecimal(1.0))

  /**
    * Generate Random Ints from 1 to 5
    */
  val getInt: Gen[Int] = Gen.choose(1, 5)

  /**
    * Generate an Item object
    */
  val genItem: Gen[Item] = for {
    id    <- Gen.alphaStr
    price <- Gen.posNum[Int]
    name  <- Gen.alphaStr
  } yield Item(id, price, name)

  /**
    * The generator for Sequence of Items
    */
  val genItemSeq: Gen[Seq[Item]] = Gen.listOf(genItem)

  /**
    * The implicit Arbitrary of the Sequence of Items
    */
  implicit val arbItemSeq: Arbitrary[Seq[Item]] = Arbitrary(genItemSeq)

  /**
    * The BulkDiscount Generator
    */
  val genBulkRule: Gen[BulkDiscount] = for {
    id             <- Gen.alphaStr
    requiredAmount <- Arbitrary(getInt).arbitrary
    discount       <- Arbitrary(genBigDecimal).arbitrary
  } yield BulkDiscount(id, requiredAmount, discount)

  /**
    * The BulkDiscount Sequence generator
    */
  val genBulkRuleSeq: Gen[Seq[BulkDiscount]] = Gen.listOf(genBulkRule)

  /**
    * The implicit Arbitrary generator for the Bulk Discount
    */
  implicit val arbBulkRuleSeq: Arbitrary[Seq[BulkDiscount]] = Arbitrary(genBulkRuleSeq)

  /**
    * The QuantityDiscount Generator
    */
  val genQuantityDiscount: Gen[QuantityDiscount] = for {
    id             <- Gen.alphaStr
    requiredAmount <- Arbitrary(getInt).arbitrary
    freeAmount     <- Arbitrary(getInt).arbitrary
  } yield QuantityDiscount(id, requiredAmount, freeAmount)

  /**
    * The QuantityDiscount Seq Generator
    */
  val genQuantityRuleSeq: Gen[Seq[QuantityDiscount]] = Gen.listOf(genQuantityDiscount)

  /**
    * The implicit Arbitrary for Sequence of QuantityDiscount
    */
  implicit val arbQuantityRuleSeq: Arbitrary[Seq[QuantityDiscount]] = Arbitrary(genQuantityRuleSeq)

  //Rules Generator

}
