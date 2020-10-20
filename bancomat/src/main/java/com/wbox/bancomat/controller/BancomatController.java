package com.wbox.bancomat.controller;

import com.wbox.bancomat.model.BanknoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wbox.bancomat.service.BancomatService;

import java.util.List;
import java.util.Map;

@Controller
public class BancomatController {
    @Autowired
    BancomatService bancomatService;

    @GetMapping("/extract")
    @ResponseBody
    public ResponseEntity<List> extract(@RequestParam Integer amount, @RequestParam Integer iterations) {
        return ResponseEntity.ok(bancomatService.extract(amount, iterations));
    }

    @GetMapping("/rechargeAccount")
    @ResponseBody
    public ResponseEntity<String> recharge() {
        bancomatService.rechargeAccount();
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/balance")
    @ResponseBody
    public ResponseEntity<Map<BanknoteType, Integer>> balance() {
        return ResponseEntity.ok(bancomatService.balance());
    }
}
