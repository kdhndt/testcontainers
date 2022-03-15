package be.vdab.testcontainers.repositories;

import be.vdab.testcontainers.domain.Persoon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@Sql("/insertPersonen.sql")
class PersoonRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final PersoonRepository persoonRepository;
    @Container
    //deze variabele maakt het MySQL programma in een container, kan even duren
    //maak deze variabele static zodat deze container enkel een keer aangemaakt wordt voor alle tests in deze class
    //static wil zeggen; er is maar 1 exemplaar, die niet verandert?
    private static final MySQLContainer mySQL =
            new MySQLContainer("mysql:latest")
                    .withDatabaseName("testcontainers")
                    .withUsername("cursist")
                    .withPassword("cursist");

    public PersoonRepositoryTest(PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    @DynamicPropertySource
    private static void source(DynamicPropertyRegistry registry) {
        //gebruik de JDBC url van de MySQL database binnen je testcontainer ipv de app.properties waarde
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
    }

    @Test
    void erIs1PersoonEnDatIsJoe() {
        assertThat(persoonRepository.findAll())
                .singleElement()
                .extracting(Persoon::getVoornaam)
                .isEqualTo("Joe");
    }
}
