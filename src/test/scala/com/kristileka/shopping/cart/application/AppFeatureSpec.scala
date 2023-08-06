package com.kristileka.shopping.cart.application

import com.kristileka.shopping.cart.Starter
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.products.Checkout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{ BeforeAndAfterEach, GivenWhenThen }

/**
  * This is a Abstract Class that will run the application for integration Test
  * generate an ApplicationContext,CheckoutContext and an Application Starter on test
  * environment and run them before each scenarion
  */
abstract class AppFeatureSpec
    extends AnyFeatureSpec
    with GivenWhenThen
    with ScalaFutures
    with BeforeAndAfterEach
    with GlobalTest {

  implicit var applicationContext: ApplicationContext = _
  implicit var checkoutContext: Option[Checkout]      = _
  var application: Starter                            = _

  override def beforeEach(): Unit = {
    super.beforeEach()
    applicationContext = ApplicationContext(items, rules, Map.empty)
    application = Starter(environment)
    checkoutContext = None
  }

  def running(testCode: => Unit): Unit =
    testCode

}
