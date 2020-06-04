package pl.jacekniezgoda.aws.actuator.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.mockito.Mock;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

public class S3HealthIndicatorTest {
    @Mock
    private AmazonS3 amazonS3;

    private S3HealthIndicator sut;

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);

        sut = new S3HealthIndicator(amazonS3);
    }

    @Test
    public void testDoHealthCheck() throws Exception {
        when(amazonS3.getRegionName()).thenReturn("a-region");
        when(amazonS3.listBuckets()).thenReturn(Collections.singletonList(new Bucket("a-bucket")));

        Health.Builder builder = new Health.Builder();

        sut.doHealthCheck(builder);

        Health result = builder.build();

        assertEquals(result.getStatus(), Status.UP);
        Map<String, Object> details = result.getDetails();
        assertNotNull(details);
        assertEquals(details.size(), 2);
        assertEquals(details.get("region"), "a-region");
        assertEquals((List)details.get("buckets"), Collections.singletonList("a-bucket"));
    }
}