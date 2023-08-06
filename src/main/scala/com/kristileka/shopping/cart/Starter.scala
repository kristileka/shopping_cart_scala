package com.kristileka.shopping.cart

import com.kristileka.shopping.cart.command.Command
import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.env.{ EnvironmentMode, TEST }
import com.kristileka.shopping.cart.application.InterfaceHandler
import com.kristileka.shopping.cart.application.exception.{ ExitException, WrongEnvironment }
import com.kristileka.shopping.cart.products.Checkout

import scala.io.StdIn

/**
  * This is a starter class the requires and environment and an applicationContext to run
  * @param environmentMode The environmentMode which the application will run into.
  *                        This is for test and running purposes
  * @param applicationContext A application Context which needs to be initialized which will hold
  *                           the items, the ruls and the carts in memory(Symbolizing a storage place)
  */
case class Starter(environmentMode: EnvironmentMode)(implicit val applicationContext: ApplicationContext)
    extends InterfaceHandler {

  implicit var checkoutContext: Option[Checkout] = None

  /**
    * This is a recursive function that can be interrupted by the shell and command only
    * to run the application
    */
  final def run(): Unit = {
    if (environmentMode == TEST)
      throw WrongEnvironment()
    try {
      checkoutContext = Command.from(StdIn.readLine()).execute()
      shellWriter()
      run()
    } catch {
      case _: ExitException => goodByeShell()
      case _: Exception =>
        shellWriter()
        run()
    }
  }

  /**
    * This is a function to execute a command as the shell would but from a test perspective
    * @param value The command value we want to run
    * @param checkoutContext the checkoutContext that would usually be created but in this sceario
    *                         given by the test
    * @return The checkoutContext that will be update deleted or created during tests
    */
  def testRunner(value: String)(implicit checkoutContext: Option[Checkout]): Option[Checkout] =
    if (environmentMode == TEST)
      Command.from(value).execute()
    else None

}
