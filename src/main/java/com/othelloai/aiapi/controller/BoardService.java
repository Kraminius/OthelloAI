package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

public BoardService() {
    boardRepository = new BoardRepository();
    }


    @GetMapping("/board")
    public void getBoard() {
        boardRepository.getBoard();
    }

    @GetMapping("/validMoves")
    public ResponseEntity<Integer> getValidMoves() {
        int test = 0;
        return ResponseEntity.ok().body(test);
    }

}
