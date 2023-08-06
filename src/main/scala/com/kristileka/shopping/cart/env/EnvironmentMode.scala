package com.kristileka.shopping.cart.env

/**
  * This is an Environment variable to determine App Running and test
  */
sealed trait EnvironmentMode

/**
  * Test Environment
  */
case object TEST extends EnvironmentMode

/**
  * Running Environment
  */
case object PRODUCTION extends EnvironmentMode
