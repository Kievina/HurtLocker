import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JerkSONParser {

    private String rawData;

    public JerkSONParser() {}

    public JerkSONParser(String rawDataString) {
        this.rawData = rawDataString;
    }

    public String getRawData() {
        return rawData;
    }

    public String replaceObjectSeparators() {
        String originalText = rawData;

        Pattern pItemSeparator = Pattern.compile("##.");
        Matcher mItemSeparator = pItemSeparator.matcher(originalText);
        String result = mItemSeparator.replaceAll("},\n n");

        Pattern p = Pattern.compile("##");
        Matcher m = p.matcher(result);
        return m.replaceAll("}");
    }

    public String replaceFieldSeparators(String text) {
        Pattern pFieldSeparator1 = Pattern.compile(".name.|name.", Pattern.CASE_INSENSITIVE);
        Pattern pFieldSeparator2 = Pattern.compile(".price.", Pattern.CASE_INSENSITIVE);
        Pattern pFieldSeparator3 = Pattern.compile(".type.", Pattern.CASE_INSENSITIVE);
        Pattern pFieldSeparator4 = Pattern.compile(".expiration.", Pattern.CASE_INSENSITIVE);

        Matcher mFieldSeparator1 = pFieldSeparator1.matcher(text);
        String field1Corrected = mFieldSeparator1.replaceAll("{name:");

        Matcher mFieldSeparator2 = pFieldSeparator2.matcher(field1Corrected);
        String field2Corrected = mFieldSeparator2.replaceAll(";price:");

        Matcher mFieldSeparator3 = pFieldSeparator3.matcher(field2Corrected);
        String field3Corrected = mFieldSeparator3.replaceAll(";type:");

        Matcher mFieldSeparator4 = pFieldSeparator4.matcher(field3Corrected);
        return mFieldSeparator4.replaceAll(";expiration:");
    }

    public List<String> createGroceryList(String text) {
        Pattern p = Pattern.compile("");
        return null;
    }

}
