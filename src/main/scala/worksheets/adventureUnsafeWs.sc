import coursera.adventure.unsafe.Adventure

val adventure: Adventure = Adventure()

math.random
adventure.eatenByMonster = false // math.random < 0.5
adventure.treasureCost = (math.random * 1000).toInt

val coins = adventure.collectCoins()

val treasure = adventure.buyTreasure(coins)
