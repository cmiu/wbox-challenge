package repository.impl;

import com.wbox.bancomat.exception.NotEnoughCashException;
import com.wbox.bancomat.model.BanknoteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.wbox.bancomat.service.impl.BancomatServiceImpl;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BancomatServieImplTest {
    BancomatServiceImpl bancomatService;

    @BeforeEach
    public void init() {
        bancomatService = new BancomatServiceImpl();
    }

    @Test
    public void test_Extract() throws NotEnoughCashException {
        assertEquals(bancomatService.extract(10).get(BanknoteType.TEN_RON), 1);

    }

    @Test
    public void test_Extract_ComplexAmounnt() throws NotEnoughCashException {
        Map<BanknoteType, Integer> result = bancomatService.extract(237);
        assertAll(
                () -> assertEquals(2, result.get(BanknoteType.HUNDRED_RON)),
                () -> assertEquals(3, result.get(BanknoteType.TEN_RON)),
                () -> assertEquals(1, result.get(BanknoteType.FIVE_RON)),
                () -> assertEquals(2, result.get(BanknoteType.ONE_RON))

        );

    }

    @Test
    public void test_Extract_ComplexAmounnt_2() throws NotEnoughCashException {
        Map<BanknoteType, Integer> result = bancomatService.extract(6437);
        assertAll(
                () -> assertEquals(50, result.get(BanknoteType.HUNDRED_RON)),
                () -> assertEquals(28, result.get(BanknoteType.FIFTY_RON)),
                () -> assertEquals(3, result.get(BanknoteType.TEN_RON)),
                () -> assertEquals(1, result.get(BanknoteType.FIVE_RON)),
                () -> assertEquals(2, result.get(BanknoteType.ONE_RON))

        );

    }

    @Test()
    public void test_Extract_NotEnoughCash() throws NotEnoughCashException {
        assertThrows(NotEnoughCashException.class, () -> bancomatService.extract(20000));

    }
}