package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardService {
    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    @GetMapping("/board")
    public ResponseEntity<int[][]> getBoard() {
        int[][] board = boardRepository.getBoard();
        return ResponseEntity.ok().body(board);
    }
    @GetMapping("/score")
    public ResponseEntity<int[]> getScore() {
        int[] score = boardRepository.getScore();
        return ResponseEntity.ok().body(score);
    }
    @GetMapping("/forfeit")
    public ResponseEntity<Void> forfeit() {
        boardRepository.forfeit();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/gameType")
    public ResponseEntity<String> getGameType() {
        String gameType = boardRepository.getGameType();
        return ResponseEntity.ok().body(gameType);
    }
    @GetMapping("/turn")
    public ResponseEntity<Boolean> getTurn() {
        boolean turn = boardRepository.getTurn();
        return ResponseEntity.ok().body(turn);
    }
    @GetMapping("/setChoice/{x}/{y}")
    public ResponseEntity<Boolean> setChoice(@PathVariable int x, @PathVariable int y) {
        boolean success = boardRepository.setChoice(x, y);
        return ResponseEntity.ok().body(success);
    }
}
