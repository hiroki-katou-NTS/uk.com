package nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.worknarrowingdown;


import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.ExternalCooperationInfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskExternalCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class NarrowingDownWorkByWorkplaceTest {
    @Injectable
    NarrowingDownWorkByWorkplace.Require require;
    /**
     * Test Getter
     */
    @Test
    public void testGetter() {
        val instance =  NarrowingDownWorkByWorkplace.create(
                require,
                Helper.wpl,
                Helper.taskFrameNo,
                Helper.childWorkList
        );
        NtsAssert.invokeGetters(instance);
    }

    @Test
    public void testChangeCodeList() {
        val instance =  NarrowingDownWorkByWorkplace.create(
                require,
                Helper.wpl,
                Helper.taskFrameNo,
                Helper.childWorkList
        );
        instance.changeCodeList(require, Helper.childWorkListChange);
        assertThat(instance.getTaskCodeList()).isEqualTo(Helper.childWorkListChange);
    }

    public static class Helper

    {
        private static final String wpl = "wpl";
        private static final TaskFrameNo taskFrameNo = new TaskFrameNo(3);
        private static final List<TaskCode> childWorkList = Arrays.asList(
                new TaskCode("CODE01"),
                new TaskCode("CODE02"),
                new TaskCode("CODE03"),
                new TaskCode("CODE04"),
                new TaskCode("CODE05")
        );
        private static final List<TaskCode> childWorkListChange = Arrays.asList(
                new TaskCode("CODE0_CHANGE1"),
                new TaskCode("CODE0_CHANGE2"),
                new TaskCode("CODE0_CHANGE3"),
                new TaskCode("CODE0_CHANGE4"),
                new TaskCode("CODE0_CHANGE5")
        );

    }
}
