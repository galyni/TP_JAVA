package formation.java.tp.utils;

import formation.java.tp.model.Book;
import formation.java.tp.model.Editor;
import formation.java.tp.model.Library;
import formation.java.tp.model.Magazine;

import java.util.Date;
import java.util.Vector;

public class LibraryInitializer
{
    public void initializeCollection(Vector<Library> pLibrary)
    {
        Book lBooks[] = new Book[]
        {
                new Book("Design Pattern"
                        , new Editor("une rue", "une ville", "un pays","un nom", "un siret")
                        , new Date()
                        , 300
                        , "Eric Gamma"
                        , eBookType.Course
                        , true),
                new Book("L'art d'avoir toujours raison"
                        , new Editor("quelque part", "dans une ville lointaine", "en Europe", "chez un editeur", "qui n'avait pas de Siret")
                        , new Date()
                        , 75
                        , "Arthur Schöpenhauer"
                        , eBookType.Philosophy
                        , true),
                new Book("Le grands livre des robots"
                        , new Editor("paumé", "j'en sais rien", "sur un continent", "va savoir", "qui sait?")
                        , new Date()
                        , 2000
                        , "Isaac Asimov"
                        ,eBookType.ScienceFiction
                        , true),
                new Book("Les portes de la perception"
                        , new Editor("est ce important?", "pas d'inspiration", "USA", "hippies&Co", "nope")
                        , new Date()
                        , 215
                        , "Aldous Huxley"
                        , eBookType.Novel
                        , true)
        } ;
        Magazine lMagazine[] = new Magazine[]
                {
                        new Magazine("Auto moto"
                                     , new Editor("rue du vroum", "Monaco", "France", "AutoMoto&Co", "1547926875")
                                     , new Date()
                                     , 50
                                     , "Les magazines qui roulent"
                                     , eMagazineType.Auto
                                     , ePublishmentFrequency.Monthly),
                        new Magazine("Science et vie"
                                     , new Editor("avenue des sciences", "Paris", "France", "ScienceEditor", "5987621468")
                                     , new Date()
                                     , 75
                                     , "Les magazines de sciences"
                                     , eMagazineType.Science
                                     , ePublishmentFrequency.Quaterly),
                        new Magazine("Le Dauphiné libéré"
                                     , new Editor("boulevard de la resistance", "Grenoble", "France", "edition Jean Moulin", "8943201547")
                                     , new Date()
                                     , 12
                                     , "Journal du dauphiné"
                                     , eMagazineType.Politic
                                     , ePublishmentFrequency.Daily),
                        new Magazine("un magazine d'histoire"
                                     , new Editor("ruelle du musée", "Orléans", "France", "edition vieille", "9032105478")
                                     , new Date()
                                     , 36
                                     , "Journal du musée"
                                     , eMagazineType.History
                                     , ePublishmentFrequency.Annual),
                } ;
    }
}
