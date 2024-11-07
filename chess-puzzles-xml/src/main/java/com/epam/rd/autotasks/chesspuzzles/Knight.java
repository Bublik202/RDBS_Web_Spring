package com.epam.rd.autotasks.chesspuzzles;

public class Knight implements ChessPiece {
	private Cell cell;
	private char piece;
	
	public Knight(Cell cell, char piece) {
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