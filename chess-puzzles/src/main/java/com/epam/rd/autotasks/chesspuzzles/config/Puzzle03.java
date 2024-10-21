package com.epam.rd.autotasks.chesspuzzles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.rd.autotasks.chesspuzzles.Bishop;
import com.epam.rd.autotasks.chesspuzzles.Cell;
import com.epam.rd.autotasks.chesspuzzles.King;
import com.epam.rd.autotasks.chesspuzzles.Pawn;
import com.epam.rd.autotasks.chesspuzzles.Queen;
import com.epam.rd.autotasks.chesspuzzles.Rook;

@Configuration
public class Puzzle03 {
	@Bean
	public Rook whiteRook1() {
		return new Rook(Cell.cell('H', 8), 'r');
	}
	@Bean
	public Pawn blackPawn1() {
		return new Pawn(Cell.cell('B', 7), 'P');
	}
	@Bean
	public Queen blackQueen() {
		return new Queen(Cell.cell('D', 7), 'Q');
	}	
	@Bean
	public Pawn blackPawn2() {
		return new Pawn(Cell.cell('E', 7), 'P');
	}
	@Bean
	public Rook whiteRook2() {
		return new Rook(Cell.cell('F', 7), 'r');
	}
	@Bean
	public Pawn blackPawn3() {
		return new Pawn(Cell.cell('A', 6), 'P');
	}
	@Bean
	public Pawn blackPawn4() {
		return new Pawn(Cell.cell('D', 6), 'P');
	}
	@Bean
	public Pawn blackPawn5() {
		return new Pawn(Cell.cell('G', 6), 'P');
	}
	@Bean
	public Pawn whitePawn1() {
		return new Pawn(Cell.cell('D', 5), 'p');
	}
	@Bean
	public King blackKing() {
		return new King(Cell.cell('G', 5), 'K');
	}
	@Bean
	public Bishop blackBishop1() {
		return new Bishop(Cell.cell('B', 4), 'B');
	}
	@Bean
	public Rook blackRook1() {
		return new Rook(Cell.cell('C', 3), 'R');
	}
	@Bean
	public Pawn whitePawn2() {
		return new Pawn(Cell.cell('H', 2), 'p');
	}
	@Bean
	public Pawn whitePawn3() {
		return new Pawn(Cell.cell('G', 2), 'p');
	}
	@Bean
	public Rook blackRook2() {
		return new Rook(Cell.cell('C', 1), 'R');
	}
	@Bean
	public Bishop whiteBishop2() {
		return new Bishop(Cell.cell('D', 1), 'b');
	}
	@Bean
	public King whiteKing() {
		return new King(Cell.cell('H', 1), 'k');
	}

}