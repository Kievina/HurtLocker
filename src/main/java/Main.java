import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();
//        System.out.println(output);

        JerkSONParser jerkSONParser = new JerkSONParser(output);

        List<GroceryItem> list = jerkSONParser.convertToGroceryItemList();
        jerkSONParser.printGroceryList(list);
        //need to update expiration value in list to capture full date

        System.out.println(jerkSONParser.getErrorCount());
        for (Map.Entry<String, Integer> entry : jerkSONParser.getNameCount().entrySet()) {
            System.out.printf("name:    %s 		 seen: %s times\n", entry.getKey(), entry.getValue());
        }

    }
}
