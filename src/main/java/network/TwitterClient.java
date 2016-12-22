package network;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KseniaB on 12/21/2016.
 */
public class TwitterClient extends BaseClient {

    private final static String BASE_URL = "https://api.twitter.com/1.1/";

    public TwitterClient(String key, String secret, String token, String tokenSecret) {
        super(key, secret, token, tokenSecret);
    }

    /**
     * Returns a collection of the most recent Tweets and retweets posted by the authenticating user and the users they follow.
     *
     * @param count           @optional - Must be less than or equal to 200. Example 5
     * @param sinceId         @optional - Returns results with an ID greater than (that is, more recent than) the specified ID. Example 12345
     * @param maxId           @optional - Returns results with an ID less than (that is, older than) or equal to the specified ID. Example 54321
     * @param trimUser        @optional - When set to either true , t or 1 , each Tweet returned in a timeline will include a user object
     *                        including only the status authors numerical ID. Example true
     * @param excludeReplies  @optional - This parameter will prevent replies from appearing in the returned timeline. Example true
     * @param includeEntities @optional - The entities node will not be included when set to false. Example false
     * @return json or null (in case wrong request)
     */
    public String getHomeTimeLine(@Nullable String count, @Nullable String sinceId, @Nullable String maxId,
                                  @Nullable String trimUser, @Nullable String excludeReplies, @Nullable String includeEntities)
            throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        String url = BASE_URL + "statuses/home_timeline.json";
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put("count", count);
        parametersMap.put("since_id", sinceId);
        parametersMap.put("max_id", maxId);
        parametersMap.put("trim_user", trimUser);
        parametersMap.put("exclude_replies", excludeReplies);
        parametersMap.put("include_entities", includeEntities);
        get(url + getParameters(parametersMap));
        return getResponse();
    }

    /**
     * Returns a collection of the most recent Tweets posted by the user.
     *
     * @param userId             @optional - The ID of the user for whom to return results for.
     * @param userName           @optional - The screen name of the user for whom to return results for.
     * @param sinceId            @optional - Returns results with an ID greater than the specified ID.
     * @param count              @optional - Specifies the number of Tweets to return.
     * @param maxId              @optional -Returns results with an ID less than specified.
     * @param trimUser           @optional - Tweet returned in a timeline will include a user object including
     *                           only the status authors numerical ID (in case of true).
     * @param excludeReplies     @optional - This parameter will prevent replies from appearing in the returned timeline.
     * @param contributorDetails @optional - This parameter enhances the contributors element of the status
     *                           response to include the screen_name of the contributor.
     * @param includeRts         @optional - When set to false , the timeline will strip any native retweets.
     * @return json or null (in case wrong request)
     */
    public String getUserTimeLine(@Nullable String userId, @Nullable String userName, @Nullable String sinceId,
                                  @Nullable String count, @Nullable String maxId, @Nullable String trimUser,
                                  @Nullable String excludeReplies, @Nullable String contributorDetails, @Nullable String includeRts)
            throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        String url = BASE_URL + "statuses/user_timeline.json";
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put("screen_id", userId);
        parametersMap.put("screen_name", userName);
        parametersMap.put("since_id", sinceId);
        parametersMap.put("count", count);
        parametersMap.put("max_id", maxId);
        parametersMap.put("trim_user", trimUser);
        parametersMap.put("exclude_replies", excludeReplies);
        parametersMap.put("contributor_details", contributorDetails);
        parametersMap.put("include_rts", includeRts);
        get(url + getParameters(parametersMap));
        return getResponse();
    }

    /**
     * Destroys the status specified by the required ID parameter.
     *
     * @param id       @optional - The numerical ID of the desired status.
     * @param trimUser @optional - When set to either true , t or 1 , each tweet returned in a
     *                 timeline will include a user object including only the status authors numerical ID.
     * @return json or null (in case wrong request)
     */
    public String postDestroyStatuses(@NotNull String id, @Nullable String trimUser)
            throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        String url = BASE_URL + "statuses/destroy/" + id + ".json";
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put("trim_user", trimUser);
        post(url + getParameters(parametersMap));
        return getResponse();
    }

    /**
     * Updates the authenticating user’s current status, also known as Tweeting.
     *
     * @param status                  @required - The text of your status update.
     * @param updateInReplyToStatusId @optional - The ID of an existing status that the update is in reply to.
     * @return json or null (in case wrong request)
     */
    public String postStatus(@NotNull String status, @Nullable String updateInReplyToStatusId)
            throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        String url = BASE_URL + "statuses/update.json";
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put("status", status.replaceAll(" ", "%20").replaceAll(",", "%27"));
        parametersMap.put("in_reply_to_status_id", updateInReplyToStatusId);
        post(url + getParameters(parametersMap));
        return getResponse();
    }

    private String getParameters(Map<String, String> parametersMap) {
        String parameters = "?";
        for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
            if (entry.getValue() != null) {
                parameters += String.format("%s=%s&", entry.getKey(), entry.getValue());
            }
        }
        return parameters;
    }
}
