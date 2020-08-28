package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RefImageEachDay;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.ReflectionImage;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class ReflectionImageTest {

    @Test
    public void getters() {

        ReflectionImage target = new ReflectionImage(new HashMap<>());
        NtsAssert.invokeGetters(target);
    }


    @Test
    public void addByWeeklyWorkingTest_1() {
        ReflectionImage target = new ReflectionImage();
        target.addByWeeklyWorking(GeneralDate.today(), null);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
        Assert.assertEquals(null,result.getWorkInformation());
    }

    @Test
    public void addByWeeklyWorkingTest_2() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTimeCode", "workCcle-workTypeCode");
        WorkInformation weeklWorkInfor = new WorkInformation("weekly-workTimeCode", "weekly-workTypeCode");

        ReflectionImage target = new ReflectionImage();
        target.addByWeeklyWorking(GeneralDate.today(),workCycleWorkInfor);
        target.addByWeeklyWorking(GeneralDate.today(),weeklWorkInfor);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
        Assert.assertEquals(weeklWorkInfor,result.getWorkInformation());
    }

    @Test
    public void addByWeeklyWorkingTest_3() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        refImageEachDay.setWorkCreateMethod(WorkCreateMethod.PUB_HOLIDAY);

        HashMap<GeneralDate, RefImageEachDay> mapTest = new HashMap<>();
        mapTest.put(GeneralDate.today(), refImageEachDay);
        ReflectionImage target = new ReflectionImage(mapTest);
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(WorkCreateMethod.PUB_HOLIDAY,result.getWorkCreateMethod());
    }
    @Test
    public void addByWeeklyWorkingTest_4() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        refImageEachDay.setWorkCreateMethod(WorkCreateMethod.WORK_CYCLE);

        HashMap<GeneralDate, RefImageEachDay> mapTest = new HashMap<>();
        mapTest.put(GeneralDate.today(), refImageEachDay);
        ReflectionImage target = new ReflectionImage(mapTest);
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
        Assert.assertEquals(workInformation,result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest() {
        WorkInformation workCycleWorkInfor = new WorkInformation("workCcle-workTimeCode", "workCcle-workTypeCode");
        WorkInformation weeklWorkInfor = new WorkInformation("weekly-workTimeCode", "weekly-workTypeCode");

        ReflectionImage target = new ReflectionImage();
        target.addHolidays(GeneralDate.today(),workCycleWorkInfor);
        target.addHolidays(GeneralDate.today(),weeklWorkInfor);

        val result = target.getListRefOrdByDate().get(0);

        Assert.assertEquals(GeneralDate.today(),result.getDate());
        Assert.assertEquals(WorkCreateMethod.PUB_HOLIDAY,result.getWorkCreateMethod());
        Assert.assertEquals(weeklWorkInfor,result.getWorkInformation());
    }

    @Test
    public void addHolidaysTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        refImageEachDay.setWorkCreateMethod(WorkCreateMethod.WEEKLY_WORK);

        HashMap<GeneralDate, RefImageEachDay> mapTest = new HashMap<>();
        mapTest.put(GeneralDate.today(), refImageEachDay);
        ReflectionImage target = new ReflectionImage(mapTest);
        target.addHolidays(GeneralDate.today(),workInformation);

        val result = target.getListRefOrdByDate().get(0);
        Assert.assertEquals(WorkCreateMethod.WEEKLY_WORK,result.getWorkCreateMethod());
    }

    @Test
    public void addInWorkCycleTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = new ReflectionImage();
        target.addInWorkCycle(GeneralDate.today(),workInformation);
        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isFalse();
    }

    @Test
    public void addInWorkCycleTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

        RefImageEachDay refImageEachDay = new RefImageEachDay();
        HashMap<GeneralDate, RefImageEachDay> mapTest = new HashMap<>();
        mapTest.put(GeneralDate.max(), refImageEachDay);
        ReflectionImage target = new ReflectionImage(mapTest);

        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isTrue();
    }

    @Test
    public void getListRefOrdByDateTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode","workTypeCode");
        RefImageEachDay item1 = new RefImageEachDay(1,workInformation,GeneralDate.today());
        RefImageEachDay item2 = new RefImageEachDay(2,workInformation,GeneralDate.max());

        HashMap<GeneralDate, RefImageEachDay> mapTest = new HashMap<>();
        mapTest.put(GeneralDate.max(), item1);
        mapTest.put(GeneralDate.today(), item2);
        ReflectionImage target = new ReflectionImage(mapTest);

        val result = target.getListRefOrdByDate();
        assertThat(result.get(0).getDate().equals(GeneralDate.today()));
        assertThat(result.get(1).getDate().equals(GeneralDate.max()));
    }


}
