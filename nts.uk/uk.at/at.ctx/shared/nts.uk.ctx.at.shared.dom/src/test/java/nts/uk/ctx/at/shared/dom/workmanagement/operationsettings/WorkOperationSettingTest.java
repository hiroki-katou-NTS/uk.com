package nts.uk.ctx.at.shared.dom.workmanagement.operationsettings;


import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class WorkOperationSettingTest {

    @Test
    public void testGetter() {
        val instance = new WorkOperationSetting(
                WorkOperationMethod.DO_NOT_USE
        );
        NtsAssert.invokeGetters(instance);

    }
}
