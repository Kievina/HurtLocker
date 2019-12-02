import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JerkSONParser {

    private String rawData;

    public JerkSONParser() {
    }

    public JerkSONParser(String rawDataString) {
        this.rawData = rawDataString;
    }

    public String getRawData() {
        return rawData;
    }

    public List<GroceryItem> createGroceryList(GroceryItem groceryItem) {
        List<GroceryItem> groceryList = new ArrayList<GroceryItem>();
        groceryList.add(groceryItem);
        return groceryList;
    }

    public GroceryItem createItem(String text) {
        return new GroceryItem(findItemName(text), Double.valueOf(findItemPrice(text)), findItemType(text), findItemExpirationDate(text));
    }

    public String findItemName(String text) {
        Pattern nameValue = Pattern.compile("name\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = nameValue.matcher(text);
        if (m.find())
            return m.group(1);
//            return m.groupCount(); // returns 1
        else
            return "Not Found";
    }

    public String findItemPrice(String text) {
        Pattern nameValue = Pattern.compile("price\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = nameValue.matcher(text);
        if (m.find())
            return m.group(1);
        else
            return " ";
    }

    public String findItemType(String text) {
        Pattern nameValue = Pattern.compile("type\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = nameValue.matcher(text);
        if (m.find())
            return m.group(1);
        else
            return " ";
    }

    public String findItemExpirationDate(String text) {
        Pattern nameValue = Pattern.compile("expiration\\W+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = nameValue.matcher(text);
        if (m.find())
            return m.group(1);
        else
            return " ";
    }
}























//    public String replaceObjectSeparators() {
//        String originalText = rawData;
//
//        Pattern pItemSeparator = Pattern.compile("##.");
//        Matcher mItemSeparator = pItemSeparator.matcher(originalText);
//        String result = mItemSeparator.replaceAll("},\n n");
//
//        Pattern p = Pattern.compile("##");
//        Matcher m = p.matcher(result);
//        return m.replaceAll("}");
//    }
//
//    public String replaceFieldSeparators() {
//        String text = replaceObjectSeparators();
//        Pattern pFieldSeparator1 = Pattern.compile(".name.|name.", Pattern.CASE_INSENSITIVE);
//        Pattern pFieldSeparator2 = Pattern.compile(".price.", Pattern.CASE_INSENSITIVE);
//        Pattern pFieldSeparator3 = Pattern.compile(".type.", Pattern.CASE_INSENSITIVE);
//        Pattern pFieldSeparator4 = Pattern.compile(".expiration.", Pattern.CASE_INSENSITIVE);
//
//        Matcher mFieldSeparator1 = pFieldSeparator1.matcher(text);
//        String field1Corrected = mFieldSeparator1.replaceAll("{name:");
//
//        Matcher mFieldSeparator2 = pFieldSeparator2.matcher(field1Corrected);
//        String field2Corrected = mFieldSeparator2.replaceAll(",price:");
//
//        Matcher mFieldSeparator3 = pFieldSeparator3.matcher(field2Corrected);
//        String field3Corrected = mFieldSeparator3.replaceAll(",type:");
//
//        Matcher mFieldSeparator4 = pFieldSeparator4.matcher(field3Corrected);
//        return mFieldSeparator4.replaceAll(",expiration:");
//    }
