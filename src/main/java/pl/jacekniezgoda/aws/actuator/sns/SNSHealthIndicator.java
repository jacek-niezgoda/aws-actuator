package pl.jacekniezgoda.aws.actuator.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.stream.Collectors;

public class SNSHealthIndicator extends AbstractHealthIndicator {
    private final AmazonSNS amazonSNS;

    public SNSHealthIndicator(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("topics", amazonSNS.listTopics()
                                .getTopics()
                                .stream()
                                .map(Topic::getTopicArn)
                                .collect(Collectors.toList())
                );
    }
}
