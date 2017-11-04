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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Controller
public class PoissonnerieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoissonnerieController.class);
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final PoissonnerieRepository poissonnerieRepository;

    @Autowired
    public PoissonnerieController(PoissonnerieRepository poissonnerieRepository) {
        this.poissonnerieRepository = poissonnerieRepository;
    }

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/poissons-fiches")
    String fichesPoissons() {
        return "poissons-fiches";
    }

    @RequestMapping("/poissons-commercialisables")
    String poissonsCommercialisables(Map<String, Object> model) {
        List<Poisson> poissons = poissonnerieRepository.findAll();

        model.put("poissons", poissons.stream()
                .map(this::toPoissonDto)
                .filter(PoissonDto::isCommercialisationPossible)
                .sorted(comparing(PoissonDto::getEspece))
                .collect(toList()));
        model.put("dateDuJour", DF.format(LocalDate.now()));

        return "poissons-commercialisables";
    }

    @RequestMapping("/poisson/findByNom")
    @ResponseBody
    List<PoissonDto> findPoissonsByNom(@RequestParam("nom") String nom) {
        List<Poisson> poissons = poissonnerieRepository.findByEspeceIgnoreCaseContaining(nom);
        return poissons.stream().map(this::toPoissonDto).collect(toList());
    }

    @RequestMapping("/poisson/{code}")
    String findPoissonByCode(@PathVariable("code") int code,
                             Map<String, Object> model) {
        Poisson poisson = poissonnerieRepository.getOne(code);

        model.put("poisson", toPoissonDto(poisson));
        model.put("dateDuJour", DF.format(LocalDate.now()));

        return "fichePoisson";
    }

    private PoissonDto toPoissonDto(Poisson poisson) {
        return new PoissonDto.Builder()
                .setCode(poisson.getCode())
                .setEspece(poisson.getEspece())
                .setNomScientifique(poisson.getNomScientifique())
                .setDateDebutVente(poisson.getDateDebutVente())
                .setDateFinVente(poisson.getDateFinVente())
                .setType(poisson.getType())
                .setZonesDePeche(buildListeZonesDePecheDto(poisson))
                .build();
    }

    private List<ZonePecheDto> buildListeZonesDePecheDto(Poisson poisson) {
        return findZonesDePecheParentes(poisson).stream().map(zp ->
                new ZonePecheDto.Builder()
                        .setCode(zp.getCode())
                        .setLibelle(zp.getLibelle())
                        .setSousZones(buildListeSousZonesDePecheDto(zp.getCode(), poisson))
                        .build()
        ).collect(toList());
    }

    private List<ZonePeche> findZonesDePecheParentes(Poisson poisson) {
        List<ZonePeche> zonesPecheParent = poisson.getZonesDePeche()
                .stream()
                .filter(zp -> zp.getParent() == null)
                .collect(toList()); // niveau 1 seulement

        List<ZonePeche> zonesPecheParent2 = poisson.getZonesDePeche()
                .stream()
                .filter(zp -> zp.getParent() != null)
                .map(ZonePeche::getParent)
                .collect(toList()); // niveau 2 sans niveau 1 explicitement dans la liste

        return Stream.concat(zonesPecheParent.stream(), zonesPecheParent2.stream())
                .distinct()
                .sorted(comparing(ZonePeche::getCode))
                .collect(toList());
    }

    private List<ZonePecheDto> buildListeSousZonesDePecheDto(String codeZonePecheParent, Poisson poisson) {
        return poisson.getZonesDePeche().stream()
                .filter(zp -> zp.getParent() != null)
                .filter(zp -> Objects.equals(zp.getParent().getCode(), codeZonePecheParent))
                .map(zp -> new ZonePecheDto.Builder()
                        .setCode(zp.getCode())
                        .setCodeZoneParent(codeZonePecheParent)
                        .setLibelle(zp.getLibelle())
                        .build()
                ).collect(toList());
    }

}
