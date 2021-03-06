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
import org.springframework.http.HttpStatus;

/**
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public class RetryErroneousIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RetryErroneous runnable;

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/retry-erroneous/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/retry-erroneous/expected/emptyDatabase.yml")
    public void emptyDatabase() {
        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/retry-erroneous/initial/oneUnprocessedItem.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/retry-erroneous/expected/oneUnprocessedItem.yml")
    public void oneUnprocessedItem() {
        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/retry-erroneous/initial/hackernewsException.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/retry-erroneous/expected/hackernewsException.yml")
    public void hackernewsException() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(HttpStatus.INTERNAL_SERVER_ERROR.value())
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/retry-erroneous/initial/oneItem.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/retry-erroneous/expected/oneItem.yml")
    public void oneItem() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(
                    new ResourceOf("wiremock/hackernews/cron/retry-erroneous/1.json")
                )
            )
        );

        this.runnable.run();
    }

    @Test
    @DataSet(
        value = "db-rider/hackernews/cron/retry-erroneous/initial/twoItems.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/hackernews/cron/retry-erroneous/expected/twoItems.yml")
    public void twoItems() {
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/1.json"),
                new Response(
                    new ResourceOf("wiremock/hackernews/cron/retry-erroneous/1.json")
                )
            )
        );
        new WireMockWrapper().stub(
            new Stub(
                new Request("/hackernews/item/2.json"),
                new Response(
                    new ResourceOf("wiremock/hackernews/cron/retry-erroneous/2.json")
                )
            )
        );

        this.runnable.run();
    }
}
