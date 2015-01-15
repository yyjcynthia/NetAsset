package assign2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareLogic {

    private PriceService priceService;

    ShareLogic() {
    }

    public ShareLogic(PriceService givenPriceService) {
        priceService = givenPriceService;
    }

    public double calculateShareValue(int numOfShare,
            double priceOfStock) {
        return numOfShare * priceOfStock;
    }

    public double calculateNetAsset(List<Double> values) {
        double netAssetValue = 0.0;

        for (double value : values) {
            netAssetValue += value;
        }

        return netAssetValue;
    }

    public double getStockPrice(String ticker){

        double price = 0.0;
        try {
            price = priceService.readPrice(ticker);
        } catch (RuntimeException ex) {
            throw new ShareLogicException();

        }

        if (price < 0.0) {
            throw new ShareLogicException();
        }

        return price;
    }

    public Map<String, Double> shareValuesAndNet(Map<String, Integer> tickers){
        List<Double> shareValueList = new ArrayList<>();
        Map<String, Double> shareValuesAndNetMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : tickers.entrySet()) {
            String symbol = entry.getKey();
            int number = entry.getValue();
            double stockPrice = getStockPrice(symbol);
            double shareValue = calculateShareValue(number, stockPrice);
            shareValueList.add(shareValue);
            shareValuesAndNetMap.put(symbol, shareValue);
        }

        double netValue = calculateNetAsset(shareValueList);
        shareValuesAndNetMap.put("NetAsset", netValue);
        return shareValuesAndNetMap;
    }
}