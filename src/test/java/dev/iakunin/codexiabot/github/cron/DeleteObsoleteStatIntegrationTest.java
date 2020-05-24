package dev.iakunin.codexiabot.github.cron;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import dev.iakunin.codexiabot.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DeleteObsoleteStatIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private DeleteObsoleteStat stat;

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/emptyDatabase.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/emptyDatabase.yml")
    public void emptyDatabase() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/oneStat.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/oneStat.yml")
    public void oneStat() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/twoStats.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/twoStats.yml")
    public void twoStats() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/threeStats.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/threeStats.yml")
    public void threeStats() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/threeStatsWithForeignKey.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/threeStatsWithForeignKey.yml")
    public void threeStatsWithForeignKey() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/twoStatsOfTwoTypes.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/twoStatsOfTwoTypes.yml")
    public void twoStatsOfTwoTypes() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/fourStatsOfTwoTypes.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/fourStatsOfTwoTypes.yml")
    public void fourStatsOfTwoTypes() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/sixStatsOfTwoTypes.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/sixStatsOfTwoTypes.yml")
    public void sixStatsOfTwoTypes() {
        stat.run();
    }

    @Test
    @DataSet(
        value = "db-rider/github/cron/delete-obsolete-stat/initial/sixStatsOfTwoTypesWithForeignKeys.yml",
        cleanBefore = true, cleanAfter = true
    )
    @ExpectedDataSet("db-rider/github/cron/delete-obsolete-stat/expected/sixStatsOfTwoTypesWithForeignKeys.yml")
    public void sixStatsOfTwoTypesWithForeignKeys() {
        stat.run();
    }
}
