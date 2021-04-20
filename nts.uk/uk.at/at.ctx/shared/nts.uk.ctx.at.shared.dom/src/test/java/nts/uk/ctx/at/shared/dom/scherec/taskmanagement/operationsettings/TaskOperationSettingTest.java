package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings;


import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class TaskOperationSettingTest {

    @Test
    public void testGetter() {
        val instance = new TaskOperationSetting(
                TaskOperationMethod.DO_NOT_USE
        );
        NtsAssert.invokeGetters(instance);

    }
}
