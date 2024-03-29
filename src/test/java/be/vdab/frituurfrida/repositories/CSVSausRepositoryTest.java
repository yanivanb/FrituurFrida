package be.vdab.frituurfrida.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(CSVSausRepository.class)
class CSVSausRepositoryTest {
    private final Path PAD;
    private CSVSausRepository repository;

    public CSVSausRepositoryTest(CSVSausRepository repository, @Value("${CSVSausenPad}") Path pad) {
        this.repository = repository;
        this.PAD = pad;
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