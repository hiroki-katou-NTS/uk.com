package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.EmployeeExtractCondition;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.GrandTotalDisplaySetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.WorkplaceTotalDisplaySetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class SupportWorkOutputDataTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        SupportWorkOutputData data = new SupportWorkOutputData(
                require,
                "000000000003-0001",
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
                ),
                new GrandTotalDisplaySetting(
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        NtsAssert.invokeGetters(data);
    }

    @Test
    public void testNotDisplayGrandTotal() {
        SupportWorkOutputData data = new SupportWorkOutputData(
                require,
                "000000000003-0001",
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
                ),
                new GrandTotalDisplaySetting(
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getGrandTotal().isPresent()).isEqualTo(false);
        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(true);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(true);
    }

    @Test
    public void testNotDisplayTotalSupportWorkplace() {
        SupportWorkOutputData data = new SupportWorkOutputData(
                require,
                "000000000003-0001",
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
                ),
                new GrandTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getGrandTotal().isPresent()).isEqualTo(true);
        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(false);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(false);
    }
}
