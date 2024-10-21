package com.epam.rd.autotasks.chesspuzzles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.rd.autotasks.chesspuzzles.Cell;
import com.epam.rd.autotasks.chesspuzzles.King;
import com.epam.rd.autotasks.chesspuzzles.Pawn;
import com.epam.rd.autotasks.chesspuzzles.Queen;

@Configuration
public class Puzzle02 {
	@Bean
	public Queen whiteQueen() {
		return new Queen(Cell.cell('H', 8), 'q');
	}
	@Bean
	public Queen blackQueen() {
		return new Queen(Cell.cell('F', 4), 'Q');
	}
	@Bean
	public King blackKing() {
		return new King(Cell.cell('D', 3), 'K');
	}
	@Bean
	public King whiteKing() {
		return new King(Cell.cell('B', 2), 'k');
	}
	@Bean
	public Pawn blackPawn1() {
		return new Pawn(Cell.cell('E', 2), 'P');
	}

}