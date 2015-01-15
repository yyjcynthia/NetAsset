package assign2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebService implements PriceService {

    @Override
    public double readPrice(String symbol) {

        String stringurl = "http://ichart.finance.yahoo.com/table.csv?s=" + symbol;
        URL url = tryUrl(stringurl);
        tryConnection(url);

        Scanner scanner = tryScanner(url);
        scanner.nextLine();
        String[] tokens = scanner.nextLine().split(",");
        return (tryGetDoubleTypeStockPrice(tokens));
    }

    private Double tryGetDoubleTypeStockPrice(String[] tokens) throws NumberFormatException {

        try {
            return GetDoubleTypeStockPrice(tokens);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException();
        }
    }

    protected double GetDoubleTypeStockPrice(String[] tokens) throws NumberFormatException {
        return Double.parseDouble(tokens[6]);
    }

    private Scanner tryScanner(URL url) throws RuntimeException {

        try {
            return scanner(url);
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    protected Scanner scanner(URL url) throws IOException {

        return new Scanner(url.openStream());
    }

    private void tryConnection(URL url) throws RuntimeException {

        try {
            connection(url);
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    protected void connection(URL url) throws IOException {
        url.openConnection();
    }

    private URL tryUrl(String stringurl) throws RuntimeException {

        try {
            return getUrl(stringurl);
        } catch (MalformedURLException ex) {
            throw new RuntimeException();
        }
    }

    protected URL getUrl(String stringurl) throws MalformedURLException {

        return new URL(stringurl);
    }

}