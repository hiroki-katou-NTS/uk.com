package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class SupportWorkDetailsTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetter() {
        SupportWorkDetails detail = SupportWorkDetails.create(
                require,
                "employee-id-00001",
                GeneralDate.today(),
                1,
                "",
                "",
                Arrays.asList(1, 2, 3),
                null,
                null
        );

        NtsAssert.invokeGetters(detail);
    }

    @Test
    public void testSetSupportWork() {
        SupportWorkDetails detail = SupportWorkDetails.create(
                require,
                "employee-id-00001",
                GeneralDate.today(),
                1,
                "",
                "",
                Arrays.asList(1, 2, 3),
                null,
                null
        );
        detail.setSupportWork(true);

        assertThat(detail.isSupportWork()).isEqualTo(true);
    }
}
