package com.jime.game.domain.model

//data class Player(
//    val name: String,
//    val playingPile: MutableList<Card>,
//    val discardPile: MutableList<Card> = mutableListOf()
//)


abstract class Player {
    abstract val name: String
    abstract val playingPile: MutableList<Card>
    abstract val discardPile: MutableList<Card>
}

data class Player1(
    override val name: String,
    override val playingPile: MutableList<Card>,
    override val discardPile: MutableList<Card> = mutableListOf()
): Player()

data class Player2(
    override val name: String,
    override val playingPile: MutableList<Card>,
    override val discardPile: MutableList<Card> = mutableListOf()
): Player()

fun Player.nextPlayingCardOrNull(): Card? {
    return playingPile.firstOrNull()
}

fun Player.hasCardsToPlay(): Boolean {
    return playingPile.isNotEmpty()
}

fun Player.removedPlayedCard(card: Card) {
    playingPile.remove(card)
}

fun Player.addPoints(cards: List<Card>) {
    discardPile.addAll(cards)
}

fun Player.getPoints(): Int {
    return discardPile.size
}