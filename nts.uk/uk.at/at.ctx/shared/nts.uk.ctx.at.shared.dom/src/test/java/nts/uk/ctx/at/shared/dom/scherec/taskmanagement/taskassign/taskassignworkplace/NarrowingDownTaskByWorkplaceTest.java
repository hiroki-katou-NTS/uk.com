package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class NarrowingDownTaskByWorkplaceTest {
    @Injectable
    NarrowingDownTaskByWorkplace.Require require;

    /**
     * Test Getter
     */
    @Test
    public void testGetter() {
        val taskNo = Helper.taskFrameNo;
        val taskList = Helper.childWorkList;
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, taskNo, taskList);
        }};
        val instance = NarrowingDownTaskByWorkplace.create(
                require,
                Helper.wpl,
                taskNo,
                taskList
        );
        NtsAssert.invokeGetters(instance);
    }

    @Test
    public void testChangeCodeList() {
        val taskNo = Helper.taskFrameNo;
        val taskList = Helper.childWorkList;
        val listChange = Helper.childWorkListChange;
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, taskNo, taskList);

            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, taskNo, listChange);

        }};

        val instance = NarrowingDownTaskByWorkplace.create(
                require,
                Helper.wpl,
                taskNo,
                taskList
        );
        instance.changeCodeList(require, Helper.childWorkListChange);
        assertThat(instance.getTaskCodeList()).isEqualTo(listChange);
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
