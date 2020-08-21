package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import mockit.Mocked;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleTestHelper;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;

@RunWith(JMockit.class)
public class CreateWorkCycleAppImageTest {

    @Injectable
    CreateWorkCycleAppImage.Require require;

    //Case 1
    // 勤務サイクル -> 週間勤務 -> 祝日
    @Test
    public void test_01(@Mocked final AppContexts tr) {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        List<GeneralDate> dates = new ArrayList<>();
        for(int i = 0; i < 7 ; i++) {
            dates.add(startDate.addDays(i));
        }
        List<DatePeriod> datePeriod = DatePeriod.create(dates);
        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "0000001";

                require.getWorkCycle((String) any, (String) any);
                List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting((String) any);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());
            }
        };
        CreateWorkCycleAppImage serivce = new CreateWorkCycleAppImage();
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.PUB_HOLIDAY));
        WorkCycleRefSetting refSetting = new WorkCycleRefSetting(
                "code",
                createMethods,
                1,
                "dummy",
                "dummy",
                "dummy"
        );
        List<RefImageEachDay> result = serivce.create(require, datePeriod.get(0), refSetting);
        assertThat(result.isEmpty()).isFalse();
        Assert.assertEquals(result.get(0).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(1).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(2).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(3).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(4).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(5).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(6).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
    }

    //Case 2
    // 週間勤務 -> 勤務サイクル -> 祝日
    @Test
    public void test_02(@Mocked final AppContexts tr) {
        GeneralDate startDate = GeneralDate.ymd(2000,12,01);
        List<GeneralDate> dates = new ArrayList<>();
        for(int i = 0; i < 7 ; i++) {
            dates.add(startDate.addDays(i));
        }
        List<DatePeriod> datePeriod = DatePeriod.create(dates);
        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "0000001";

                require.getWorkCycle((String) any, (String) any);
                List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting((String) any);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate((String) any, (GeneralDate) any, (GeneralDate) any);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2000,12,01), "Dummy"));
                result = holidays;
            }
        };
        CreateWorkCycleAppImage serivce = new CreateWorkCycleAppImage();
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY));
        WorkCycleRefSetting refSetting = new WorkCycleRefSetting(
                "code",
                createMethods,
                1,
                "dummy",
                "dummy",
                "dummy"
        );
        serivce.create(require, datePeriod.get(0), refSetting);
        List<RefImageEachDay> result = serivce.create(require, datePeriod.get(0), refSetting);
        assertThat(result.isEmpty()).isFalse();
        Assert.assertEquals(result.get(0).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(1).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(2).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(3).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(4).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(5).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(6).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
    }

    //Case 3
    // 祝日 -> 週間勤務 -> 勤務サイクル
    @Test
    public void test_03(@Mocked final AppContexts tr) {
        GeneralDate startDate = GeneralDate.ymd(2020,02,11);
        List<GeneralDate> dates = new ArrayList<>();
        for(int i = 0; i < 7 ; i++) {
            dates.add(startDate.addDays(i));
        }
        List<DatePeriod> datePeriod = DatePeriod.create(dates);
        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "0000001";

                require.getWorkCycle((String) any, (String) any);
                List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting((String) any);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());
            }
        };
        CreateWorkCycleAppImage serivce = new CreateWorkCycleAppImage();
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.PUB_HOLIDAY, WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.WORK_CYCLE));
        WorkCycleRefSetting refSetting = new WorkCycleRefSetting(
                "code",
                createMethods,
                1,
                "dummy",
                "dummy",
                "dummy"
        );
        serivce.create(require, datePeriod.get(0), refSetting);
        List<RefImageEachDay> result = serivce.create(require, datePeriod.get(0), refSetting);
        assertThat(result.isEmpty()).isFalse();
        Assert.assertEquals(result.get(0).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(1).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(2).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(3).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(result.get(4).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(5).getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(result.get(6).getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
    }

}
