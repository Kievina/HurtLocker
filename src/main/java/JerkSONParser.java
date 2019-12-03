import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JerkSONParser {
    private List<GroceryItem> groceryList;
    private String rawData;

    public JerkSONParser() {
        this.groceryList = new ArrayList<GroceryItem>();
    }

    public JerkSONParser(String rawDataString) {
        this.groceryList = new ArrayList<GroceryItem>();
        this.rawData = rawDataString;
    }

    public String getRawData() {
        return rawData;
    }

    private List<GroceryItem> getGroceryList() {
        return groceryList;
    }

    public Map<String, Integer> getNameCount() {
        int countApples = 0;
        int countBread = 0;
        int countCookies = 0;
        int countMilk = 0;
        Map<String, Integer> map = new HashMap<String, Integer>();

        Pattern pApples = Pattern.compile("apple", Pattern.CASE_INSENSITIVE);
        Pattern pBread = Pattern.compile("bread", Pattern.CASE_INSENSITIVE);
        Pattern pCookies = Pattern.compile("c[o0][o0]kies", Pattern.CASE_INSENSITIVE);
        Pattern pMilk = Pattern.compile("milk", Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < groceryList.size(); i++) {
            Matcher m1 = pApples.matcher(groceryList.get(i).getName());
            Matcher m2 = pBread.matcher(groceryList.get(i).getName());
            Matcher m3 = pCookies.matcher(groceryList.get(i).getName());
            Matcher m4 = pMilk.matcher(groceryList.get(i).getName());


            while (m1.find())
                countApples++;
            while (m2.find())
                countBread++;
            while (m3.find())
                countCookies++;
            while (m4.find())
                countMilk++;
        }

        map.put("Milk", countMilk);
        map.put("Bread", countBread);
        map.put("Cookies", countCookies);
        map.put("Apples", countApples);


        return map;
    }

    private int getPriceCount() {

        return 0;
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
        String objName;
        String objPrice;
        String objType;
        String objExp;
        List<String> itemsAsString = getItemStrings();

        for (String itemString : itemsAsString) {
            objName = findName(itemString);             // get name
            objPrice = findPrice(itemString);           // get price
            objType = findType(itemString);             // get type
            objExp = findExpiration(itemString);        // get expiration

            // create new GroceryItem
            GroceryItem newItem = new GroceryItem(objName, objPrice, objType, objExp);

            // add GroceryItem to list
            groceryList.add(newItem);
        }
        return groceryList;
    }

    private List<String> getItemStrings() {
        Pattern p = Pattern.compile("##");
        String[] splitString = p.split(replaceFieldSeparators());
        return Arrays.asList(splitString);
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
        Pattern expValue = Pattern.compile("expiration\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = expValue.matcher(itemText);
        if (m.find())
            return m.group(1);
        else
            return "";
    }

    public String replaceFieldSeparators() {
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

    public void printGroceryList(List<GroceryItem> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i).toString());
        }
    }
}