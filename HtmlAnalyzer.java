import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;

public class HtmlAnalyzer {
    public static void main(String[] args) throws IOException {

        try {
            URL pageLocation = new URL(args[0]);
            Scanner in = new Scanner(pageLocation.openStream());

            if (validateHTML(in))
                Analyzer(pageLocation);           
            in.close();

        } catch (Exception e) {
            System.out.println("URL connection error");
        }
    }

    public static boolean validateHTML(Scanner scanner) throws IOException {
        int openTags = 0;
        int closeTags = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("<")) {
                if (line.contains("/")) {
                    closeTags++;
                } else {
                    openTags++;
                }
            }
        }
        if (openTags == closeTags) {
            return true;
        }
        System.out.println("HTML Malformed");
        return false;
    }

    public static void Analyzer(URL page) throws IOException {

        Scanner in = new Scanner(page.openStream());

        ArrayList<String> phrase = new ArrayList<String>();
        ArrayList<Integer> index = new ArrayList<Integer>();
        int c = 0;

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (!line.contains("/") && line != "") {
                if (line.contains("<")) {
                    c++;
                } else {
                    phrase.add(line.trim());
                    index.add(c);
                }
            } else if (line != "") {
                c--;
            }

        }

        int max = 0;
        for (int i = 0; i < index.size(); i++) {
            int value = index.get(i);
            if (value > max) {
                max = value;
                c = i;
            }
        }

        System.out.println(phrase.get(c));
        in.close();
    }

}