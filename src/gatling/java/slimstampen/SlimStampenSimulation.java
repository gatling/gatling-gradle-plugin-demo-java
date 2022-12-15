package slimstampen;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SlimStampenSimulation extends Simulation {

    private final String baseUrl = "https://app-test.slimstampen.nl/ruggedlearning";
    private final int amountOfResponses = 100;
    private final int amountOfUsers = 100;

    FeederBuilder<String> userFeeder = csv("users.csv").circular();
    FeederBuilder<Object> responseFeeder = jsonFile("responses.json").random();

    ChainBuilder jwks =
            exec(http("JWKS").get("/.well-known/jwks.json").check(status().is(200)));

    ChainBuilder loginAndPractice = feed(userFeeder)
            .exec(http("login")
                    .post("/api/user/login")
                    .body(StringBody("{ \"username\": \"#{email}\", \"password\": \"#{password}\"}"))
                    .check(header("Set-Cookie").exists().saveAs("session"))
            )
            .pause(1)
            .exec(addCookie(Cookie("Cookie", "#{session}")))
            .exec(http("profile_get")
                    .get("/api/profile/get")
                    .check(bodyString().saveAs("body"))
                    .check(status().is(200))
                    .check(jsonPath("$.anonymous").ofBoolean().is(false))
            )
            .pause(2)
            .repeat(amountOfResponses).on(
                    feed(responseFeeder)
                            .pause(2)
                            .exec(http("response_save")
                                    .post("/api/response/save")
                                    .body(StringBody("{\"alternatives\": \"[]\", " +
                                            "\"answerMethod\": \"#{answer_method}\"," +
                                            "\"backSpaceUsed\": \"#{backspace_used}\", " +
                                            "\"backSpacedFirstLetter\": \"#{backspaced_first_letter}\", " +
                                            "\"correct\": \"#{correct}\", " +
                                            "\"currenTime\": \"#{current_time}\", " +
                                            "\"factId\": \"#{fact_id}\", " +
                                            "\"givenResponse\": \"#{given_response}\", " +
                                            "\"lessonId\": \"#{lesson_id}\", " +
                                            "\"mostDifficult\": \"#{most_difficult}\", " +
                                            "\"numberOfChoices\": \"#{number_of_choices}\", " +
                                            "\"presentationDuration\": \"#{presentation_duration}\", " +
                                            "\"presentationStartTime\": \"#{presentation_start_time}\", " +
                                            "\"presentedCueTextIndex\": \"#{presented_cue_text_index}\", " +
                                            "\"reactionTime\": \"#{reaction_time}\", " +
                                            "\"sessionId\": \"#{session_id}\", " +
                                            "\"sessionTime\": \"#{session_time}\"}"))
                                    .check(status().is(200))
                            )
                            .exec(http("mastery_save")
                                    .post("/api/mastery/save")
                                    .body(StringBody("{ \"correct\": \"#{correct}\", " +
                                            "\"factId\": \"#{fact_id}\", " +
                                            "\"lessonId\": \"#{lesson_id}\", " +
                                            "\"mostDifficult\": \"#{most_difficult}\", " +
                                            "\"reactionTime\": \"#{reaction_time}\", " +
                                            "\"sessionId\": \"#{session_id}\", " +
                                            "\"sessionTime\": \"#{session_time}\"}"))
                                    .check(status().is(200))
                            )
            );

    HttpProtocolBuilder httpProtocol =
            http.baseUrl(baseUrl)
                    .acceptHeader("application/json,text/plain,*/*")
                    .acceptLanguageHeader("nl-NL,nl;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6")
                    .acceptEncodingHeader("gzip, deflate, br")
                    .contentTypeHeader("application/json")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
                    );

    ScenarioBuilder json = scenario("Json").exec(jwks);
    ScenarioBuilder loginScenario = scenario("Login").exec(loginAndPractice);

    {
        setUp(
                json.injectOpen(rampUsers(amountOfUsers).during(10)),
                loginScenario.injectOpen(rampUsers(amountOfUsers).during(30))
        ).protocols(httpProtocol);
    }
}
