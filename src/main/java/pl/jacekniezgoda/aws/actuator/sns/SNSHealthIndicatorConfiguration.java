package pl.jacekniezgoda.aws.actuator.sns;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.boot.actuate.autoconfigure.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.autoconfigure.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnEnabledHealthIndicator("sns")
public class SNSHealthIndicatorConfiguration extends CompositeHealthIndicatorConfiguration<SNSHealthIndicator, AmazonSNS> {
    private final AmazonSNS amazonSNS;

    public SNSHealthIndicatorConfiguration(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    @Bean
    @ConditionalOnMissingBean(name = "snsHealthIndicator")
    public HealthIndicator snsHealthIndicator() {
        return createHealthIndicator(amazonSNS);
    }
}
