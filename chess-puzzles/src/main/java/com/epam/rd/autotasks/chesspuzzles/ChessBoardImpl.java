package com.epam.rd.autotasks.chesspuzzles;

import java.util.Collection;

public class ChessBoardImpl implements ChessBoard{
	private final ChessPiece[][] board = new ChessPiece[8][8];
	
	
	public ChessBoardImpl(Collection<ChessPiece> pieces) {
		init(pieces);
	}
	
	private void init(Collection<ChessPiece> pieces) {
		for (ChessPiece piece : pieces) {
			Cell cell = piece.getCell();
			if(cell != null) {
				board[8 - cell.number][cell.letter - 'A'] = piece;
			}		
		}		
	}

	@Override
	public String state() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			ChessPiece[] pieces = board[i];
			
			for (int j = 0; j < pieces.length; j++) {
				ChessPiece piece = pieces[j];
				char ch = piece == null ? '.' : piece.toChar();
				builder.append(ch);
			}
			
			if(i < board.length - 1) {
				builder.append('\n');
			}
		}
		return builder.toString();
	}

}
