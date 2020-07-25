package Tests;

import Utils.PropertiesCache;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class loginAPITest {
    RequestSpecification specification;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = PropertiesCache.getInstance().getProperty("awsURL");
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType(ContentType.JSON);
        specification = builder.build();
    }

    @Test
    public void loginWithEmail() {
        String emailId = PropertiesCache.getInstance().getProperty("userEmail");
        String password = PropertiesCache.getInstance().getProperty("emailPassword");
        RequestSpecification request = RestAssured.given().spec(specification);
        String body = "{\n" +
                "\t\"userId\" : \"" + emailId + "\",\n" +
                "\t\"password\": \"" + password + "\"\n" +
                "}";
        Response response = request.body(body).post("/sign-in");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(jsonPathEvaluator.getString("accessToken"));
        Assert.assertEquals(jsonPathEvaluator.getString("roles[0].authority"), "ROLE_NORMAL_USER");

    }

    @Test
    public void loginWithMobile() {
        String mobile = PropertiesCache.getInstance().getProperty("userMobile");
        String password = PropertiesCache.getInstance().getProperty("mobilePassword");
        RequestSpecification request = RestAssured.given().spec(specification);
        String body = "{\n" +
                "\t\"userId\" : \"" + mobile + "\",\n" +
                "\t\"password\": \"" + password + "\"\n" +
                "}";
        Response response = request.body(body).post("/sign-in");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(jsonPathEvaluator.getString("accessToken"));
        Assert.assertEquals(jsonPathEvaluator.getString("roles[0].authority"), "ROLE_NORMAL_USER");

    }
}
