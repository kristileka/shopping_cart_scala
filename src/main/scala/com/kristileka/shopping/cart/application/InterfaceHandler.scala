package com.kristileka.shopping.cart.application

import com.kristileka.shopping.cart.command.Command

/**
  * A trait to start the shell correctly and print on the interface some data cleaning
  * outputs.
  */
trait InterfaceHandler {
  println("")
  println("=> Welcome <=")
  println(s"=> Press the commend  ${Command.HELP}  to get a list of all operations <=")
  def shellWriter(): Unit  = print("System> ")
  def goodByeShell(): Unit = println("Goodbye !")
}
