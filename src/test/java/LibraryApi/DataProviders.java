package LibraryApi;

import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "BooksData")
    public static Object[][] bookId(){
        return new Object[][]{
                {"yfg", "356"},
                {"jffh","898"},
                {"hxdjs","836"}
        };
    }
}
