package concepts;


import Utilities.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payload.coursePrice());

        //Print No of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println(count);

        //Print Purchase Amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        //Print Title of the first course
        String courseTitle = js.get("courses.title[0]");
        System.out.println(courseTitle);

        //Print All course titles and their respective Prices
        for (int i = 0; i < count; i++) {
            System.out.println(js.get("courses.title[" + i + "]").toString());
            System.out.println(js.get("courses.price[" + i + "]").toString());
        }


        // Print no of copies sold by RPA Course
        for (int i = 0; i < count; i++) {
            String titles = js.get("courses.title[" + i + "]");
            if (titles.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses.copies[" + i + "]");
                System.out.println("RPA course copies " + copies);
            }
        }

        // Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        for (int i = 0; i < count; i++) {
            int price = js.get("courses.price[" + i + "]");
            int copy = js.get("courses.copies[" + i + "]");
            int coursePrice = price * copy;
            sum = sum + coursePrice;
        }
        System.out.println(sum);
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, totalAmount);
//        if (sum == totalAmount) {
//            System.out.println("Matches");
//        } else {
//            System.out.println("does not match");
//        }
    }
}
/* Different ways to send a payload
1. As a String
2. As a json file
3. As a Json Object using hashmap
4. As a Json array using arrayList
 */




