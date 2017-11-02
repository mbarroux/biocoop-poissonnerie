package fr.biocoop.poissonnerie.controllers;

import fr.biocoop.poissonnerie.controllers.dtos.PoissonDto;
import fr.biocoop.poissonnerie.controllers.dtos.ZonePecheDto;
import fr.biocoop.poissonnerie.repositories.PoissonnerieRepository;
import fr.biocoop.poissonnerie.repositories.entities.Poisson;
import fr.biocoop.poissonnerie.repositories.entities.ZonePeche;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
public class PoissonnerieController {

    private static final Logger logger = LoggerFactory.getLogger(PoissonnerieController.class);

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
    @ResponseBody
        // TODO ?
    List<Poisson> findPoissonsByNom(@RequestParam("nom") String nom) {
        List<Poisson> poissons = poissonnerieRepository.findByEspeceIgnoreCaseContaining(nom);
        return poissons;
    }

    @RequestMapping("/poisson/{code}")
    String findPoissonByCode(@PathVariable("code") int code,
                             Map<String, Object> model) {

        Poisson poisson = poissonnerieRepository.getOne(code);
        logger.debug("poisson : " + poisson);

        PoissonDto poissonDto = new PoissonDto.Builder()
                .setCode(poisson.getCode())
                .setEspece(poisson.getEspece())
                .setNomScientifique(poisson.getNomScientifique())
                .setDateDebutVente(poisson.getDateDebutVente())
                .setDateFinVente(poisson.getDateFinVente())
                .setType(poisson.getType())
                .setZonesDePeche(buildListeZonesDePecheDto(poisson))
                .build();

        model.put("poisson", poissonDto);

        return "fichePoisson";
    }

    private List<ZonePecheDto> buildListeZonesDePecheDto(Poisson poisson) {
        List<ZonePeche> zonesPecheParent = poisson.getZonesDePeche()
                .stream()
                .filter(zp -> zp.getParent() == null)
                .collect(toList());

        return zonesPecheParent.stream().map(zp ->
                new ZonePecheDto.Builder()
                        .setCode(zp.getCode())
                        .setCodeZoneParent(zp.getCode())
                        .setLibelle(zp.getLibelle())
                        .setSousZones(buildListeSousZonesDePecheDto(zp))
                        .build()
        ).collect(toList());
    }

    private List<ZonePecheDto> buildListeSousZonesDePecheDto(ZonePeche zonePecheParent) {
        return zonePecheParent.getSousZones().stream().map(zp ->
                new ZonePecheDto.Builder()
                        .setCode(zp.getCode())
                        .setLibelle(zp.getLibelle())
                        .build()
        ).collect(toList());
    }

}
