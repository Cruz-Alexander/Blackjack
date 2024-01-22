# Blackjack Game Test Suite

This repository contains a test suite for a Blackjack game implemented in Java. The test suite includes public tests and support classes to validate the functionality of the Blackjack game.

## Table of Contents
- [Structure](#structure)
- [How to Use](#how-to-use)
- [Public Tests](#public-tests)
- [Support Classes](#support-classes)

## Structure

- `SupportPublicTests.java`: Support class containing public tests to assess the basic functionality of the Blackjack game.

- `StudentTests.java`: File reserved for student-specific test cases.

## How to Use

1. Clone this repository to your local machine.

2. Open the project in your Java development environment.

3. Run the tests provided in `SupportPublicTests.java` to check the correctness of basic game functionality.

4. Optionally, add your own test cases to `StudentTests.java` and run them to further validate the implementation.

## Public Tests

### 1. Deck Creation Test

```java
String results = SupportPublicTests.pubTest1DeckCreation();
