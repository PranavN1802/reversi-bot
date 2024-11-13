# Reversi AI Project

This project is a Java-based implementation of the game *Reversi* (Othello), featuring an AI opponent, a graphical user interface, and competitive gameplay using Minimax search and alpha-beta pruning.

## Overview

Reversi is a strategic two-player board game played on an 8x8 grid. The objective is to have the majority of pieces in your color by the game’s end. This project includes:

- **Board Management**: `BoardState` and `Move` classes handle the game logic and board updates.
- **Player Agents**: `MoveChooser` offers human, random, and AI-controlled moves.
- **Game Control**: `Othello.java` manages the main game loop and graphical interface.

## Running the Game

To compile and start the game:

```bash
$ javac *.java
$ java Othello
