package pl.jacekniezgoda.aws.actuator.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.stream.Collectors;

public class S3HealthIndicator extends AbstractHealthIndicator {
    private final AmazonS3 amazonS3;

    public S3HealthIndicator(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("region", amazonS3.getRegionName())
                .withDetail("buckets", amazonS3.listBuckets()
                                .stream()
                                .map(Bucket::getName)
                                .collect(Collectors.toList())
                );
    }
}
