package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class WorkCycleInfoTest {

    @Test
    public void dispOrderGetter() {
        WorkCycleInfo info = new WorkCycleInfo();
        NtsAssert.invokeGetters(info);
    }

}
