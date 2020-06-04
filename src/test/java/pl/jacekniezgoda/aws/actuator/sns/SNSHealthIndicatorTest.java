package pl.jacekniezgoda.aws.actuator.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.Topic;
import org.mockito.Mock;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class SNSHealthIndicatorTest {
    @Mock
    private AmazonSNS amazonSNS;

    private SNSHealthIndicator sut;


    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);

        sut = new SNSHealthIndicator(amazonSNS);
    }

    @Test
    public void testDoHealthCheck() throws Exception {
        ListTopicsResult listTopicsResult = mock(ListTopicsResult.class);
        Topic topic = mock(Topic.class);

        when(amazonSNS.listTopics()).thenReturn(listTopicsResult);
        when(listTopicsResult.getTopics()).thenReturn(Collections.singletonList(topic));
        when(topic.getTopicArn()).thenReturn("an-arn");

        Health.Builder builder = new Health.Builder();

        sut.doHealthCheck(builder);

        Health result = builder.build();

        assertEquals(result.getStatus(), Status.UP);
        Map<String, Object> details = result.getDetails();
        assertNotNull(details);
        assertEquals(details.size(), 1);
        assertEquals((List)details.get("topics"), Collections.singletonList("an-arn"));
    }
}