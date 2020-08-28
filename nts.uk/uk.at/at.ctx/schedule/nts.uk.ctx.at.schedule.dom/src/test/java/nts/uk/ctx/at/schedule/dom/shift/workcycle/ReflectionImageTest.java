package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import static org.assertj.core.api.Assertions.assertThat;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RefImageEachDay;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.ReflectionImage;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

@RunWith(JMockit.class)
public class ReflectionImageTest {

    @Test
    public void getters() {

        ReflectionImage target = new ReflectionImage(new HashMap<>());
        NtsAssert.invokeGetters(target);
    }

    @Test
    public void seters() {
        ReflectionImage target = new ReflectionImage();
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        HashMap<GeneralDate, RefImageEachDay> mapTest = new HashMap<>();
        mapTest.put(GeneralDate.today(), refImageEachDay);
        target.setDay(mapTest);
        assertThat(mapTest.equals(target.getDay()));
    }

    @Test
    public void addByWeeklyWorkingTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = new ReflectionImage();
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);
        assertThat(target.getDay()).isNotEmpty();
    }

    @Test
    public void addByWeeklyWorkingTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        refImageEachDay.setWorkCreateMethod(WorkCreateMethod.PUB_HOLIDAY);
        ReflectionImage target = new ReflectionImage();
        target.getDay().put(GeneralDate.today(),refImageEachDay);
        target.addByWeeklyWorking(GeneralDate.today(),workInformation);
        assertThat(target.getDay()).isNotEmpty();
    }

    @Test
    public void addHolidaysTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = new ReflectionImage();
        target.addHolidays(GeneralDate.today(),workInformation);
        assertThat(target.getDay()).isNotEmpty();
    }

    @Test
    public void addHolidaysTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        refImageEachDay.setWorkCreateMethod(WorkCreateMethod.WEEKLY_WORK);
        ReflectionImage target = new ReflectionImage();
        target.getDay().put(GeneralDate.today(),refImageEachDay);
        target.addHolidays(GeneralDate.today(),workInformation);
        assertThat(target.getDay()).isNotEmpty();
    }

    @Test
    public void adInWorkCycleTest() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");
        ReflectionImage target = new ReflectionImage();
        target.addInWorkCycle(GeneralDate.today(),workInformation);
        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isFalse();
    }

    @Test
    public void addInWorkCycleTest_1() {
        WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

        RefImageEachDay refImageEachDay = new RefImageEachDay();
        refImageEachDay.setWorkCreateMethod(WorkCreateMethod.WORK_CYCLE);
        ReflectionImage target = new ReflectionImage();
        target.getDay().put(GeneralDate.max(),refImageEachDay);

        assertThat(target.addInWorkCycle(GeneralDate.today(),workInformation)).isTrue();
    }

    @Test
    public void getListRefOrdByDateTest() {
        RefImageEachDay refImageEachDay = new RefImageEachDay();
        ReflectionImage target = new ReflectionImage();
        target.getDay().put(GeneralDate.max(),refImageEachDay);
        assertThat(target.getListRefOrdByDate().size()).isEqualTo(1);
    }


}
