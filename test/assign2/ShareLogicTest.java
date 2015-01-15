package assign2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

public class ShareLogicTest extends TestCase {

    ShareLogic shareLogic;

    @Override
    protected void setUp() {
        shareLogic = new ShareLogic();

    }

    public void testCanary() {
        assertTrue(true);
    }

    public void testCalculateShareValueOfAShare() {
        assertEquals(120379.0, shareLogic.calculateShareValue(100, 1203.79), 0.0001);
    }

    public void testCalculateShareValueOfAShareWithZeroShare() {
        assertEquals(0.0, shareLogic.calculateShareValue(0, 1203.79), 0.0001);
    }

    public void testCalculateShareValueWithZeroPrice() {
        assertEquals(0.0, shareLogic.calculateShareValue(100, 0.0), 0.0001);
    }

    public void testCalculateNetAssetWithNoValue() {
        List<Double> values = new ArrayList<>();
        assertEquals(0.0, shareLogic.calculateNetAsset(values));
    }

    public void testCalculateNetAssetWithOneValue() {
        List<Double> values = Arrays.asList(3.8);
        assertEquals(3.8, shareLogic.calculateNetAsset(values));
    }

    public void testCalculateNetAssetWithTwoValues() {
        List<Double> values = Arrays.asList(3.8, 2.0);
        assertEquals(5.8, shareLogic.calculateNetAsset(values));
    }

    public void testCalculateNetAssetWithMultipleValues() {
        List<Double> values = Arrays.asList(3.8, 2.0, 5.2, 8.0, 1.0, 0.0, 1.0, 3.1);
        assertEquals(24.1, shareLogic.calculateNetAsset(values));
    }

    public void testGetStockPriceForValidTicker() throws IOException {
        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) {
                return 111.22;
            }
        };

        shareLogic = new ShareLogic(priceService);
        assertEquals(111.22, shareLogic.getStockPrice("GOOG"));

    }

    public void testGetStockPriceForInvalidTicker() {

        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) throws RuntimeException {
                throw new RuntimeException("Exception: Invalid ticker");
            }
        };

        shareLogic = new ShareLogic(priceService);

        try {
            shareLogic.getStockPrice("INVALIDTICKER");
            fail("Expected Exception for invalid ticker");
        } catch (RuntimeException ex) {
            //:)
        }

    }

    public void testGetStockPriceForNetworkError(){

        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) throws RuntimeException {
                throw new RuntimeException("Exception: Network connection error");
            }
        };

        shareLogic = new ShareLogic(priceService);

        try {
            shareLogic.getStockPrice("INVALIDTICKER");
            fail("Expected Exception for network connection error");
        } catch (ShareLogicException ex) {
            //:)
        }
    }

    public void testGetStockPriceForNegativePriceError() {

        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) {
                return -121.1;
            }
        };

        shareLogic = new ShareLogic(priceService);

        try {
            shareLogic.getStockPrice("GOOG");
            fail("Expected Exception for Negative Price error");
        } catch (ShareLogicException ex) {
            //:)
        }

    }

    public void testShareValuesAndNetForValidTicker() throws IOException {
        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) {
                return 111.22;
            }
        };

        shareLogic = new ShareLogic(priceService);

        Map<String, Integer> shareValuesAndNetMap = new HashMap<>();
        shareValuesAndNetMap.put("AAPL", 200);
        shareValuesAndNetMap.put("GOOG", 100);

        Map<String, Double> actualShareValues = shareLogic.shareValuesAndNet(shareValuesAndNetMap);

        Map<String, Double> expectMap = new HashMap<>();
        expectMap.put("GOOG", 11122.0);
        expectMap.put("AAPL", 22244.0);
        expectMap.put("NetAsset", 33366.0);

        assertEquals(expectMap, actualShareValues);
    }

    public void testShareValuesAndNetForInvalidTicker() throws IOException{
        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) throws RuntimeException {

                    throw new RuntimeException("Exception: Invalid ticker");
            }
        };

        shareLogic = new ShareLogic(priceService);

        Map<String, Integer> shareValuesAndNetMap = new HashMap<>();
        shareValuesAndNetMap.put("AAPL", 200);
        shareValuesAndNetMap.put("INVALIDTICKER", 100);

        try {
            Map<String, Double> actualShareValues = shareLogic.shareValuesAndNet(shareValuesAndNetMap);
            fail("Expected Exception for invalid ticker");
        } catch (ShareLogicException ex) {
            //:)
        }

    }

    public void testShareValuesAndNetForNetworkError() throws IOException {
        PriceService priceService = new PriceService() {

            @Override
            public double readPrice(String ticker) throws RuntimeException {
                throw new RuntimeException("Exception: Network connection error");
            }
        };

        shareLogic = new ShareLogic(priceService);

        Map<String, Integer> shareValuesAndNetMap = new HashMap<>();
        shareValuesAndNetMap.put("AAPL", 200);
        shareValuesAndNetMap.put("GOOG", 100);

        try {
            Map<String, Double> actualShareValues = shareLogic.shareValuesAndNet(shareValuesAndNetMap);
            fail("Expected Exception for network connection error");
        } catch (ShareLogicException ex) {
            //:)
        }

    }

    public void testShareValuesAndNetForNegativePriceError() throws IOException {
        PriceService priceService = new PriceService() {
            @Override
            public double readPrice(String ticker) {
                return -121.1;
            }
        };

        shareLogic = new ShareLogic(priceService);
        Map<String, Integer> shareValuesAndNetMap = new HashMap<>();
        shareValuesAndNetMap.put("AAPL", 200);
        shareValuesAndNetMap.put("GOOG", 100);

        try {
            Map<String, Double> actualShareValues = shareLogic.shareValuesAndNet(shareValuesAndNetMap);
            fail("Expected Exception for negative price error");
        } catch (ShareLogicException ex) {
            //:)
        }

    }
}