package com.kristileka.shopping.cart

import com.kristileka.shopping.cart.application.Global

/**
  * The Application Start which is the initial Point of the app that generates the Context through
  * the Global trait and starts the system with the PRODUCTION environment
  */
object ApplicationStarter extends App with Global {
  Starter(environment).run()
}
