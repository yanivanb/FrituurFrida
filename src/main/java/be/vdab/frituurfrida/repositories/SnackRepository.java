package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.dto.VerkochtAantalPerSnack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SnackRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    private final RowMapper<Snack> snackMapper =
            (result, rowNum) ->
                    new Snack(result.getLong("id"), result.getString("naam"),
                            result.getBigDecimal("prijs"));


    public SnackRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template).withTableName("snacks").usingGeneratedKeyColumns("id");
    }

    public Optional<Snack> findById(long id) {
        try {
            var sql = """
            select id, naam, prijs
            from snacks
            where id = ?
            """;
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<Snack> findByBeginNaam(String beginNaam) {
        var sql = """
                select id, naam, prijs
                from snacks
                where naam like ?
                order by naam
                """;
        return template.query(sql, snackMapper, beginNaam + '%');
    }

    public void update(Snack snack) {
        var sql = """
        update pizzas
        set naam = ?, prijs = ?, pikant = ?
        where id = ?
        """;
        if (template.update(sql,
                snack.getNaam(), snack.getPrijs(), snack.getId())
                == 0) {
            throw new SnackNietGevondenException();
        }
    }

    public List<VerkochtAantalPerSnack> findVerkochteAantallenPerSnack() {
        var sql = """
                select id, naam, sum(aantal) as totaalAantal
                from snacks left outer join dagverkopen on snacks.id = dagverkopen.snackId
                group by id, naam
                order by id
                """;
        RowMapper<VerkochtAantalPerSnack> mapper = (result, rowNum) ->
                new VerkochtAantalPerSnack(result.getLong("id"), result.getString("naam"),
                        result.getInt("totaalAantal"));
        return template.query(sql, mapper);
    }

}
