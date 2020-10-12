package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkCycleInfoTest {

    @Injectable
    private WorkInformation.Require require;

    @Test
    public void getWorkCycleInfoTest() {
        WorkCycleInfo info = WorkCycleInfo.create(1, new WorkInformation("WType1", "WTimeC1"));
        NtsAssert.invokeGetters(info);
    }

    @Test
    public void getWorkCycleInfoDay_1Test() {
        NtsAssert.businessException("Msg_1689",() -> {
            WorkCycleInfo info = WorkCycleInfo.create(0, new WorkInformation("WType1", "WTimeC1"));
            });
    }

    @Test
    public void getWorkCycleInfoDay_2Test() {
        NtsAssert.businessException("Msg_1689",() -> {
            WorkCycleInfo info = WorkCycleInfo.create(100, new WorkInformation("WType1", "WTimeC1"));
        });
    }

    @Test
    public void testCheckError() {
        WorkCycleInfo workCycleInfo =  WorkCycleInfo.create(2, new WorkInformation("WType001", "WTime001"));

        new Expectations(WorkInformation.class) {
            {
                workCycleInfo.getWorkInformation().checkErrorCondition(require);
                result = ErrorStatusWorkInfo.NORMAL;
            }
        };

        ErrorStatusWorkInfo result =  workCycleInfo.checkError(require);
        assertThat(result).isEqualByComparingTo(ErrorStatusWorkInfo.NORMAL);

    }

}
