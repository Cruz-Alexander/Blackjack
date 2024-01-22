package blackjack;

import java.util.*;

public class Blackjack implements BlackjackEngine {
    private List<Card> gameDeck;
    private List<Card> dealerCards;
    private List<Card> playerCards;
    private int playerAccount;
    private int initialBet;
    private int numberOfDecks;
    private Random randomGenerator;

    /**
     * Constructor you must provide. Initializes the player's account to 200 and the
     * initial bet to 5. Feel free to initialize any other fields. Keep in mind that
     * the constructor does not define the deck(s) of cards.
     *
     * @param randomGenerator
     * @param numberOfDecks
     */
    public Blackjack(Random randomGenerator, int numberOfDecks) {
        this.randomGenerator = randomGenerator;
        this.numberOfDecks = numberOfDecks;
        playerAccount = 200;
        initialBet = 5;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void createAndShuffleGameDeck() {
        gameDeck = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            for (CardSuit suit : CardSuit.values()) {
                for (CardValue value : CardValue.values()) {
                    gameDeck.add(new Card(value, suit));
                }
            }
        }
        Collections.shuffle(gameDeck, randomGenerator);
    }

    public Card[] getGameDeck() {
        return gameDeck.toArray(new Card[0]);
    }

    public void deal() {
        createAndShuffleGameDeck();

        dealerCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        playerCards.add(drawCard());
        dealerCards.add(drawCardDown());
        playerCards.add(drawCard());
        dealerCards.add(drawCard());
    }

    public Card[] getDealerCards() {
        return dealerCards.toArray(new Card[0]);
    }

    public int[] getDealerCardsTotal() {
        return calculatePossibleTotals(dealerCards);
    }

    public int getDealerCardsEvaluation() {
        int total = calculateCardsTotal(dealerCards);
        if (total > 21) {
            return BUST;
        } else if (total == 21 && dealerCards.size() == 2) {
            return BLACKJACK;
        } else {
            return LESS_THAN_21;
        }
    }

    public Card[] getPlayerCards() {
        return playerCards.toArray(new Card[0]);
    }

    public int[] getPlayerCardsTotal() {
        return calculatePossibleTotals(playerCards);
    }

    public int getPlayerCardsEvaluation() {
        int total = calculateCardsTotal(playerCards);
        if (total > 21) {
            return BUST;
        } else if (total == 21 && playerCards.size() == 2) {
            return BLACKJACK;
        } else {
            return LESS_THAN_21;
        }
    }

    public void playerHit() {
        playerCards.add(drawCard());
        if (getPlayerCardsEvaluation() == BUST) {
            dealerCards.get(0).setFaceUp();
        }
    }

    public void playerStand() {
        dealerCards.get(0).setFaceUp();
        if (getPlayerCardsEvaluation() == BLACKJACK) {
            return;
        }
        while (getDealerCardsEvaluation() == LESS_THAN_21 && calculateCardsTotal(dealerCards) < 16) {
            dealerCards.add(drawCard());
        }
    }

    public int getGameStatus() {
        int playerEvaluation = getPlayerCardsEvaluation();
        int dealerEvaluation = getDealerCardsEvaluation();

        if (playerEvaluation == BLACKJACK && dealerEvaluation == BLACKJACK) {
            return 1; // Tie
        } else if (playerEvaluation == BLACKJACK) {
            setAccountAmount(playerAccount = playerAccount + initialBet);
            return 7; // Player wins
        } else if (dealerEvaluation == BLACKJACK) {
            setAccountAmount(playerAccount = playerAccount - initialBet);
            dealerCards.get(0).setFaceUp();
            return 6; // Dealer wins
        } else if (playerEvaluation == BUST) {
            setAccountAmount(playerAccount = playerAccount - initialBet);
            return 6; // Dealer wins
        } else if (dealerEvaluation == BUST) {
            setAccountAmount(playerAccount = playerAccount + initialBet);
            return 7; // Player wins
        } else if (playerEvaluation == LESS_THAN_21 && dealerEvaluation == LESS_THAN_21) {
            int playerTotal = 0;
            int dealerTotal = 0;
            if (getPlayerCardsTotal().length > 1) {
                if (getPlayerCardsTotal()[0] < getPlayerCardsTotal()[1]) {
                    playerTotal = getPlayerCardsTotal()[1];
                }
            } else {
                playerTotal = getPlayerCardsTotal()[0];
            }
            if (getDealerCardsTotal().length > 1) {
                if (getDealerCardsTotal()[0] < getDealerCardsTotal()[1]) {
                    dealerTotal = getDealerCardsTotal()[1];
                }
            } else {
                dealerTotal = getDealerCardsTotal()[0];
            }
            if (dealerCards.get(0).isFacedUp() == true) {
                if (playerTotal > dealerTotal) {
                    setAccountAmount(playerAccount = playerAccount + initialBet);
                    return 7; // Player wins
                } else if (playerTotal < dealerTotal) {
                    setAccountAmount(playerAccount = playerAccount - initialBet);
                    return 6; // Dealer wins
                } else {
                    return 1; // Tie
                }
            }
        }
        return 8; // Game in progress
    }

    public void setBetAmount(int amount) {
        initialBet = amount;
    }

    public int getBetAmount() {
        return initialBet;
    }

    public void setAccountAmount(int amount) {
        playerAccount = amount;
    }

    public int getAccountAmount() {
        return playerAccount;
    }

    private int calculateCardsTotal(List<Card> cards) {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            if (card.getValue() == CardValue.Ace) {
                aceCount++;
            } else {
                total += card.getValue().getIntValue();
            }
        }

        for (int i = 0; i < aceCount; i++) {
            if (total + 11 <= 21) {
                total += 11;
            } else if (total + 1 <= 21) {
                total += 1;
            }
        }

        return total;
    }

    private int[] calculatePossibleTotals(List<Card> cards) {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            if (card.getValue() == CardValue.Ace) {
                aceCount++;
            } else {
                total += card.getValue().getIntValue();
            }
        }

        List<Integer> possibleTotals = new ArrayList<>();
        if (aceCount >= 2) {
            if (total + aceCount <= 21) {
                total += aceCount;
                possibleTotals.add(total);
                total -= aceCount;
            }
            if (total + aceCount - 1 + 11 <= 21) {
                total += aceCount - 1 + 11;
                possibleTotals.add(total);
                total -= aceCount - 1 + 11;
            }
        } else if (total <= 21 && aceCount == 0) {
            possibleTotals.add(total);
        } else {
            for (int i = 0; i < aceCount; i++) {
                if (total + 1 <= 21) {
                    total += 1;
                    possibleTotals.add(total);
                    total -= 1;
                }
                if (total + 11 <= 21) {
                    total += 11;
                    possibleTotals.add(total);
                    total -= 11;
                }
            }
        }

        int[] totals = new int[possibleTotals.size()];
        for (int i = 0; i < possibleTotals.size(); i++) {
            totals[i] = possibleTotals.get(i);
        }

        return totals;
    }

    private int countAces(List<Card> cards) {
        int count = 0;
        for (Card card : cards) {
            if (card.getValue() == CardValue.Ace) {
                count++;
            }
        }
        return count;
    }

    private Card drawCard() {
        Card tempCard = gameDeck.get(0);
        gameDeck.remove(0);
        return tempCard;
    }

    private Card drawCardDown() {
        Card tempCard = gameDeck.get(0);
        gameDeck.remove(0);
        tempCard.setFaceDown();
        return tempCard;
    }
}
