package dev.iakunin.codexiabot.codexia.config;

import dev.iakunin.codexiabot.codexia.cron.UpdateProjects;
import dev.iakunin.codexiabot.common.runnable.Logging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class UpdateProjectsCronConfig implements SchedulingConfigurer {

    private final UpdateProjects updateProjects;

    private final String cronExpression;

    public UpdateProjectsCronConfig(
        UpdateProjects updateProjects,
        @Value("${app.cron.codexia.update-projects:-}") String cronExpression
    ) {
        this.updateProjects = updateProjects;
        this.cronExpression = cronExpression;
    }

    @Bean
    public Runnable updateProjectsRunnable() {
        return new Logging(this.updateProjects);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(
            this.updateProjectsRunnable(),
            this.cronExpression
        );
    }
}