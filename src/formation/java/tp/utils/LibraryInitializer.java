package formation.java.tp.utils;

import formation.java.tp.model.*;

import java.util.Date;
import java.util.Vector;

public class LibraryInitializer
{
    public void initializeCollection(Library pLibrary)
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
        Magazine lMagazines[] = new Magazine[]
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
                                     , ePublishmentFrequency.Annual)
                } ;
        CD lCDs[] = new CD[]
                {
                        new CD("Adibou à l'école"
                               , new Editor("impasse élémentaire", "Dusseldorf", "Allemagne", "Adibou Ausgaben", "6751398542")
                               , new Date()
                               , 117.6
                               , eCDType.Course),
                        new CD("Photoshop"
                                , new Editor("85 show pimp street", "Miami", "USA", "Silicon Valley", "9780230145")
                                , new Date()
                                , 0
                                , eCDType.Software),
                        new CD("The Offspring - Americana"
                                , new Editor("somewhere on Punk avenue", "Los Angeles", "USA", "destroy everything editions", "00245896314")
                                , new Date()
                                , 58.2
                                , eCDType.Music),
                        new CD("Forest ambiance"
                                , new Editor("ruelle de la sérénité", "Utopia", "Heaven", "Deus edition", "9999999999")
                                , new Date()
                                , 999
                                , eCDType.Ambiance)
                } ;
        DVD lDVDs[] = new DVD[]
                {
                        new DVD("Matrix"
                                , new Editor("proche du centre de la Terre", "Zion", "unknown", "last survivor editions", "0000000000")
                                , new Date()
                                , 137.5
                                , eDVDType.Film
                                , true),
                        new DVD("Witcher"
                                , new Editor("dans un garage", "Wroclaw", "Pologne", "CD projekts", "9543186542")
                                , new Date()
                                , 6000
                                , eDVDType.Software
                                , false),
                        new DVD("La marche des empereurs"
                                , new Editor("pôle Sud", "panguoria", "extreme South", "le manchot pas droit", "9831546785")
                                , new Date()
                                , 115.9
                                , eDVDType.Documentary
                                , true),
                        new DVD("Deftones - Live in Sydney"
                                , new Editor("9 rue du skate park", "Portland", "USA", "skaterzz", "9012864856")
                                , new Date()
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
