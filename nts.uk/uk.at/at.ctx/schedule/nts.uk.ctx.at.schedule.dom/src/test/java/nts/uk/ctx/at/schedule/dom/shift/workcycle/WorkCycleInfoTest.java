package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class WorkCycleInfoTest {

    @Injectable
    private WorkInformation.Require require;

    @Test
    public void getWorkCycleInfoTest() {
        WorkCycleInfo info = WorkCycleInfo.WorkCycleInfo(1, new WorkInformation("WTimeC1", "WType1"));
        NtsAssert.invokeGetters(info);
    }

    @Test
    public void getWorkCycleInfoDay_1Test() {
        NtsAssert.businessException("Msg_1689",() -> {
            WorkCycleInfo info = WorkCycleInfo.WorkCycleInfo(0, new WorkInformation("WTimeC1", "WType1"));
            });
    }

    @Test
    public void getWorkCycleInfoDay_2Test() {
        NtsAssert.businessException("Msg_1689",() -> {
            WorkCycleInfo info = WorkCycleInfo.WorkCycleInfo(100, new WorkInformation("WTimeC1", "WType1"));
        });
    }

}
