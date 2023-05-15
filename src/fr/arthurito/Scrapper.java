package fr.arthurito.scrapper;

import java.util.Scanner;

public class Scrapper {

    public static void main(String[] args) {
        scrap();
    }
    
    public static void scrap() {
    	Scanner scanner = new Scanner(System.in);

        boolean bool = true;
        Integer choix;


        while (bool) {
            System.out.println("1. générer le site web\n");
            System.out.print("0. quitter le programme\n");
            System.out.print("Choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) {
                ScrapIG.scrapeWebsite("https://www.instant-gaming.com/fr/jeux/steam/", "steam.html");
                ScrapIG.scrapeWebsite("https://www.instant-gaming.com/fr/jeux/nintendo-eshop/", "nintendo.html");
                ScrapIG.scrapeWebsite("https://www.instant-gaming.com/fr/jeux/playstation-store/", "playstation.html");
                ScrapIG.scrapeWebsite("https://www.instant-gaming.com/fr/jeux/battle-net/", "battle-net.html");

                ScrapIG.openHTMLFile("steam.html");
            }


            if (choix == 0) {
                System.out.println("Vous avez quitter le programme");
                bool = false;
                break;
            }
        }
        scanner.close();
    }

}