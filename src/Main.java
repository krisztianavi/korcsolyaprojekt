import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static List<Korcsolya> rovidProgList = new ArrayList<>();
    static List<Korcsolya> dontoList = new ArrayList<>();

    public static void main(String[] args) {
        try (BufferedReader rovidReader = new BufferedReader(new InputStreamReader(new FileInputStream(".idea/rovidprogram.csv"), StandardCharsets.UTF_8));
             BufferedReader dontoReader = new BufferedReader(new InputStreamReader(new FileInputStream(".idea/donto.csv"), StandardCharsets.UTF_8))) {
            rovidReader.readLine();
            String line;
            while ((line = rovidReader.readLine()) != null) {
                rovidProgList.add(new Korcsolya(line));
            }
            dontoReader.readLine();
            while ((line = dontoReader.readLine()) != null) {
                dontoList.add(new Korcsolya(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("2. feladat");
        System.out.println("\tA rövidprogramban " + rovidProgList.size() + " induló volt");

        System.out.println("3. feladat");
        boolean bejutottE = dontoList.stream().anyMatch(k -> k.getOrszag().equals("HUN"));
        if (bejutottE) {
            System.out.println("\tA magyar versenyző bejutott a kűrbe");
        } else {
            System.out.println("\tA magyar versenyző nem jutott be a kűrbe");
        }

        System.out.println("5. feladat");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\tKérem a versenyző nevét: ");
        String keresettNev = scanner.nextLine();
        boolean voltIlyen = rovidProgList.stream().anyMatch(k -> k.getNev().equals(keresettNev));
        if (!voltIlyen) {
            System.out.println("\tIlyen nevű induló nem volt");
        } else {
            System.out.println("6. feladat");
            double osszPont = osszPontSzam(keresettNev);
            System.out.printf("\tA versenyző összpontszáma: %.2f\n", osszPont);
        }

        System.out.println("7. feladat");
        Map<String, Long> orszagok = dontoList.stream()
                .collect(Collectors.groupingBy(Korcsolya::getOrszag, Collectors.counting()));

        orszagok.forEach((orszag, count) -> {
            if (count > 1) {
                System.out.println("\t" + orszag + ": " + count + " versenyző");
            }
        });

        System.out.println("8. feladat: vegeredmeny.csv");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vegeredmeny.csv"), StandardCharsets.UTF_8))) {
            dontoList.forEach(k -> k.setOsszPont(osszPontSzam(k.getNev())));
            dontoList.sort(Comparator.comparingDouble(Korcsolya::getOsszPont).reversed());

            int helyezes = 1;
            for (Korcsolya k : dontoList) {
                try {
                    writer.write(String.format("%d;%s;%s;%.2f\n", helyezes++, k.getNev(), k.getOrszag(), k.getOsszPont()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double osszPontSzam(String nev) {
        double osszPont = 0;
        for (Korcsolya k : rovidProgList) {
            if (k.getNev().equals(nev)) {
                osszPont += k.getTechPontszam() + k.getKompPontszam() - k.getLevonas();
            }
        }
        for (Korcsolya k : dontoList) {
            if (k.getNev().equals(nev)) {
                osszPont += k.getTechPontszam() + k.getKompPontszam() - k.getLevonas();
            }
        }
        return osszPont;
    }
}
