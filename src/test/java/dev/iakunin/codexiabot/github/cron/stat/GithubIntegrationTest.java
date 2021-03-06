package dev.iakunin.codexiabot.github.cron.stat;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import dev.iakunin.codexiabot.AbstractIntegrationTest;
import dev.iakunin.codexiabot.config.GithubConfig;
import dev.iakunin.codexiabot.util.WireMockWrapper;
import dev.iakunin.codexiabot.util.wiremock.Response;
import dev.iakunin.codexiabot.util.wiremock.Stub;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

/**
 * @checkstyle MultipleStringLiterals (500 lines)
 */
@SpringBootTest(classes = {
    AbstractIntegrationTest.TestConfig.class,
    GithubConfig.class
})
public class GithubIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private Github github;

    @Test
    @DataSet(
        value = "db-rider/github/cron/stat/github/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/stat/github/expected/emptyDatabase.yml")
    public void emptyDatabase() {
        this.github.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/stat/github/initial/notCodexiaSource.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/stat/github/expected/notCodexiaSource.yml")
    public void notCodexiaSource() {
        this.github.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/stat/github/initial/happyPath.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/stat/github/expected/happyPath.yml")
    public void happyPath() {
        new WireMockWrapper().stub(
            new Stub(
                "/github/repos/instaloader/instaloader",
                new ResourceOf("wiremock/github/cron/stat/github/instaloader.json")
            )
        );

        this.github.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/stat/github/initial/githubIoException.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/stat/github/expected/githubIoException.yml")
    public void githubIoException() {
        new WireMockWrapper().stub(
            new Stub(
                "/github/repos/instaloader/instaloader",
                new Response(HttpStatus.INTERNAL_SERVER_ERROR.value())
            )
        );

        this.github.run();
    }
}
