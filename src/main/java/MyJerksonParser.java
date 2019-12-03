import java.util.ArrayList;
import java.util.List;

public class MyJerksonParser {
    private List<GroceryItem> groceryList;
    private String rawData;

    public MyJerksonParser() {
        this.groceryList = new ArrayList<GroceryItem>();
    }

    public MyJerksonParser(String rawDataString) {
        this.groceryList = new ArrayList<GroceryItem>();
        this.rawData = rawDataString;
    }

    private List<String> getAllItems() {
        return null;
    }

    public List<GroceryItem> convertItemsToGroceryItemList() {
        List<String> allItemAsString = getAllItems();
        List<GroceryItem> allItems = new ArrayList<GroceryItem>();
        for(String line : allItemAsString) {
            // get name
            // get price
            // get type
            // get expiration
            // create new GroceryItem
            // add GroceryItem to list
        }
        return allItems;
    }
}
