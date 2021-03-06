package dev.iakunin.codexiabot.hackernews.cron;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import dev.iakunin.codexiabot.AbstractIntegrationTest;
import dev.iakunin.codexiabot.util.WireMockWrapper;
import dev.iakunin.codexiabot.util.wiremock.Request;
import dev.iakunin.codexiabot.util.wiremock.Response;
import dev.iakunin.codexiabot.util.wiremock.Stub;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @checkstyle MultipleStringLiterals (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class IncrementedParserIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private IncrementedParser runnable;

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/incremented-parser/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/incremented-parser/expected/emptyDatabase.yml")
    public void emptyDatabaseWithoutHackernews() {
        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/incremented-parser/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet(
        "db-rider/hackernews/cron/incremented-parser/expected/emptyDatabaseItemWithoutUrl.yml"
    )
    public void emptyDatabaseItemWithoutUrl() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(
                    new ResourceOf(
                        "wiremock/hackernews/cron/incremented-parser/itemWithoutUrl.json"
                    )
                )
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/incremented-parser/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet(
        "db-rider/hackernews/cron/incremented-parser/expected/emptyDatabaseItemWithGithubUrl.yml"
    )
    public void emptyDatabaseItemWithGithubUrl() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(
                    new ResourceOf(
                        "wiremock/hackernews/cron/incremented-parser/itemWithGithubUrl.json"
                    )
                )
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/incremented-parser/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet(
        "db-rider/hackernews/cron/incremented-parser/expected/emptyDatabaseItemWithGistUrl.yml"
    )
    public void emptyDatabaseItemWithGistUrl() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(
                    new ResourceOf(
                        "wiremock/hackernews/cron/incremented-parser/itemWithGistUrl.json"
                    )
                )
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/incremented-parser/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/incremented-parser/expected/emptyDatabase.yml")
    public void emptyDatabaseEmptyResponse() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response("")
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/incremented-parser/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet(
        "db-rider/hackernews/cron/incremented-parser"
        + "/expected/emptyDatabaseAndTwoItemsAtHackernews.yml"
    )
    public void emptyDatabaseAndTwoItemsAtHackernews() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(
                    new ResourceOf(
                        "wiremock/hackernews/cron/incremented-parser/itemWithoutUrl.json"
                    )
                )
            )
        );
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/2.json"),
                new Response(
                    new ResourceOf("wiremock/hackernews/cron/incremented-parser/itemWithUrl.json")
                )
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(value =
        "db-rider/hackernews/cron/incremented-parser/initial/notEmptyDatabaseWithoutHackernews.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet(
        "db-rider/hackernews/cron/incremented-parser/expected/notEmptyDatabaseWithoutHackernews.yml"
    )
    public void notEmptyDatabaseWithoutHackernews() {
        this.runnable.run();
    }

    @Test
    @DataSet(value =
        "db-rider/hackernews/cron/incremented-parser/initial/notEmptyDatabaseExistsInRepo.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet(
        "db-rider/hackernews/cron/incremented-parser/expected/notEmptyDatabaseExistsInRepo.yml"
    )
    public void notEmptyDatabaseExistsInRepo() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/2.json"),
                new Response(
                    new ResourceOf(
                        "wiremock/hackernews/cron/incremented-parser/itemWithoutUrl.json"
                    )
                )
            )
        );

        this.runnable.run();
    }
}
