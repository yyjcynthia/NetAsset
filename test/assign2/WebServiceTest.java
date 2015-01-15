package assign2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

public class WebServiceTest extends TestCase {

    WebService webprice;

    @Override
    protected void setUp() {
        webprice = new WebService();
    }

    public void testCanary() {
        assertTrue(true);
    }

    public void testWebServiceGetValidPrice() throws IOException {
        String symbol = "GOOG";
        double price = webprice.readPrice(symbol);
        assertTrue(price > 0.0);
    }

    public void testWebServiceGetPriceFromInvalidTicker() {
        String symbol = "INVALIDTICKER";

        try {
            webprice.readPrice(symbol);
            fail("Expected RuntimeException for Invalid Ticker error");
        } catch (RuntimeException ex) {
            //:)
        }
    }

    public void testWebServiceUrlFail() {

        class WebServerEx extends WebService {

            WebServerEx() {
            }

            @Override
            protected URL getUrl(String stringurl) throws MalformedURLException {
                throw new MalformedURLException ();
            }
        }

        WebServerEx webpriceEx = new WebServerEx();

        String symbol = "GOOG";

        try {
            webpriceEx.readPrice(symbol);
            fail("Expected Exception for url format error");
        } catch (RuntimeException ex) {
            //:)
        }
    }

    public void testWebServiceConnectionFail() {

        class WebServerEx extends WebService {

            WebServerEx() {
            }

            @Override
            protected void connection(URL url) throws IOException {
                throw new IOException();
            }
        }

        WebServerEx webpriceEx = new WebServerEx();

        String symbol = "GOOG";

        try {
            webpriceEx.readPrice(symbol);
            fail("Expected Exception for network connection error");
        } catch (RuntimeException ex) {
            //:)
        }
    }

    public void testWebServiceTryScannerFail() {

        class WebServerEx extends WebService {

            WebServerEx() {
            }

            @Override
            protected Scanner scanner(URL url) throws IOException {
                throw new RuntimeException();
            }
        }

        WebServerEx webpriceEx = new WebServerEx();

        String symbol = "GOOG";

        try {
            webpriceEx.readPrice(symbol);
            fail("Expected Exception for scan web page error");
        } catch (RuntimeException ex) {
            //:)
        }
    }

    public void testWebServiceTryParseValueFail() {

        class WebServerEx extends WebService {

            WebServerEx() {
            }

            @Override
            protected double GetDoubleTypeStockPrice(String[] tokens) throws NumberFormatException {
                throw new NumberFormatException();
            }
        }

        WebServerEx webpriceEx = new WebServerEx();

        String symbol = "GOOG";

        try {
            webpriceEx.readPrice(symbol);
            fail("Expected Exception for get unexpcted data error");
        } catch (NumberFormatException ex) {
            //:)
        }
    }

}