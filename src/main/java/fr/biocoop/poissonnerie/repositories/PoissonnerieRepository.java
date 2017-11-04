package fr.biocoop.poissonnerie.repositories;

import fr.biocoop.poissonnerie.repositories.entities.Poisson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoissonnerieRepository extends JpaRepository<Poisson, Integer> {

    List<Poisson> findByEspeceIgnoreCaseContaining(String espece);
}
