package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
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
    public void testMethodCreateSusses() {
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
        assertThat(instance.getTaskCodeList()).isEqualTo(taskList);
        assertThat(instance.getTaskFrameNo()).isEqualTo(taskNo);
        assertThat(instance.getWorkPlaceId()).isEqualTo(Helper.wpl);
    }

    @Test
    public void testMethodCreateFail() {
        val taskNo = Helper.taskFrameNo;
        val taskList = Helper.childWorkList;
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, taskNo, taskList);
            result = new BusinessException("Msg_2065");
        }};

        NtsAssert.businessException("Msg_2065", () -> NarrowingDownTaskByWorkplace.create(
                require,
                Helper.wpl,
                taskNo,
                taskList));
    }

    @Test
    public void testChangeCodeListSusses() {
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
        NtsAssert.businessException("Msg_2065", () -> instance.changeCodeList(require, Helper.childWorkListChange));
    }

    @Test
    public void testChangeCodeListFail() {
        val taskNo = Helper.taskFrameNo;
        val taskList = Helper.childWorkList;
        val listChange = Helper.childWorkListChange;
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, taskNo, taskList);

            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, taskNo, listChange);
            result = new BusinessException("Msg_2065");

        }};

        val instance = NarrowingDownTaskByWorkplace.create(
                require,
                Helper.wpl,
                taskNo,
                taskList
        );
        NtsAssert.businessException("Msg_2065", () -> instance.changeCodeList(require, Helper.childWorkListChange));
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
