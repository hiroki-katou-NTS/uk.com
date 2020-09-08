package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.ReflectionImage;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class ReflectionImageTest {

    @Test
    public void getters() {

        ReflectionImage target = ReflectionImage.create();
        NtsAssert.invokeGetters(target);
    }


    @Test
    public void addByWeeklyWorkingTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = ReflectionImage.create();
        target.addByWeeklyWorking(GeneralDate.today(), workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(GeneralDate.today()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.WEEKLY_WORK).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(workInformation).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void addByWeeklyWorkingTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTimeCode", "workCcle-workTypeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTimeCode", "weekly-workTypeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workCycleWorkInfor);

        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(GeneralDate.today()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.WEEKLY_WORK).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(workInformation).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void addByWeeklyWorkingTest_3() {
        WorkInformation workHolidayWorkInfor = new WorkInformation("workCcle-workTimeCode", "workCcle-workTypeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTimeCode", "weekly-workTypeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.today(), workHolidayWorkInfor);

        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(GeneralDate.today()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.PUB_HOLIDAY).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(workHolidayWorkInfor).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.today(), workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(GeneralDate.today()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.PUB_HOLIDAY).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(workInformation).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTimeCode", "workCcle-workTypeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTimeCode", "weekly-workTypeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workCycleWorkInfor);

        target.addHolidays(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(GeneralDate.today()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.PUB_HOLIDAY).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(workInformation).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest_3() {
        WorkInformation workHolidayWorkInfor = new WorkInformation("holiday-workTimeCode", "holiday-workTypeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTimeCode", "weekly-workTypeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);
        
        target.addHolidays(GeneralDate.today(), workHolidayWorkInfor);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(GeneralDate.today()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.WEEKLY_WORK).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(workInformation).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void addInWorkCycleTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workInformation);
        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isFalse();
    }

    @Test
    public void addInWorkCycleTest_1() {
        WorkInformation holidayWorkInfo = new WorkInformation("workTimeCode1", "workTypeCode1");
        WorkInformation workInformation = new WorkInformation("workTimeCode2", "workTypeCode2");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.max(),holidayWorkInfo);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isTrue();
        assertThat(GeneralDate.max()).isEqualByComparingTo(result.getDate());
        assertThat(WorkCreateMethod.WORK_CYCLE).isEqualByComparingTo(result.getWorkCreateMethod());
        assertThat(holidayWorkInfo).isEqualTo(result.getWorkInformation());
    }

    @Test
    public void getListRefOrdByDateTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode","workTypeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.ymd(2020,9,5),workInformation);
        target.addHolidays(GeneralDate.ymd(2020,9,2),workInformation);
        target.addHolidays(GeneralDate.ymd(2020,9,1),workInformation);
        target.addHolidays(GeneralDate.ymd(2020,9,3),workInformation);

        val result = target.getListRefOrdByDate();

        assertThat(result.get(0).getDate()).isEqualByComparingTo(GeneralDate.ymd(2020,9,1));
        assertThat(result.get(1).getDate()).isEqualByComparingTo(GeneralDate.ymd(2020,9,2));
        assertThat(result.get(2).getDate()).isEqualByComparingTo(GeneralDate.ymd(2020,9,3));
        assertThat(result.get(3).getDate()).isEqualByComparingTo(GeneralDate.ymd(2020,9,5));
    }


}
