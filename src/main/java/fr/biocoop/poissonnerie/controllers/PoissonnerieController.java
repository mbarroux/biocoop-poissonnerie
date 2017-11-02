package fr.biocoop.poissonnerie.controllers;

import fr.biocoop.poissonnerie.repositories.PoissonnerieRepository;
import fr.biocoop.poissonnerie.repositories.entities.Poisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class PoissonnerieController {

    private final PoissonnerieRepository poissonnerieRepository;

    @Autowired
    public PoissonnerieController(PoissonnerieRepository poissonnerieRepository) {
        this.poissonnerieRepository = poissonnerieRepository;
    }

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/poisson/findByNom")
    @ResponseBody // TODO ?
    List<Poisson> findPoissonsByNom(@RequestParam("nom") String nom) {
        List<Poisson> poissons = poissonnerieRepository.findByEspeceLike(nom);
        return poissons;
    }

    @RequestMapping("/poisson/{code}")
    String findPoissonByCode(@PathVariable("code") int code,
                             Map<String, Object> model) {

        Poisson poisson = poissonnerieRepository.getOne(code);
        // TODO DTO

        model.put("poisson", poisson);
        return "fichePoisson";
    }

}
