package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;

@JdbcTest
@Sql({"/insertSnacks.sql", "/insertDagverkopen.sql"})
@Import(SnackRepository.class)
class SnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String SNACKS = "snacks";
    private final SnackRepository repository;

    SnackRepositoryTest(SnackRepository repository) {
        this.repository = repository;
    }

    private long idVanTestSnack() {
        return jdbcTemplate.queryForObject(
                "select id from snacks where naam='test'", Long.class);
    }
    @Test
    void update() {
        var id = idVanTestSnack();
        var snack = new Snack(id, "test", BigDecimal.TEN);
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS, "prijs=10 and id=" + id)).isOne();
    }
    @Test
    void updateOnbestaandeSnack() {
        assertThatExceptionOfType(SnackNietGevondenException.class)
                .isThrownBy(() -> repository.update(new Snack(-1,"test",BigDecimal.TEN)));
    }
    @Test
    void findById() {
        assertThat(repository.findById(idVanTestSnack()))
                .hasValueSatisfying(snack->assertThat(snack.getNaam()).isEqualTo("test"));
    }
    @Test
    void findByOnbestaandeIdVindtGeenSnack() {
        assertThat(repository.findById(-1)).isEmpty();
    }
    @Test
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("t"))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 't%'"))
                .extracting(Snack::getNaam)
                .allSatisfy(naam -> assertThat(naam.toLowerCase()).startsWith("t"))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void findVerkochtAantalPerSnack() {
        var verkochteAantallenPerSnack = repository.findVerkochteAantallenPerSnack();
        assertThat(verkochteAantallenPerSnack).hasSize(countRowsInTable(SNACKS));
        var rij1 = verkochteAantallenPerSnack.get(0);
        assertThat(rij1.totaalAantal()).isEqualTo(jdbcTemplate.queryForObject(
                "select sum(aantal) from dagVerkopen where snackId = " + rij1.id(),
                Integer.class));
    }
}
