import helper.Helper
import helper.JsonParser
import helper.TwitterCred
import network.TwitterClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by KseniaB on 12/21/2016.
 */
class TwitterApiTests extends Specification {

    @Shared
    def text = "Tweeting" + Math.abs(new Random().nextInt()) % 600 + 1
    TwitterClient client = new TwitterClient(TwitterCred.KEY, TwitterCred.SECRET, TwitterCred.TOKEN, TwitterCred.TOKEN_SECRET)

    def 'get twitter home time line'() {
        when: 'add new status'
        client.postStatus(expectedStatus, null)
        then: 'get home time line'
        String result = client.getHomeTimeLine("1", null, null, "true", "true", "false")
        then: 'verify that response code is 200'
        assert client.getResponseCode() == 200

        and: 'verify that difference between actual and expected date is less than 5 seconds'
        String actualDate = JsonParser.getValueFromJsonBy(result, "created_at")
        assert Helper.getDifferenceBetween(expectedDate, actualDate) <= 5

        and: 'verify that json contains correct retweet count'
        String actualRetweetCount = JsonParser.getValueFromJsonBy(result, "retweet_count")
        assert actualRetweetCount == expectedRetweetCount

        and: 'verify that json contains correct status'
        String actualStatus = JsonParser.getValueFromJsonBy(result, "text")
        assert actualStatus == expectedStatus

        where:
        expectedStatus = "My Tweet " + Math.abs(new Random().nextInt()) % 600 + 1
        expectedRetweetCount = "0"
        expectedDate = new Date().format('E MMM dd HH:mm:ss Z yyyy')
    }

    def 'destroy status'() {
        when: 'add new status'
        client.postStatus(status, null)
        then: 'get user time line (without retweets)'
        String statusesJson = client.getUserTimeLine(null, TwitterCred.USER_NAME, null, "10", null, "true", "true", "true", "false")
        then: 'get numerical id of the status'
        String id = JsonParser.getValueFromJsonBy(statusesJson, "id_str")
        then: 'destroy status'
        String destroyJson = client.postDestroyStatuses(id, null);
        then: 'verify that response code is 200'
        assert client.getResponseCode() == 200
        and: 'verify that status was destroyed with correct id'
        assert JsonParser.getValueFromJsonBy(destroyJson, "id_str") == id
        and: 'verify that json contains correct status'
        assert JsonParser.getValueFromJsonBy(destroyJson, "text") == status

        where: 'generate unique status'
        status = "Tweeting" + Math.abs(new Random().nextInt()) % 600 + 1
    }

    @Unroll("add status and verify response code (#code) and that json contains correct status (#status)")
    def 'update status test'() {
        when: 'post request to the twitter api: add new status'
        String updatedStatusJson = client.postStatus(text, null)

        then: 'verify response code'
        client.getResponseCode() == code
        and: 'verify that json contains correct status'
        JsonParser.getValueFromJsonBy(updatedStatusJson, "text") == status

        where:
        code | status
        200  | text
        403  | null
    }
}
