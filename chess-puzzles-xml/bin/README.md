# Spring Chess Puzzles XML

Chess is a famous table game. This exercise is about representing chess board states as Spring configurations.

## Details
Consider each chess piece as a particular object that implements [ChessPiece](src/main/java/com/epam/rd/autotasks/chesspuzzles/ChessPiece.java) interface.
Then a chessboard state may be represented via Spring Application Context, which contains beans of chess pieces.

Your task is to construct Spring XML Configurations to create such Application Contexts.
A configuration must provide beans of chess pieces.
The testing code uses them to create an instance of [ChessBoard](src/main/java/com/epam/rd/autotasks/chesspuzzles/ChessBoard.java), which must present the chessboard state as a String.
Implementing this interface and its static method `of` is your responsibility as well.

You need to provide the following configurations
(put them into [src/main/resources](src/main/resources)):
- default.xml
- defaultblack.xml
- defaultwhite.xml
- puzzle01.xml
- puzzle04.xml
- puzzle05.xml

You may refer to board states presented in related text files in [src/test/resources/boards](src/test/resources/boards)

### State String Symbols:

|Symbols|Meaning|
|---|---| 
| . | empty cell|
| K | black king|
| Q | black queen|
| R | black rook, castle|
| B | black bishop|
| N | black knight|
| P | black pawn|
| k | white king|
| q | white queen|
| r | white rook, castle|
| b | white bishop|
| n | white knight|
| p | white pawn|

Example:
```
RNBQKBNR
PPPPPPPP
........
........
....p...
........
pppp.ppp
rnbqkbnr
```

### Cell address
To address a cell, we use a regular chess scheme, as in an example below:
```
8│RNBQKBNR
7│PPPPPPPP
6│........
5│........
4│....p...
3│........
2│pppp.ppp
1│rnbqkbnr
 └────────
  ABCDEFGH      
```
