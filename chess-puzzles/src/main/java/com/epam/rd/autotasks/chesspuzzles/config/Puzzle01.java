package com.epam.rd.autotasks.chesspuzzles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.rd.autotasks.chesspuzzles.Bishop;
import com.epam.rd.autotasks.chesspuzzles.Cell;
import com.epam.rd.autotasks.chesspuzzles.King;
import com.epam.rd.autotasks.chesspuzzles.Knight;
import com.epam.rd.autotasks.chesspuzzles.Pawn;
import com.epam.rd.autotasks.chesspuzzles.Rook;

@Configuration
public class Puzzle01 {
	@Bean
	public Rook blackRook1() {
		return new Rook(Cell.cell('E', 8), 'R');
	}
	@Bean
	public King whiteKing() {
		return new King(Cell.cell('B', 7), 'k');
	}
	@Bean
	public Knight whiteKnight1() {
		return new Knight(Cell.cell('C', 6), 'n');
	}
	@Bean
	public Bishop blackBishop1() {
		return new Bishop(Cell.cell('D', 6), 'B');
	}
	@Bean 
	public Pawn blackPawn1() {
		return new Pawn(Cell.cell('F', 6), 'P');
	}
	@Bean
	public King blackKing() {
		return new King(Cell.cell('C', 5), 'K');
	}
	@Bean
	public Bishop blackBishop2() {
		return new Bishop(Cell.cell('D', 5), 'B');
	}
	@Bean
	public Pawn blackPawn2() {
		return new Pawn(Cell.cell('C', 3), 'P');
	}
	@Bean
	public Pawn whitePawn1() {
		return new Pawn(Cell.cell('D', 2), 'p');
	}
	@Bean
	public Bishop whiteBishop2() {
		return new Bishop(Cell.cell('E', 2), 'b');
	}
}
