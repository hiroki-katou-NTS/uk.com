package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import mockit.Mocked;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleTestHelper;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
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
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());
            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.PUB_HOLIDAY));
        WorkCycleRefSetting refSetting = new WorkCycleRefSetting(
                "code",
                createMethods,
                1,
                "dummy",
                "dummy",
                "dummy"
        );
        List<RefImageEachDay> result = CreateWorkCycleAppImage.create(require, datePeriod, refSetting);
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
        GeneralDate startDate = GeneralDate.ymd(2020,1,1);
        GeneralDate endDate = GeneralDate.ymd(2020,1,7);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(1, "001", "101", 1));
                infos.add(new WorkCycleInfo(1, "002", "102", 2));
                infos.add(new WorkCycleInfo(1, "003", "103", 3));
                infos.add(new WorkCycleInfo(1, "004", "104", 4));
                infos.add(new WorkCycleInfo(1, "005", "105", 5));

                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate((GeneralDate) any, (GeneralDate) any);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                result = holidays;
            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY));
        WorkCycleRefSetting refSetting = new WorkCycleRefSetting(
                "code",
                createMethods,
                1,
                "dummy",
                "dummy",
                "dummy"
        );
        List<RefImageEachDay> result = CreateWorkCycleAppImage.create(require, datePeriod, refSetting);
        assertThat(result.isEmpty()).isFalse();
        val day0 = result.get(0);
        Assert.assertEquals(day0.getDate(), GeneralDate.ymd(2020, 1, 1));
        Assert.assertEquals(day0.getWorkCreateMethod(), WorkCreateMethod.PUB_HOLIDAY);
        Assert.assertEquals(day0.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day0.getWorkInformation().getWorkTimeCode(), null);

        val day1 = result.get(1);
        Assert.assertEquals(day1.getDate(), GeneralDate.ymd(2020, 1, 2));
        Assert.assertEquals(day1.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("102"));

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode(), null);

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("003"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("103"));

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("004"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("104"));

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("005"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("105"));

    }

    //Case 3
    // 祝日 -> 週間勤務 -> 勤務サイクル
    @Test
    public void test_03(@Mocked final AppContexts tr) {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));
                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());
            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.PUB_HOLIDAY, WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.WORK_CYCLE));
        WorkCycleRefSetting refSetting = new WorkCycleRefSetting(
                "code",
                createMethods,
                1,
                "dummy",
                "dummy",
                "dummy"
        );
        CreateWorkCycleAppImage.create(require, datePeriod, refSetting);
        List<RefImageEachDay> result = CreateWorkCycleAppImage.create(require, datePeriod, refSetting);
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
