package fr.biocoop.poissonnerie.repositories;

import fr.biocoop.poissonnerie.repositories.entities.Poisson;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoissonnerieRepository extends JpaRepository<Poisson, Integer> {

    List<Poisson> findByEspeceLike(String espece);
}
