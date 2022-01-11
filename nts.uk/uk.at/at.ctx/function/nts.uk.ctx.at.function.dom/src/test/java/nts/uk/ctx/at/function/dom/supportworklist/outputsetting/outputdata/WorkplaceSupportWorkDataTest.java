package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.WorkplaceTotalDisplaySetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkplaceSupportWorkDataTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(SupportWorkDetails.create(
                        require,
                        "000000000003-0001",
                        GeneralDate.today(),
                        1,
                        "workplace-id-0001",
                        "0001",
                        Arrays.asList(1, 2, 3),
                        null,
                        null
                )),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE
                )
        );

        NtsAssert.invokeGetters(data);
    }

    @Test
    public void testNotDisplayTotalOneDay() {
        SupportWorkDetails supportWorkDetail = SupportWorkDetails.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                1,
                "workplace-id-0001",
                "0001",
                Arrays.asList(1, 2, 3),
                null,
                null
        );
        supportWorkDetail.setSupportWork(true);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE
                )
        );

        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(true);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(true);
        assertThat(data.getTotalWorkplace().isPresent()).isEqualTo(true);
        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().isPresent()).isEqualTo(false);
    }

    @Test
    public void testNotDisplaySupportDetail() {
        SupportWorkDetails supportWorkDetail = SupportWorkDetails.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                1,
                "workplace-id-0001",
                "0001",
                Arrays.asList(1, 2, 3),
                null,
                null
        );
        supportWorkDetail.setSupportWork(true);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE
                )
        );

        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(true);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(true);
        assertThat(data.getTotalWorkplace().isPresent()).isEqualTo(true);
        assertThat(data.getSupportDetails().size()).isEqualTo(0);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().isPresent()).isEqualTo(true);
    }

    @Test
    public void testNotDisplaySupportWorkplace() {
        SupportWorkDetails supportWorkDetail = SupportWorkDetails.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                1,
                "workplace-id-0001",
                "0001",
                Arrays.asList(1, 2, 3),
                null,
                null
        );
        supportWorkDetail.setSupportWork(true);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE
                )
        );

        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(false);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(false);
        assertThat(data.getTotalWorkplace().isPresent()).isEqualTo(true);
        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().isPresent()).isEqualTo(true);
    }

    @Test
    public void testNotDisplayWorkplaceTotal() {
        SupportWorkDetails supportWorkDetail = SupportWorkDetails.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                1,
                "workplace-id-0001",
                "0001",
                Arrays.asList(1, 2, 3),
                null,
                null
        );
        supportWorkDetail.setSupportWork(true);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE
                )
        );

        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(true);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(true);
        assertThat(data.getTotalWorkplace().isPresent()).isEqualTo(false);
        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().isPresent()).isEqualTo(true);
    }
}
