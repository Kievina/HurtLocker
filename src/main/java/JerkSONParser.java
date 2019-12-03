import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JerkSONParser {
    private List<GroceryItem> groceryList;
    private String rawData;

    public JerkSONParser() {
        this.groceryList = new ArrayList<>();
    }

    public JerkSONParser(String rawDataString) {
        this.groceryList = new ArrayList<>();
        this.rawData = rawDataString;
    }

    public String getRawData() {
        return rawData;
    }

    private List<GroceryItem> getGroceryList() {
        return groceryList;
    }

    public String printReport1() {
        String result = "";
        for (Map.Entry<String, Long> entry : mapItemCounts().entrySet()) {
            result += entry.getKey() + " : " + entry.getValue() + '\n';
        }
        return result;
    }

    public String printReport2(String itemName) {
        String result = "";
//        if(getPriceCountsOfItem(itemName).entrySet().get(itemName).size() == 1) {
//        for (Map.Entry<String, Map<String, Long>> entry : getPriceCountsOfItem(itemName).entrySet()) {
//            result += "Price " + entry.getKey() + " : " + entry.getValue() + '\n';
//        }
        return result;
    }

    private Map<String, Map<String, Long>> getPriceCountsOfItem(String itemName) {
        Map<String, Map<String, Long>> linkedMap = new LinkedHashMap<>();
        Map<String, Long> map = new LinkedHashMap<>();

//        List<String> allItemPrices = groceryList.stream().filter(item -> item.getName().equals(itemName))
//                .map(item -> item.getPrice()).collect(Collectors.toList());
//        Set<String> uniquePrices = new HashSet<>(allItemPrices);
//        for (String uniquePrice : uniquePrices) {
//            map.put(uniquePrice, allItemPrices.stream().filter(price -> price.equals(uniquePrice)).count());
//        }
//        linkedMap.put(itemName, map);
        List<GroceryItem> cleanList = groceryList.stream().filter(item -> !item.getPrice().equals("type"))
                .collect(Collectors.toList());
        linkedMap.put("Milk",
                cleanList.stream().filter(x -> x.getName().equals("Milk"))
                        .collect(Collectors.groupingBy(GroceryItem::getPrice, Collectors.counting())));
        linkedMap.put("Bread",
                cleanList.stream().filter(x -> x.getName().equals("Bread"))
                        .collect(Collectors.groupingBy(GroceryItem::getPrice, Collectors.counting())));
        linkedMap.put("Cookies",
                cleanList.stream().filter(x -> x.getName().equals("Cookies"))
                        .collect(Collectors.groupingBy(GroceryItem::getPrice, Collectors.counting())));
        linkedMap.put("Apples",
                cleanList.stream().filter(x -> x.getName().equals("Milk"))
                        .collect(Collectors.groupingBy(GroceryItem::getPrice, Collectors.counting())));
        return linkedMap;
    }


    private Map<String, Long> mapItemCounts() {
        List<GroceryItem> newList = groceryList.stream().filter(item -> !item.getPrice().equals("type"))
                .collect(Collectors.toList());
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("Milk", newList.stream().filter(item -> item.getName().equals("Milk")).count()); // 6 times - correct
        map.put("Bread", newList.stream().filter(item -> item.getName().equals("Bread")).count()); // 6 times - correct
        map.put("Cookies", newList.stream().filter(item -> item.getName().equals("Cookies")).count()); // 8 times - correct
        map.put("Apples", newList.stream().filter(item -> item.getName().equals("Apples")).count()); // 4 times - correct
        return map;
    }

    public int getErrorCount() {
        int count = 0;
        Pattern p1 = Pattern.compile("price", Pattern.CASE_INSENSITIVE);
        Pattern p2 = Pattern.compile("type", Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < groceryList.size(); i++) {
            Matcher m1 = p1.matcher(groceryList.get(i).getName());
            Matcher m2 = p2.matcher(groceryList.get(i).getPrice());
            while (m1.find() | m2.find())
                count++;
        }
        return count;
    }

    public List<GroceryItem> convertToGroceryItemList() {
        List<String> itemsAsString = getItemStrings();
        for (String itemString : itemsAsString) {
            // create new GroceryItem and add to list
            groceryList.add(new GroceryItem(findName(itemString), findPrice(itemString),
                    findType(itemString), findExpiration(itemString)));
        }
        replaceNames();
        return groceryList;
    }

    private List<String> getItemStrings() {
        Pattern p = Pattern.compile("##");
        String[] splitString = p.split(replaceFieldSeparators());
        return Arrays.asList(splitString);
    }

    private String replaceFieldSeparators() {
        String originalText = rawData;
        Pattern pFieldSeparator1 = Pattern.compile("name.", Pattern.CASE_INSENSITIVE);
        Pattern pFieldSeparator2 = Pattern.compile(".price.", Pattern.CASE_INSENSITIVE);
        Pattern pFieldSeparator3 = Pattern.compile(".type.", Pattern.CASE_INSENSITIVE);
        Pattern pFieldSeparator4 = Pattern.compile(".expiration.", Pattern.CASE_INSENSITIVE);

        Matcher mFieldSeparator1 = pFieldSeparator1.matcher(originalText);
        String field1Corrected = mFieldSeparator1.replaceAll("name:");

        Matcher mFieldSeparator2 = pFieldSeparator2.matcher(field1Corrected);
        String field2Corrected = mFieldSeparator2.replaceAll(";price:");

        Matcher mFieldSeparator3 = pFieldSeparator3.matcher(field2Corrected);
        String field3Corrected = mFieldSeparator3.replaceAll(";type:");

        Matcher mFieldSeparator4 = pFieldSeparator4.matcher(field3Corrected);
        return mFieldSeparator4.replaceAll(";expiration:");
    }

    private void replaceNames() {
        for (int i = 0; i < groceryList.size(); i++) {
            if (appleMatcher(groceryList.get(i).getName()).find())
                groceryList.get(i).setName("Apples");
            else if (breadMatcher(groceryList.get(i).getName()).find())
                groceryList.get(i).setName("Bread");
            else if (cookieMatcher(groceryList.get(i).getName()).find())
                groceryList.get(i).setName("Cookies");
            else if (milkMatcher(groceryList.get(i).getName()).find())
                groceryList.get(i).setName("Milk");
            else
                groceryList.get(i).setName("price");
        }
    }

    private Matcher appleMatcher(String text) {
        Pattern pApples = Pattern.compile("apples", Pattern.CASE_INSENSITIVE);
        return pApples.matcher(text);
    }

    private Matcher breadMatcher(String text) {
        Pattern pBread = Pattern.compile("bread", Pattern.CASE_INSENSITIVE);
        return pBread.matcher(text);
    }

    private Matcher cookieMatcher(String text) {
        Pattern pCookies = Pattern.compile("c[o0][o0]kies", Pattern.CASE_INSENSITIVE);
        return pCookies.matcher(text);
    }

    private Matcher milkMatcher(String text) {
        Pattern pMilk = Pattern.compile("milk", Pattern.CASE_INSENSITIVE);
        return pMilk.matcher(text);
    }

    private String findName(String text) {
        Pattern nameValue = Pattern.compile("name\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = nameValue.matcher(text);
        if (m.find())
            return m.group(1);
        else
            return "";
    }

    private String findPrice(String itemText) {
        Pattern priceValue = Pattern.compile("price\\W+([0-9].[0-9]+|\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = priceValue.matcher(itemText);
        if (m.find())
            return m.group(1);
        else
            return "";
    }

    private String findType(String itemText) {
        Pattern typeValue = Pattern.compile("type\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = typeValue.matcher(itemText);
        if (m.find())
            return m.group(1);
        else
            return "";
    }

    private String findExpiration(String itemText) {
        Pattern expValue = Pattern.compile("expiration:(\\d+/\\d+/\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher m = expValue.matcher(itemText);
        if (m.find())
            return m.group(1);
        else
            return "";
    }

    public void printGroceryList(List<GroceryItem> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i).toString());
        }
    }
}