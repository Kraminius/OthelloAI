package com.othelloai.aiapi.controller;

import com.google.gson.Gson;
import com.othelloai.aiapi.model.BoardResponse;
import com.othelloai.aiapi.model.Config;
import com.othelloai.aiapi.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

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

    @GetMapping("/skipTurn/{player}")
    public ResponseEntity<Void> skipTurn(@PathVariable("player") boolean player) {
        DeferredResult<ResponseEntity<Boolean>> deferredResult = new DeferredResult<>();

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                deferredResult.setResult(ResponseEntity.ok(true));
            }

            @Override
            public void onError(Exception e) {
                System.err.println("Operation failed: " + e.getMessage());

            }
        };
        boardRepository.skipTurn(player, callback);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gameType")
    public ResponseEntity<String> getGameType() {
        String gameType = boardRepository.getGameType();
        return ResponseEntity.ok().body(gameType);
    }

    @GetMapping("/turn/{player}")
    public ResponseEntity<Boolean> getTurn(@PathVariable("player") boolean player) {
        boolean turn = boardRepository.getTurn();
        return ResponseEntity.ok().body(turn == player);
    }

    @GetMapping("/setChoice/{x}/{y}/{player}")
    public DeferredResult<ResponseEntity<Boolean>> setChoice(@PathVariable("x") int x, @PathVariable("y") int y, @PathVariable("player") boolean player) {
        DeferredResult<ResponseEntity<Boolean>> deferredResult = new DeferredResult<>();

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                deferredResult.setResult(ResponseEntity.ok(true));
            }

            @Override
            public void onError(Exception e) {
                // Log the exception or handle it as needed
                System.err.println("Operation failed: " + e.getMessage());
                deferredResult.setResult(ResponseEntity.badRequest().body(false));
            }
        };

        boardRepository.setChoice(x, y, player, callback);

        return deferredResult;
    }


}
