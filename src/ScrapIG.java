package fr.arthurito.scrapper;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ScrapIG {

	public static void openHTMLFile(String fileName) {
        try {
            File file = new File(fileName);
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void scrapeWebsite(String url, String html) {

        try {

            Document document = Jsoup.connect(url).get();

            System.out.println("Document récupéré : " + document.title());

            Elements elements = document.select("div.item");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><title>Données Scrapées</title><meta charset=\"UTF-8\">");
            htmlContent.append("<style>");
            htmlContent.append("body {");
            htmlContent.append("font-family: Arial, sans-serif;");
            htmlContent.append("background-color: #272727");
            htmlContent.append("}");

            htmlContent.append(".container {");
            htmlContent.append("display: flex;");
            htmlContent.append("flex-wrap: wrap;");
            htmlContent.append("justify-content: center;");
            htmlContent.append("}");

            htmlContent.append(".game-card {");
            htmlContent.append("display: flex;");
            htmlContent.append("flex-direction: column;");
            htmlContent.append("align-items: center;");
            htmlContent.append("position: relative;");
            htmlContent.append("width: 320px;");
            htmlContent.append("padding: 16px;");
            htmlContent.append("box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);");
            htmlContent.append("text-align: center;");
            htmlContent.append("margin: 16px;");
            htmlContent.append("background-color: rgba(16,16,16,.4)");
            htmlContent.append("transition: transform 0.3s ease;");
            htmlContent.append("}");

            htmlContent.append(".game-card:hover {");
            htmlContent.append("box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4);");
            htmlContent.append("}");

            htmlContent.append(".game-card img {");
            htmlContent.append("width: 100%;");
            htmlContent.append("height: 280px;");
            htmlContent.append("object-fit: cover;");
            htmlContent.append("border-radius: 8px;");
            htmlContent.append("margin-bottom: 16px;");
            htmlContent.append("transition: transform 0.3s ease;");
            htmlContent.append("}");

            htmlContent.append(".game-card:hover img {");
            htmlContent.append("transform: scale(1.1);");
            htmlContent.append("}");

            htmlContent.append(".game-card p.title {");
            htmlContent.append("font-size: 18px;");
            htmlContent.append("font-weight: bold;");
            htmlContent.append("color: white");
            htmlContent.append("}");

            htmlContent.append(".game-card p.price {");
            htmlContent.append("font-size: 16px;");
            htmlContent.append("color: #888;");
            htmlContent.append("}");

            htmlContent.append(".pagination {");
            htmlContent.append("display: flex;");
            htmlContent.append("justify-content: center;");
            htmlContent.append("margin-top: 20px;");
            htmlContent.append("}");

            htmlContent.append(".pagination button {");
            htmlContent.append("background-color: orange;");
            htmlContent.append("color: #fff;");
            htmlContent.append("border: none;");
            htmlContent.append("border-radius: 4px;");
            htmlContent.append("padding: 8px 16px;");
            htmlContent.append("margin: 0 4px;");
            htmlContent.append("cursor: pointer;");
            htmlContent.append("width: 100px;");
            htmlContent.append("transition: background-color 0.3s;");
            htmlContent.append("}");

            htmlContent.append(".pagination button:hover {");
            htmlContent.append("background-color: #d88e05");
            htmlContent.append("}");

            htmlContent.append(".game-card .discount {");
            htmlContent.append("position: absolute;");
            htmlContent.append("bottom: 144px;");
            htmlContent.append("left: 16px;");
            htmlContent.append("background-color: orange;");
            htmlContent.append("color: white;");
            htmlContent.append("padding: 4px;");
            htmlContent.append("border-radius: 8px;");
            htmlContent.append("}");

            htmlContent.append("</style>");
            htmlContent.append("</head><body>");

            if (elements.isEmpty()) {
                htmlContent.append("<p>Aucun élément trouvé.</p>");
            } else {

                htmlContent.append("<div class=\"pagination\">");
                htmlContent.append("<button onclick=\"window.location.href='steam.html'\">Steam</button>");
                htmlContent.append("<button onclick=\"window.location.href='nintendo.html'\">Nintendo</button>");
                htmlContent.append("<button onclick=\"window.location.href='playstation.html'\">PlayStation</button>");
                htmlContent.append("<button onclick=\"window.location.href='battle-net.html'\">Battle-Net</button>");

                htmlContent.append("</div>");

                htmlContent.append("<div class=\"container\">");

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("scrapped_data.txt", true), StandardCharsets.UTF_8));
                
                for (Element element : elements) {
                    String imageUrl = element.select("img").attr("data-src");
                    String title = element.select("div.name").text();
                    String price = element.select("div.price").text();
                    String discount = element.select("div.discount").text();

                    htmlContent.append("<div class=\"game-card\">");
                    htmlContent.append("<img src=\"").append(imageUrl).append("\" alt=\"Image\">");
                    htmlContent.append("<p class=\"discount\">").append(discount).append("</p>");
                    htmlContent.append("<p class=\"title\">").append(title).append("</p>");
                    htmlContent.append("<p class=\"price\">").append(price).append("</p>");
                    htmlContent.append("</div>");

                    writer.write("Image : " + imageUrl + "\n");
                    writer.write("Title: " + title + "\n");
                    writer.write("Price: " + price + "\n");
                    writer.write("Discount: " + discount + "\n");
                    writer.write("\n");
                }
                htmlContent.append("</div>");

            }

            htmlContent.append("</body></html>");

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(html), StandardCharsets.UTF_8));
            writer.write(htmlContent.toString());
            writer.close();

            System.out.println("Le fichier HTML a été généré avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
