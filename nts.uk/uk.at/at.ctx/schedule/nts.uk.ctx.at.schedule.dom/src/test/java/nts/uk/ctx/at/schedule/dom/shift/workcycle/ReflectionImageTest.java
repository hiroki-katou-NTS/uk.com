package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.ReflectionImage;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
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
        WorkInformation workInformation = new WorkInformation( "workTypeCode","workTimeCode");
        ReflectionImage target = ReflectionImage.create();
        target.addByWeeklyWorking(GeneralDate.today(), workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(result.getDate()).isEqualTo(GeneralDate.today());
        assertThat(result.getWorkCreateMethod()).isEqualTo(WorkCreateMethod.WEEKLY_WORK);
        assertThat(result.getWorkInformation()).isEqualTo(workInformation);
    }

    @Test
    public void addByWeeklyWorkingTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTypeCode", "workCcle-workTimeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTypeCode", "weekly-workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workCycleWorkInfor);

        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(result.getDate()).isEqualTo(GeneralDate.today());
        assertThat(result.getWorkCreateMethod()).isEqualTo(WorkCreateMethod.WEEKLY_WORK);
        assertThat(result.getWorkInformation()).isEqualTo(workInformation);
    }

    @Test
    public void addByWeeklyWorkingTest_3() {
        WorkInformation workHolidayWorkInfor = new WorkInformation("workholiday-workTypeCode", "workholiday-workTimeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTypeCode","weekly-workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.today(), workHolidayWorkInfor);

        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(result.getDate()).isEqualTo(GeneralDate.today());
        assertThat(result.getWorkCreateMethod()).isEqualTo(WorkCreateMethod.PUB_HOLIDAY);
        assertThat(result.getWorkInformation()).isEqualTo(workHolidayWorkInfor);
    }

    @Test
    public void addHolidaysTest_1() {
        WorkInformation workInformation = new WorkInformation("workTypeCode","workTimeCode");
        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.today(), workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(result.getDate()).isEqualTo(GeneralDate.today());
        assertThat(result.getWorkCreateMethod()).isEqualTo(WorkCreateMethod.PUB_HOLIDAY);
        assertThat(result.getWorkInformation()).isEqualTo(workInformation);
    }

    @Test
    public void addHolidaysTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTypeCode", "workCcle-workTimeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTypeCode", "weekly-workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workCycleWorkInfor);

        target.addHolidays(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(result.getDate()).isEqualTo(GeneralDate.today());
        assertThat(result.getWorkCreateMethod()).isEqualTo(WorkCreateMethod.PUB_HOLIDAY);
        assertThat(result.getWorkInformation()).isEqualTo(workInformation);
    }

    @Test
    public void addHolidaysTest_3() {
        WorkInformation workHolidayWorkInfor = new WorkInformation("holiday-workTypeCode", "holiday-workTimeCode");
        WorkInformation workInformation = new WorkInformation("weekly-workTypeCode", "weekly-workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);
        
        target.addHolidays(GeneralDate.today(), workHolidayWorkInfor);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(result.getDate()).isEqualTo(GeneralDate.today());
        assertThat(result.getWorkCreateMethod()).isEqualTo(WorkCreateMethod.WEEKLY_WORK);
        assertThat(result.getWorkInformation()).isEqualTo(workInformation);
    }

    @Test
    public void addInWorkCycleTest() {
        WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");
        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workInformation);
        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isFalse();
    }

    @Test
    public void addInWorkCycleTest_1() {
        WorkInformation wrkCycleWorkInfo = new WorkInformation("workTypeCode1", "workTimeCode1");

        ReflectionImage target = ReflectionImage.create();
        val result = target.addInWorkCycle(GeneralDate.max(),wrkCycleWorkInfo);

        assertThat(result).isEqualTo(true);
        assertThat(target.getListRefOrdByDate().get(0).getDate()).isEqualTo(GeneralDate.max());
        assertThat(target.getListRefOrdByDate().get(0).getWorkCreateMethod()).isEqualTo(WorkCreateMethod.WORK_CYCLE);
        assertThat(target.getListRefOrdByDate().get(0).getWorkInformation()).isEqualTo(wrkCycleWorkInfo);
    }

    @Test
    public void getListRefOrdByDateTest() {
        WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.ymd(2020,9,5),workInformation);
        target.addHolidays(GeneralDate.ymd(2020,9,2),workInformation);
        target.addHolidays(GeneralDate.ymd(2020,9,1),workInformation);
        target.addHolidays(GeneralDate.ymd(2020,9,3),workInformation);

        val result = target.getListRefOrdByDate();

        assertThat(result.get(0).getDate()).isEqualTo(GeneralDate.ymd(2020,9,1));
        assertThat(result.get(1).getDate()).isEqualTo(GeneralDate.ymd(2020,9,2));
        assertThat(result.get(2).getDate()).isEqualTo(GeneralDate.ymd(2020,9,3));
        assertThat(result.get(3).getDate()).isEqualTo(GeneralDate.ymd(2020,9,5));
    }


}
