package be.vdab.testcontainers.repositories;

import be.vdab.testcontainers.domain.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersoonRepository extends JpaRepository<Persoon, Long> {
}
