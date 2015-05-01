import scala.util.Try

type Coin
type Treasure

trait Adventure{
  def collectCoins(): Try[List[Coin]] = {???}
  def buyTreasure(coins: List[Coin]): Try[Treasure] = ???
}

val adventure = new Adventure()
val coins = adventure.collectCoins()
val treasure = adventure.buyTreasure(coins)