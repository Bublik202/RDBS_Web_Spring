package com.epam.rd.autotasks.chesspuzzles;

public class Pawn implements ChessPiece {

	private Cell cell;
	private char piece;
	
	public Pawn(Cell cell, char piece) {
		super();
		this.cell = cell;
		this.piece = piece;
	}

	@Override
	public Cell getCell() {
		return cell;
	}

	@Override
	public char toChar() {
		return piece;
	}

}
