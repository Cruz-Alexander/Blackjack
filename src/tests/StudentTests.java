package tests;

import java.io.BufferedReader;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

import blackjack.Blackjack;
import blackjack.Card;

/**
 * Please put your own test cases into this file, so they can be tested on the
 * server.
 */
public class StudentTests {

    public static String pubTest4Play1() {
        Blackjack blackjack = setBlackJack();

        blackjack.deal();
        String results = getCardsString(blackjack.getPlayerCards()) + "\n";
        results += getCardsString(blackjack.getDealerCards()) + "\n";
        results += "*** Standing ***\n";
        blackjack.playerStand();
        results += getCardsString(blackjack.getPlayerCards()) + "\n";
        results += getCardsString(blackjack.getDealerCards());

        String playerEvaluation = mapIntString(blackjack.getPlayerCardsEvaluation());
        String dealerEvaluation = mapIntString(blackjack.getDealerCardsEvaluation());
        results += "Player: " + playerEvaluation + "\n";
        results += "Dealer: " + dealerEvaluation + "\n";

        results += "Game Status: ";
        results += mapIntString(blackjack.getGameStatus()) + "\n";

        results += "Player Bank: ";
        results += blackjack.getAccountAmount();

        return results;
    }

    private static Blackjack setBlackJack() {
        Random randomGenerator = new Random(59);
        int numberOfDecks = 1;
        Blackjack blackjack = new Blackjack(randomGenerator, numberOfDecks);
        return blackjack;
    }

    private static String getCardsString(Card[] array) {
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i] + "\n";
        }
        return result;
    }

    private static String mapIntString(int value) {
        String result;
        switch (value) {
            case Blackjack.DRAW:
                result = "DRAW";
                break;
            case Blackjack.LESS_THAN_21:
                result = "LESS_THAN_21";
                break;
            case Blackjack.BUST:
                result = "BUST";
                break;
            case Blackjack.BLACKJACK:
                result = "BLACKJACK";
                break;
            case Blackjack.HAS_21:
                result = "HAS_21";
                break;
            case Blackjack.DEALER_WON:
                result = "DEALER_WON";
                break;
            case Blackjack.PLAYER_WON:
                result = "PLAYER_WON";
                break;
            case Blackjack.GAME_IN_PROGRESS:
                result = "GAME_IN_PROGRESS";
                break;
            default:
                result = "INVALID";
                break;
        }
        return result;
    }

    public static void main(String args[]) {
        String results = StudentTests.pubTest4Play1();
        System.out.println(results);
    }
}
