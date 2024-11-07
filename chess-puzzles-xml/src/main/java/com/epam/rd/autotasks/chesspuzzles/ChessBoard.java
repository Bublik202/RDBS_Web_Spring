package com.epam.rd.autotasks.chesspuzzles;

import java.util.Collection;

public interface ChessBoard {
	static ChessBoard of(Collection<ChessPiece> pieces){
    	final ChessPiece[][] board = new ChessPiece[8][8];
    	
    	for (ChessPiece piece : pieces) {
			Cell cell = piece.getCell();
			if(cell != null) {
				board[8 - cell.number][cell.letter - 'A'] = piece;
			}
		}
    	
        return new ChessBoard() {
			
			@Override
			public String state() {
				StringBuilder builder =  new StringBuilder();
				for (ChessPiece[] chessPieces : board) {
					for (ChessPiece piece : chessPieces) {
						char state = (piece == null) ? '.' : piece.toChar();
						builder.append(state);
					}
					builder.append("\n");
				}				
				builder.deleteCharAt(builder.length()-1);
				
				return builder.toString();
			}
		};
    }

    String state();
}
