package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;

import java.util.stream.Stream;

public interface SausRepository {
        Stream<Saus> findAll();
}
