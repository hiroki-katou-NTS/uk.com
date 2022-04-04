package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.SupportWorkDetailsHelper;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(JMockit.class)
public class SupportDetailTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        SupportDetail detail = SupportDetail.create(
                require,
                "000000000003-0001",
                "0001",
                Arrays.asList(SupportWorkDetailsHelper.createDetailData("", GeneralDate.today(), Arrays.asList(930)))
        );

        NtsAssert.invokeGetters(detail);
    }
}
