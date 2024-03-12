package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.model.BoardResponse;
import com.othelloai.aiapi.model.Config;
import com.othelloai.aiapi.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

@RestController
public class BoardService {
    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    @GetMapping("/board")
    public ResponseEntity<String> getBoard() {
        System.out.println("Something");
        System.out.println(boardRepository.getBoard().toString());
        int[][] board = boardRepository.getBoard();
        System.out.println(boardRepository.getBoard().toString());
        Gson gson = new Gson();
        boolean turn = Config.getTurn();
        BoardResponse boardResponse = new BoardResponse(board, turn);
        String json = gson.toJson(boardResponse);
        System.out.println("THIS IS MY JSON: " + json);
        return ResponseEntity.ok().body(json);
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
    @GetMapping("/skipTurn")
    public ResponseEntity<Void> skipTurn() {
        boardRepository.skipTurn();
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
        if(!success) return ResponseEntity.badRequest().body(success); //sends a 400 error to client
        return ResponseEntity.ok().body(success);
    }
}
