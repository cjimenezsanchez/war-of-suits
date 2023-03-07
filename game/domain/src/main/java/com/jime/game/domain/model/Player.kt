package com.jime.game.domain.model

//data class Player(
//    val name: String,
//    val playingPile: MutableList<Card>,
//    val discardPile: MutableList<Card> = mutableListOf()
//)


abstract class Player {
    abstract var name: String
    abstract var playingPile: MutableList<Card>
    abstract var discardPile: MutableList<Card>
}

data class Player1(
    override var name: String,
    override var playingPile: MutableList<Card>,
    override var discardPile: MutableList<Card> = mutableListOf()
): Player()

data class Player2(
    override var name: String,
    override var playingPile: MutableList<Card>,
    override var discardPile: MutableList<Card> = mutableListOf()
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