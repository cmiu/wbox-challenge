package com.wbox.bancomat.service;

import com.wbox.bancomat.model.BanknoteType;

import java.util.List;
import java.util.Map;

public interface BancomatService {
    List<Map<BanknoteType, Integer>> extract(Integer amount, Integer iterations);

    void rechargeAccount();

    Map<BanknoteType, Integer> balance();
}
