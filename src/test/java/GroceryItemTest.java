import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroceryItemTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void groceryItemConstructorTest() {
        String expectedName = "milk";
        String expectedPrice = "2.50";
        String expectedType = "food";
        String expectedExpiration = "12/25/2019";
        GroceryItem groceryItem = new GroceryItem("milk", "2.50", "food", "12/25/2019");

        Assert.assertEquals(expectedName, groceryItem.getName());
        Assert.assertEquals(expectedPrice, groceryItem.getPrice());
        Assert.assertEquals(expectedType, groceryItem.getType());
        Assert.assertEquals(expectedExpiration, groceryItem.getExpiration());
    }

    @Test
    public void getNameTest() {
    }

    @Test
    public void setNameTest() {
        String expectedName = "milk";
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName(expectedName);

        Assert.assertEquals(expectedName, groceryItem.getName());
    }

    @Test
    public void getPriceTest() {
    }

    @Test
    public void setPriceTest() {
        String expectedPrice = "2.50";
        GroceryItem groceryItem = new GroceryItem();

        groceryItem.setPrice(expectedPrice);

        Assert.assertEquals(expectedPrice, groceryItem.getPrice());
    }

    @Test
    public void getTypeTest() {
    }

    @Test
    public void setTypeTest() {
        String expectedType = "food";
        GroceryItem groceryItem = new GroceryItem();

        groceryItem.setType(expectedType);

        Assert.assertEquals(expectedType, groceryItem.getType());
    }

    @Test
    public void getExpirationTest() {
    }

    @Test
    public void setExpirationTest() {
        String expectedExpiration = "12/25/2019";
        GroceryItem groceryItem = new GroceryItem();

        groceryItem.setExpiration(expectedExpiration);

        Assert.assertEquals(expectedExpiration, groceryItem.getExpiration());
    }

    @Test
    public void toStringTest() {
        GroceryItem groceryItem = new GroceryItem("milk", "2.50", "food", "12/25/2019");
        String expectedOutput = "GroceryItem {name='milk', price=2.5, type='food', expiration='12/25/2019'}";
        Assert.assertEquals(expectedOutput, groceryItem.toString());
//        System.out.println(groceryItem.toString());
    }
}