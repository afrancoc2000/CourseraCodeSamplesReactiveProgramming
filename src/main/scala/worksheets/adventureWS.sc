import coursera.adventure.{Treasure, Coin}
import coursera.adventure.safe.Adventure

import scala.util.{Failure, Success, Try}

val adventure: Adventure = Adventure()

math.random
adventure.eatenByMonster = false // math.random < 0.5
adventure.treasureCost = (math.random * 1000).toInt

val coins: Try[List[Coin]] = adventure.collectCoins()

val treasure1: Try[Treasure] = coins match {
  case Success(cs)          => adventure.buyTreasure(cs)
  case Failure(t)           => Failure(t)
}

val treasure2: Try[Treasure] =
  coins.flatMap(cs => adventure.buyTreasure(cs))

val treasure3: Try[Treasure] = for {
  coins <- adventure.collectCoins()
  treasure <- adventure.buyTreasure(coins)
} yield treasure

