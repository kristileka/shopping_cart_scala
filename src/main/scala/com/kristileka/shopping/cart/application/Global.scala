package com.kristileka.shopping.cart.application

import com.kristileka.shopping.cart.context.ApplicationContext
import com.kristileka.shopping.cart.env.{ EnvironmentMode, GlobalConfig, PRODUCTION }
import com.kristileka.shopping.cart.products.Checkout

trait Global {
  val environment: EnvironmentMode = PRODUCTION
  val config: GlobalConfig         = new GlobalConfig()
  implicit val applicationContext: ApplicationContext =
    ApplicationContext(config.getItems, config.getRules, Map.empty)
}
