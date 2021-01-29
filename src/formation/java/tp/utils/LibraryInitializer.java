package formation.java.tp.utils;

import formation.java.tp.model.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Vector;

public class LibraryInitializer
{
    public void initializeCollection(Library pLibrary)
    {
        Book lBooks[] = new Book[]
        {
                new Book("Design Pattern"
                        , new Editor("une rue", "une ville", "un pays","un nom", "un siret", "38000")
                        , LocalDateTime.now()
                        , 300
                        , "Eric Gamma"
                        , eBookType.Course
                        , true),
                new Book("L'art d'avoir toujours raison"
                        , new Editor("quelque part", "dans une ville lointaine", "en Europe", "chez un editeur", "qui n'avait pas de Siret", "38000")
                        , LocalDateTime.now()
                        , 75
                        , "Arthur Schöpenhauer"
                        , eBookType.Philosophy
                        , true),
                new Book("Le grands livre des robots"
                        , new Editor("paumé", "j'en sais rien", "sur un continent", "va savoir", "qui sait?", "38000")
                        , LocalDateTime.now()
                        , 2000
                        , "Isaac Asimov"
                        ,eBookType.ScienceFiction
                        , true),
                new Book("Les portes de la perception"
                        , new Editor("est ce important?", "pas d'inspiration", "USA", "hippies&Co", "nope", "38000")
                        , LocalDateTime.now()
                        , 215
                        , "Aldous Huxley"
                        , eBookType.Novel
                        , true)
        } ;
        Magazine lMagazines[] = new Magazine[]
                {
                        new Magazine("Auto moto"
                                     , new Editor("rue du vroum", "Monaco", "France", "AutoMoto&Co", "1547926875", "38000")
                                     , LocalDateTime.now()
                                     , 50
                                     , "Les magazines qui roulent"
                                     , eMagazineType.Auto
                                     , ePublishmentFrequency.Monthly),
                        new Magazine("Science et vie"
                                     , new Editor("avenue des sciences", "Paris", "France", "ScienceEditor", "5987621468", "38000")
                                     , LocalDateTime.now()
                                     , 75
                                     , "Les magazines de sciences"
                                     , eMagazineType.Science
                                     , ePublishmentFrequency.Quaterly),
                        new Magazine("Le Dauphiné libéré"
                                     , new Editor("boulevard de la resistance", "Grenoble", "France", "edition Jean Moulin", "8943201547", "38000")
                                     , LocalDateTime.now()
                                     , 12
                                     , "Journal du dauphiné"
                                     , eMagazineType.Politic
                                     , ePublishmentFrequency.Daily),
                        new Magazine("un magazine d'histoire"
                                     , new Editor("ruelle du musée", "Orléans", "France", "edition vieille", "9032105478", "38000")
                                     , LocalDateTime.now()
                                     , 36
                                     , "Journal du musée"
                                     , eMagazineType.History
                                     , ePublishmentFrequency.Annual)
                } ;
        CD lCDs[] = new CD[]
                {
                        new CD("Adibou à l'école"
                               , new Editor("impasse élémentaire", "Dusseldorf", "Allemagne", "Adibou Ausgaben", "6751398542", "38000")
                               , LocalDateTime.now()
                               , 117.6
                               , eCDType.Course),
                        new CD("Photoshop"
                                , new Editor("85 show pimp street", "Miami", "USA", "Silicon Valley", "9780230145", "38000")
                                , LocalDateTime.now()
                                , 0
                                , eCDType.Software),
                        new CD("The Offspring - Americana"
                                , new Editor("somewhere on Punk avenue", "Los Angeles", "USA", "destroy everything editions", "00245896314", "38000")
                                , LocalDateTime.now()
                                , 58.2
                                , eCDType.Music),
                        new CD("Forest ambiance"
                                , new Editor("ruelle de la sérénité", "Utopia", "Heaven", "Deus edition", "9999999999", "38000")
                                , LocalDateTime.now()
                                , 999
                                , eCDType.Ambiance)
                } ;
        DVD lDVDs[] = new DVD[]
                {
                        new DVD("Matrix"
                                , new Editor("proche du centre de la Terre", "Zion", "unknown", "last survivor editions", "0000000000", "38000")
                                , LocalDateTime.now()
                                , 137.5
                                , eDVDType.Film
                                , true),
                        new DVD("Witcher"
                                , new Editor("dans un garage", "Wroclaw", "Pologne", "CD projekts", "9543186542", "38000")
                                , LocalDateTime.now()
                                , 6000
                                , eDVDType.Software
                                , false),
                        new DVD("La marche des empereurs"
                                , new Editor("pôle Sud", "panguoria", "extreme South", "le manchot pas droit", "9831546785", "38000")
                                , LocalDateTime.now()
                                , 115.9
                                , eDVDType.Documentary
                                , true),
                        new DVD("Deftones - Live in Sydney"
                                , new Editor("9 rue du skate park", "Portland", "USA", "skaterzz", "9012864856", "38000")
                                , LocalDateTime.now()
                                , 198.7
                                , eDVDType.Concert
                                , true)
                } ;
        for (byte i = 0; i < 4; ++i)
        {
            pLibrary.mBookLibrary.add(lBooks[i]) ;
            pLibrary.mMagazineLibrary.add(lMagazines[i]) ;
            pLibrary.mCDLibrary.add(lCDs[i]) ;
            pLibrary.mDVDLibrary.add(lDVDs[i]) ;
        }
    }
}
