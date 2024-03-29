package be.vdab.frituurfrida.repositories;


import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
@Qualifier("CSV")
public class CSVSausRepository implements SausRepository {
    private final Path PAD;

    public CSVSausRepository(@Value("${CSVSausenPad}") Path PAD) {
        this.PAD = PAD;
    }

    @Override
    public Stream<Saus> findAll() {
        try {
            return Files.lines(PAD).map(this::maakSaus);
        } catch (IOException ex) {
            throw new SausRepositoryException("Fout bij lezen " + PAD, ex);
        }
    }
    private Saus maakSaus(String regel) {
        var onderdelen = regel.split(",");
        if (onderdelen.length < 2) {
            throw new SausRepositoryException(PAD+":"+regel+": minder dan 2 onderdelen");
        }
        try {
            var ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);
            return new Saus(Long.parseLong(onderdelen[0]),onderdelen[1],ingredienten);
        } catch (NumberFormatException ex) {
            throw new SausRepositoryException(PAD + ":" + regel + ": verkeerde id", ex);
        }
    }
}
