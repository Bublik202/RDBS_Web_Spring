package com.epam.rd.autotasks.chesspuzzles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.rd.autotasks.chesspuzzles.Cell;
import com.epam.rd.autotasks.chesspuzzles.King;
import com.epam.rd.autotasks.chesspuzzles.Knight;
import com.epam.rd.autotasks.chesspuzzles.Pawn;
import com.epam.rd.autotasks.chesspuzzles.Queen;
import com.epam.rd.autotasks.chesspuzzles.Rook;
import com.epam.rd.autotasks.chesspuzzles.Bishop;

@Configuration
public class DefaultBlack {
	@Bean
	public King blackKing() {
		return new King(Cell.cell('E', 8), 'K');
	}	
	@Bean
	public Queen blackQueen() {
		return new Queen(Cell.cell('D', 8), 'Q');
	}	
	@Bean
	public Rook blackRook1() {
		return new Rook(Cell.cell('A', 8), 'R');
	}
	@Bean
	public Rook blackRook2() {
		return new Rook(Cell.cell('H', 8), 'R');
	}	
	@Bean
	public Bishop blackBishop1() {
		return new Bishop(Cell.cell('C', 8), 'B');
	}
	@Bean
	public Bishop blackBishop2() {
		return new Bishop(Cell.cell('F', 8), 'B');
	}	
	@Bean
	public Knight blackKnight1() {
		return new Knight(Cell.cell('B', 8), 'N');
	}
	@Bean
	public Knight blackKnight2() {
		return new Knight(Cell.cell('G', 8), 'N');
	}	
	@Bean
	public Pawn blackPawn1() {
		return new Pawn(Cell.cell('A', 7), 'P');
	}
	@Bean
	public Pawn blackPawn2() {
		return new Pawn(Cell.cell('B', 7), 'P');
	}
	@Bean
	public Pawn blackPawn3() {
		return new Pawn(Cell.cell('C', 7), 'P');
	}
	@Bean
	public Pawn blackPawn4() {
		return new Pawn(Cell.cell('D', 7), 'P');
	}
	@Bean
	public Pawn blackPawn5() {
		return new Pawn(Cell.cell('E', 7), 'P');
	}
	@Bean
	public Pawn blackPawn6() {
		return new Pawn(Cell.cell('F', 7), 'P');
	}
	@Bean
	public Pawn blackPawn7() {
		return new Pawn(Cell.cell('G', 7), 'P');
	}
	@Bean
	public Pawn blackPawn8() {
		return new Pawn(Cell.cell('H', 7), 'P');
	}
}
