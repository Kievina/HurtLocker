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

        System.out.println(jerkSONParser.getErrorCount());

//        System.out.println(jerkSONParser.getItemCount());
//        jerkSONParser.printGroceryList(list);
        jerkSONParser.getPriceCountsOfItem().values().stream()
//        .forEach((key, value) -> System.out.println(key + ":" + value));
                .forEach(value -> System.out.println("Item Name: " + value));
    }
}
