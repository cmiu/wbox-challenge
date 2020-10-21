package com.wbox.bancomat.service.impl;

import com.wbox.bancomat.exception.NotEnoughCashException;
import com.wbox.bancomat.model.BanknoteType;
import org.springframework.beans.factory.annotation.Autowired;
import com.wbox.bancomat.service.BancomatService;
import com.wbox.bancomat.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BancomatServiceImpl implements BancomatService {

    private static final Integer INITIAL_ONE_HUNDRED_COUNT = 100;
    private static final Integer INITIAL_FIFTY_COUNT = 50;
    private static final Integer LARGE_AMOUNT_THRESHOLD = 200;

    private Map<BanknoteType, Integer> account;

    @Autowired
    NotificationService notificationService;

    public BancomatServiceImpl() {
        account = new TreeMap<>();
        rechargeAccount();
    }

    @Override
    public List<Map<BanknoteType, Integer>> extract(Integer amount, Integer iterations) {
        List<Map<BanknoteType, Integer>> results = new ArrayList<>();

        if (amount > LARGE_AMOUNT_THRESHOLD) {
            System.out.println(String.format("Warning: Amount over % RON", LARGE_AMOUNT_THRESHOLD));
        }
        while (iterations-- > 0) {
            try {
                results.add(extract(amount));
                if (account.get(BanknoteType.HUNDRED_RON) < 0.25 * INITIAL_ONE_HUNDRED_COUNT) {
                    notificationService.sendEmail("Bancnote de 100 RON sub 20%");
                }
                if (account.get(BanknoteType.HUNDRED_RON) < 0.15 * INITIAL_ONE_HUNDRED_COUNT) {
                    notificationService.sendSMS("Bancnote de 100 RON sub 15%");
                }
                if (account.get(BanknoteType.FIFTY_RON) < 0.15 * INITIAL_ONE_HUNDRED_COUNT) {
                    notificationService.sendEmail("Bancnote de 50 RON sub 15%");
                }
            } catch (NotEnoughCashException e) {
                System.out.println(e.getMessage());
            }

        }

        return results;
    }

    @Override
    public Map<BanknoteType, Integer> balance() {
        return account;
    }

    @Override
    public void rechargeAccount() {
        account.put(BanknoteType.ONE_RON, INITIAL_ONE_HUNDRED_COUNT);
        account.put(BanknoteType.FIVE_RON, INITIAL_ONE_HUNDRED_COUNT);
        account.put(BanknoteType.TEN_RON, INITIAL_ONE_HUNDRED_COUNT);
        account.put(BanknoteType.FIFTY_RON, INITIAL_FIFTY_COUNT);
        account.put(BanknoteType.HUNDRED_RON, INITIAL_FIFTY_COUNT);
    }


    public Map<BanknoteType, Integer> extract(Integer amount) throws NotEnoughCashException {
        Map<BanknoteType, Integer> result = new HashMap<>();
        List<BanknoteType> notes = Arrays.asList(BanknoteType.values());
        Collections.reverse(notes);

        for (BanknoteType note : notes) {
            Integer reqNotesCount = amount / note.getValue();
            Integer availableNotes = account.get(note);

            if (reqNotesCount > availableNotes)
                reqNotesCount = availableNotes;

            result.put(note, reqNotesCount);
            amount -= reqNotesCount * note.getValue();
            account.put(note, availableNotes - reqNotesCount);
        }

        if (amount > 0) {
            throw new NotEnoughCashException();
        }
        return result;
    }
}
