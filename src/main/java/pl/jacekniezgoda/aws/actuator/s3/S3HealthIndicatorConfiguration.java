package pl.jacekniezgoda.aws.actuator.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.boot.actuate.autoconfigure.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.autoconfigure.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnEnabledHealthIndicator("s3")
public class S3HealthIndicatorConfiguration extends CompositeHealthIndicatorConfiguration<S3HealthIndicator, AmazonS3> {
    private final AmazonS3 amazonS3;

    public S3HealthIndicatorConfiguration(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Bean
    @ConditionalOnMissingBean(name = "s3HealthIndicator")
    public HealthIndicator s3HealthIndicator() {
        return createHealthIndicator(amazonS3);
    }
}
