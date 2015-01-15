package assign2_driver;

import assign2.ShareLogic;
import assign2.WebService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Driver {


    public static void main(String []args) throws IOException
    {
        WebService webService = new WebService();
        ShareLogic shareLogic = new ShareLogic(webService);

        Map<String, Integer> tickersAndSharesMap = new HashMap<>();
        tickersAndSharesMap.put("GOOG",1000);
        tickersAndSharesMap.put("AAPL",2000);

        Map<String, Double> shareValuesAndNetMap = new HashMap<>();
        shareValuesAndNetMap = shareLogic.shareValuesAndNet(tickersAndSharesMap);

        double netAsset = 0.0;
        System.out.println("Ticker\tNum_of_shares\t Share_value");
        for(Map.Entry<String, Double> entry1 : shareValuesAndNetMap.entrySet())
        {
            String symbol1 = entry1.getKey();
            if (symbol1 == "NetAsset")
            {
                netAsset = entry1.getValue();
                continue;
            }
            System.out.print(symbol1 + "\t");
            for(Map.Entry<String, Integer> entry2 : tickersAndSharesMap.entrySet())
            {
                String symbol2 = entry2.getKey();
                if(symbol1.equals(symbol2))
                {
                    System.out.print(entry2.getValue()+"\t\t");
                }
            }
            System.out.print(entry1.getValue() + "\n");

        }
        System.out.println("\t\tNetAsset: " + netAsset);
    }



}