package coursera.adventure.unsafe

import coursera.adventure._
import coursera.adventure.Silver
import coursera.adventure.Gold
import coursera.extensions

object Adventure {
  def apply(): Adventure = new Adventure(){
    var eatenByMonster: Boolean = math.random < 0.5
    var treasureCost: Int = 42
  }
}

trait Adventure {
  import extensions._

  var eatenByMonster: Boolean
  var treasureCost: Int

  def collectCoins(): List[Coin] = {
    if (eatenByMonster) throw new GameOver("Ooops")
    List(Gold(), Gold(), Silver())
  }

  def buyTreasure(coins: List[Coin]): Treasure = {
    val enoughCoins = coins.map(x => x.value).sum > treasureCost
    if (!enoughCoins) throw new GameOver("Nice try!")
    Diamond()
  }

  def Play() : Unit = {
    val adventure = Adventure()
    val coins = adventure.collectCoins()
    val treasure = adventure.buyTreasure(coins)
  }

}

