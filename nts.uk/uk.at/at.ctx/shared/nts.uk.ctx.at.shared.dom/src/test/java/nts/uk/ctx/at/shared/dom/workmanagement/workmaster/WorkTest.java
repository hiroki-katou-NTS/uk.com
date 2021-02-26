package nts.uk.ctx.at.shared.dom.workmanagement.workmaster;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.ctx.at.shared.dom.workmanagement.workframe.WorkFrameUsageSetting;
import nts.uk.shr.com.color.ColorCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkTest {
    @Injectable
    Work.Require require;

    /**
     * Test Getter
     */
    @Test
    public void testGetter() {
        val instance = Helper.createDomain(require);
        NtsAssert.invokeGetters(instance);
    }

    /**
     * Test case : 「@作業枠NO」== 5 の場合、「@子作業一覧」はisEmpty
     */
    @Test
    public void testCaseTaskFrameNo_is05() {
        val instance = Helper.createDomainTaskFrameNo05(require);
        assertThat(instance.getChildWorkList()).isEqualTo(Collections.emptyList());
    }

    /**
     * Test case:
     * Require.checkExistenceWorkMaster return False
     */

    @Test
    public void testCheckExistenceWorkMaster_isFalse() {


        new Expectations() {{
            require.checkExistenceWorkMaster(new TaskFrameNo(Helper.taskFrameNo.v() + 1), Helper.childWorkList);
            result = false;
        }};
        val instance = Helper.createDomain(require);
        assertThat(instance.getChildWorkList()).isEqualTo(null);
    }

    /**
     * Test case:
     * Require.checkExistenceWorkMaster return true
     */
    @Test
    public void testCheckExistenceWorkMaster_isTrue() {


        new Expectations() {{
            require.checkExistenceWorkMaster(new TaskFrameNo(Helper.taskFrameNo.v() + 1), Helper.childWorkList);
            result = true;
        }};
        val instance = Helper.createDomain(require);
        assertThat(instance.getChildWorkList()).extracting(d -> d)
                .containsExactlyInAnyOrderElementsOf(Helper.childWorkList);

    }

    /**
     * Test case : 「@作業枠NO」== 1
     */
    @Test
    public void testCaseTaskFrameNo_is01() {
        val instance = Helper.createDomainTaskFrameNo01(require);
        assertThat(instance.getDisplayInfo()).isEqualTo(null);
    }

    /**
     * Test checkExpirationDate() : Return true;
     */
    @Test
    public void testCheckExpirationDateIsTrue() {
        val instance = Helper.createDomainTaskFrameNo01(require);
         GeneralDate date = GeneralDate.fromString("2021/02/01", Helper.DATE_FORMAT);
         val ep = instance.checkExpirationDate(date);
        assertThat(ep).isTrue();
    }

    /**
     * Test checkExpirationDate() : Return false;
     */
    @Test
    public void testCheckExpirationDateIsFalse() {
        val instance = Helper.createDomainTaskFrameNo01(require);
         GeneralDate date = GeneralDate.fromString("2023/02/01", Helper.DATE_FORMAT);
         val ep = instance.checkExpirationDate(date);
        assertThat(ep).isFalse();
    }

    /**
     * Test changeChildWorkList() :
     *  throw  BusinessException("Msg_2066");
     */
    @Test
    public void testChangeChildWorkList() {
        val instance = Helper.createDomainTaskFrameNo01(require);
        NtsAssert.businessException("Msg_2066", () ->
                instance.changeChildWorkList(require,Helper.childWorkList));
    }

    public static class Helper

    {
        private static final String DATE_FORMAT = "yyyy/MM/dd";
        private static final TaskCode code = new TaskCode("CODE");
        private static final TaskFrameNo taskFrameNo = new TaskFrameNo(3);
        private static final ExternalCooperationInfo cooperationInfo = new ExternalCooperationInfo(
                Optional.of(new TaskExternalCode("externalCode1")),
                Optional.of(new TaskExternalCode("externalCode2")),
                Optional.of(new TaskExternalCode("externalCode3")),
                Optional.of(new TaskExternalCode("externalCode4")),
                Optional.of(new TaskExternalCode("externalCode5"))
        );

        private static final List<TaskCode> childWorkList = Arrays.asList(
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
                Optional.of(new ColorCode("ColorCode")),
                Optional.of(new TaskNote("TaskNote"))

        );

        static Work createDomain(Work.Require require) {

            return new Work(require, taskFrameNo, code, expirationDate, cooperationInfo, displayInfo, childWorkList);
        }

        static Work createDomainTaskFrameNo05(Work.Require require) {
            TaskFrameNo taskFrameNo = new TaskFrameNo(5);
            return new Work(require, taskFrameNo, code, expirationDate, cooperationInfo, displayInfo, childWorkList);
        }

        static Work createDomainTaskFrameNo01(Work.Require require) {
            TaskFrameNo taskFrameNo = new TaskFrameNo(5);
            return new Work(require, taskFrameNo, code, expirationDate, cooperationInfo, displayInfo, childWorkList);
        }

    }

}
