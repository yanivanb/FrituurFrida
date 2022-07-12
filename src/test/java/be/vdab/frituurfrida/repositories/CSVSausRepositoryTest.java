package be.vdab.frituurfrida.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CSVSausRepositoryTest {
    private static final Path PAD = Path.of("/data/sauzen.csv");
    private CSVSausRepository repository;
    @BeforeEach
    void beforeEach() {
        repository = new CSVSausRepository();
    }
    @Test
    void erZijnEvenveelSauzenAlsErRegelsZijnInHetCSVBestand() throws IOException {
        assertThat(repository.findAll()).hasSameSizeAs(Files.readAllLines(PAD));
    }
    @Test
    void deEersteSausBevatDeDataVanDeEersteRegelInHetCSVBestand()
            throws IOException {
        var eersteRegel = Files.lines(PAD).findFirst().get();
        var eersteSaus = repository.findAll().findFirst().get();
        assertThat(eersteSaus.getId() + "," + eersteSaus.getNaam() + "," +
                Arrays.stream(eersteSaus.getIngredienten())
                        .collect(Collectors.joining(",")))
                .isEqualTo(eersteRegel);
    }
}