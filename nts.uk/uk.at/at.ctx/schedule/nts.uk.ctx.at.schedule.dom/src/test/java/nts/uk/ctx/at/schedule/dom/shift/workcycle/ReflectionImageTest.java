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

@RunWith(JMockit.class)
public class ReflectionImageTest {

    @Test
    public void getters() {

        ReflectionImage target = ReflectionImage.create();
        NtsAssert.invokeGetters(target);
    }


    @Test
    public void addByWeeklyWorkingTest_1() {
        ReflectionImage target = ReflectionImage.create();
        target.addByWeeklyWorking(GeneralDate.today(), null);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
        Assert.assertEquals(null,result.getWorkInformation());
    }

    @Test
    public void addByWeeklyWorkingTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTypeCode", "workCcle-workTimeCode");
        WorkInformation weeklWorkInfor = new WorkInformation("weekly-workTypeCode", "weekly-workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workCycleWorkInfor);

        target.addByWeeklyWorking(GeneralDate.today(),weeklWorkInfor);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
        Assert.assertEquals(weeklWorkInfor,result.getWorkInformation());
    }

    @Test
    public void addByWeeklyWorkingTest_3() {
        WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.today(), workInformation);

        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.PUB_HOLIDAY,result.getWorkCreateMethod());
        Assert.assertEquals(workInformation,result.getWorkInformation());

    }

    @Test
    public void addHolidaysTest_1() {
        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.today(), null);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.PUB_HOLIDAY,result.getWorkCreateMethod());
        Assert.assertEquals(null,result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTypeCode", "workCcle-workTimeCode");
        WorkInformation weeklWorkInfor = new WorkInformation("weekly-workTypeCode", "weekly-workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addInWorkCycle(GeneralDate.today(),workCycleWorkInfor);

        target.addHolidays(GeneralDate.today(),weeklWorkInfor);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.PUB_HOLIDAY,result.getWorkCreateMethod());
        Assert.assertEquals(weeklWorkInfor,result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest_3() {
        WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

        ReflectionImage target = ReflectionImage.create();
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);
        
        target.addHolidays(GeneralDate.today(), workInformation);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
        Assert.assertEquals(workInformation,result.getWorkInformation());
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
        WorkInformation holidayWorkInfo = new WorkInformation("workTypeCode1", "workTimeCode1");
        WorkInformation workInformation = new WorkInformation("workTypeCode2", "workTimeCode2");

        ReflectionImage target = ReflectionImage.create();
        target.addHolidays(GeneralDate.max(),holidayWorkInfo);

        val result = target.getListRefOrdByDate().get(0);

        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isTrue();
        Assert.assertEquals(GeneralDate.max(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.PUB_HOLIDAY,result.getWorkCreateMethod());
        Assert.assertEquals(holidayWorkInfo,result.getWorkInformation());
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
        Assert.assertEquals(result.get(0).getDate(),GeneralDate.ymd(2020,9,1));
        Assert.assertEquals(result.get(1).getDate(),GeneralDate.ymd(2020,9,2));
        Assert.assertEquals(result.get(2).getDate(),GeneralDate.ymd(2020,9,3));
        Assert.assertEquals(result.get(3).getDate(),GeneralDate.ymd(2020,9,5));
    }


}
