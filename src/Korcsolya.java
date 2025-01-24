import java.util.Locale;

public class Korcsolya {
    private String nev;
    private String orszag;
    private double techPontszam;
    private double kompPontszam;
    private int levonas;
    private double osszPont;

    public Korcsolya(String adatsor) {
        String[] adatok = adatsor.split(";");
        this.nev = adatok[0];
        this.orszag = adatok[1];
        this.techPontszam = Double.parseDouble(adatok[2].replace(',', '.'));
        this.kompPontszam = Double.parseDouble(adatok[3].replace(',', '.'));
        this.levonas = Integer.parseInt(adatok[4]);
        this.osszPont = 0;
    }

    public String getNev() {
        return nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public double getTechPontszam() {
        return techPontszam;
    }

    public double getKompPontszam() {
        return kompPontszam;
    }

    public int getLevonas() {
        return levonas;
    }

    public double getOsszPont() {
        return osszPont;
    }

    public void setOsszPont(double osszPont) {
        this.osszPont = osszPont;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (%s): %.2f pont", nev, orszag, osszPont);
    }
}
