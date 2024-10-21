package com.epam.rd.autotasks.chesspuzzles;

public class Bishop implements ChessPiece{
	private final Cell cell;
	private final char piece;
	
	public Bishop(Cell cell, char piece) {
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
