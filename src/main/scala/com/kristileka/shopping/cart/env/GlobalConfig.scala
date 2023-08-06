package com.kristileka.shopping.cart.env

import com.kristileka.shopping.cart.env.GlobalConfig.loadDefaultConfig
import com.kristileka.shopping.cart.products.item.Item
import com.kristileka.shopping.cart.products.rules.{ Rule, RuleLoader }
import com.typesafe.config.{ Config, ConfigFactory, ConfigObject }

import scala.jdk.CollectionConverters.CollectionHasAsScala

/**
  * The GlobalConfiguration class that will get the rules and items form config
  * @param config The Config that is going to be gettting the data from
  */
class GlobalConfig(
    config: Config = loadDefaultConfig
) {

  /**
    * The function to get the rules from application.conf file
    * @return The Seq of rules
    */
  def getRules: Seq[Rule] = {
    val ruleList = config.getObjectList("rules").asScala.toList
    val rules = ruleList.map { configObject: ConfigObject =>
      val ruleConfig = configObject.toConfig
      RuleLoader(
        ruleConfig.getString("ruleType"),
        ruleConfig.getString("itemId"),
        ruleConfig.getInt("requiredAmount"),
        if (ruleConfig.hasPath("freeAmount")) Some(ruleConfig.getInt("freeAmount")) else None,
        if (ruleConfig.hasPath("discount")) Some(ruleConfig.getDouble("discount")) else None
      )
    }
    rules.flatMap(Rule.fromRuleLoader)
  }

  /**
    * The function to get the items from application.conf file
    *
    * @return The Seq of items
    */
  def getItems: Seq[Item] = {
    val itemsList = config.getObjectList("items").asScala.toList
    itemsList.map { configObject: ConfigObject =>
      val itemConfig = configObject.toConfig
      Item(
        itemConfig.getString("id"),
        itemConfig.getInt("price"),
        itemConfig.getString("name"),
      )
    }
  }
}

object GlobalConfig {

  private def loadDefaultConfig: Config =
    ConfigFactory.load("application.conf")
}
