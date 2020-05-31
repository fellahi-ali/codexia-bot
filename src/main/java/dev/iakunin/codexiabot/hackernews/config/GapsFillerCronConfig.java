package dev.iakunin.codexiabot.hackernews.config;

import dev.iakunin.codexiabot.common.runnable.Logging;
import dev.iakunin.codexiabot.hackernews.cron.GapsFiller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @checkstyle DesignForExtension (500 lines)
 */
@Configuration
public class GapsFillerCronConfig implements SchedulingConfigurer {

    private final GapsFiller gapsFiller;

    private final String expression;

    public GapsFillerCronConfig(
        final GapsFiller gapsFiller,
        @Value("${app.cron.hackernews.gaps-filler:-}") final String expression
    ) {
        this.gapsFiller = gapsFiller;
        this.expression = expression;
    }

    @Bean
    public Runnable gapsFillerRunnable() {
        return new Logging(this.gapsFiller);
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar registrar) {
        registrar.addCronTask(
            this.gapsFillerRunnable(),
            this.expression
        );
    }
}
