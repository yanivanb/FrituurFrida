package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.repositories.CSVSausRepository;
import be.vdab.frituurfrida.repositories.SausRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SausService {
    private final SausRepository sausRepository;

    public SausService(@Qualifier("properties") SausRepository sausRepository) {
        this.sausRepository = sausRepository;
    }
    public Stream<Saus> findAll() {
        return sausRepository.findAll();
    }
    public Stream<Saus> findByBeginNaam(char letter) {
        return sausRepository.findAll()
                .filter(saus -> saus.getNaam().charAt(0) == letter);
    }
    public Optional<Saus> findById(long id) {
        return sausRepository.findAll()
                .filter(saus -> saus.getId() == id)
                .findFirst();
    }
}
