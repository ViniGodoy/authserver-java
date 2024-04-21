package br.pucpr.auth.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorsTest {
    @Test
    void sortDirReturnsAscIfNull() {
        assertEquals("ASC", Validators.sortDir(null));
    }

    @Test
    void sortDirThrowsIfInvalid() {
        assertThrows(BadRequestException.class, () -> Validators.sortDir("invalid"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc"})
    void sortDirReturnsUpperCasedSortDirIfValid(String dir) {
        assertEquals(dir.toUpperCase(), Validators.sortDir(dir));
    }
}
