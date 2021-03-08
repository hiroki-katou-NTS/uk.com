package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.aggregateroot.taskmaster;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class TaskTest {
    @Injectable
    Task.Require require;

    /**
     * Test Getter
     */
    @Test
    public void testGetter() {
        val taskNo = Helper.taskFrameNo;
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);

        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        NtsAssert.invokeGetters(instance);
    }

    /**
     * Test case : 「@作業枠NO」== 5 の場合、「@子作業一覧」はisEmpty
     */
    @Test
    public void testCaseTaskFrameNo_is05() {
        TaskFrameNo taskFrameNo = new TaskFrameNo(5);
        val list = Helper.childTaskList;
        val instance = Task.create(require, taskFrameNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        assertThat(instance.getChildTaskList()).isEqualTo(Collections.emptyList());
    }

    /**
     * Test case : 「@作業枠NO」== 1
     */
    @Test
    public void testCaseTaskFrameNo_is01() {
        val taskNo = new TaskFrameNo(1);
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);
        ColorCode colorCode = new ColorCode("ColorCode");
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        assertThat(instance.getDisplayInfo().getColor()).isEqualTo(Optional.of(colorCode));
    }

    /**
     * Test case : 「@作業枠NO」!== 1
     */
    @Test
    public void testCaseTaskFrameNo_dif01() {
        val taskNo = new TaskFrameNo(2);
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        assertThat(instance.getDisplayInfo().getColor()).isEqualTo(Optional.empty());
    }

    /**
     * Test checkExpirationDate() : Return true;
     */
    @Test
    public void testCheckExpirationDateIsTrue() {
        val taskNo = new TaskFrameNo(2);
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        GeneralDate date = GeneralDate.fromString("2021/02/01", Helper.DATE_FORMAT);
        val ep = instance.checkExpirationDate(date);
        Assertions.assertThat(ep).isTrue();
    }

    /**
     * Test checkExpirationDate() : Return false;
     */
    @Test
    public void testCheckExpirationDateIsFalse() {
        val taskNo = new TaskFrameNo(2);
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        GeneralDate date = GeneralDate.fromString("2023/02/01", Helper.DATE_FORMAT);
        val ep = instance.checkExpirationDate(date);
        Assertions.assertThat(ep).isFalse();
    }

    /**
     * Test changeChildTaskList() :
     * throw  BusinessException("Msg_2066");
     */
    @Test
    public void testChangeChildTaskLis_withFrameNo5() {

        val taskNo = new TaskFrameNo(5);
        val list = Helper.childTaskList;

        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        NtsAssert.businessException("Msg_2066", () ->
                instance.changeChildTaskList(require, Helper.childTaskList));
    }

    /**
     * Test changeChildTaskList() :
     * +
     */
    @Test
    public void testChangeChildTaskList() {

        val taskNo = new TaskFrameNo(2);
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);
        val tsc = Helper.childTaskListChange;
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, tsc);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);

        instance.changeChildTaskList(require, tsc);
        assertThat(instance.getChildTaskList()).isEqualTo(Helper.childTaskListChange);
    }

    /**
     * Test changeChildTaskList() :
     * +
     */
    @Test
    public void testDeleteChildTaskList() {

        val taskNo = new TaskFrameNo(2);
        val list = Helper.childTaskList;
        val ts = new TaskFrameNo(taskNo.v() + 1);
        new Expectations(CheckExistenceMasterDomainService.class) {{
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, ts, list);

        }};
        val instance = Task.create(require, taskNo, Helper.code, Helper.expirationDate,
                Helper.cooperationInfo, Helper.displayInfo, list);
        instance.deleteChildTaskList();
        assertThat(instance.getChildTaskList()).extracting(d -> d)
                .containsExactlyInAnyOrderElementsOf(Collections.emptyList());
    }

    public static class Helper

    {
        private static final String DATE_FORMAT = "yyyy/MM/dd";
        private static final ColorCode colorCode = new ColorCode("ColorCode");

        private static final TaskCode code = new TaskCode("CODE");
        private static final TaskFrameNo taskFrameNo = new TaskFrameNo(3);
        private static final ExternalCooperationInfo cooperationInfo = new ExternalCooperationInfo(
                Optional.of(new TaskExternalCode("externalCode1")),
                Optional.of(new TaskExternalCode("externalCode2")),
                Optional.of(new TaskExternalCode("externalCode3")),
                Optional.of(new TaskExternalCode("externalCode4")),
                Optional.of(new TaskExternalCode("externalCode5"))
        );

        private static final List<TaskCode> childTaskList = Arrays.asList(
                new TaskCode("CODE01"),
                new TaskCode("CODE02"),
                new TaskCode("CODE03"),
                new TaskCode("CODE04"),
                new TaskCode("CODE05")
        );
        private static final GeneralDate startDate = GeneralDate.fromString("2021/01/01", DATE_FORMAT);
        private static final GeneralDate endDate = GeneralDate.fromString("2021/05/15", DATE_FORMAT);
        private static final DatePeriod expirationDate = new DatePeriod(startDate, endDate);
        private static final TaskDisplayInfo displayInfo = new TaskDisplayInfo(
                new TaskName("TaskName"),
                new TaskAbName("TaskAbName"),
                Optional.of(colorCode),
                Optional.of(new TaskNote("TaskNote"))

        );

        private static final List<TaskCode> childTaskListChange = Arrays.asList(
                new TaskCode("CODE0_CHANGE1"),
                new TaskCode("CODE0_CHANGE2"),
                new TaskCode("CODE0_CHANGE3"),
                new TaskCode("CODE0_CHANGE4"),
                new TaskCode("CODE0_CHANGE5")
        );
    }

}
