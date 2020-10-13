package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.NumOfWorkingDays;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleTestHelper;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
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
    // 勤務サイクル
    @Test
    public void test_01() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);
        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WORK_CYCLE));
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
        Assert.assertEquals(day0.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day0.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day0.getWorkInformation().getWorkTimeCode().v(), "102");

        val day1 = result.get(1);
        Assert.assertEquals(day1.getDate(), GeneralDate.ymd(2020, 1, 2));
        Assert.assertEquals(day1.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "101");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode().v(), "101");

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode().v(), "102");

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "101");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "102");
    }

    //Case 2
    // 勤務サイクル　→　週間勤務
    @Test
    public void test_02() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);
        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WORK_CYCLE,WorkCreateMethod.WEEKLY_WORK));
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
        Assert.assertEquals(day0.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day0.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day0.getWorkInformation().getWorkTimeCode().v(), "102");

        val day1 = result.get(1);
        Assert.assertEquals(day1.getDate(), GeneralDate.ymd(2020, 1, 2));
        Assert.assertEquals(day1.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "101");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "101");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "102");
    }

    //Case 3
    // 週間勤務　→　勤務サイクル
    @Test
    public void test_03() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);
        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.WORK_CYCLE));
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
        Assert.assertEquals(day0.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day0.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day0.getWorkInformation().getWorkTimeCode().v(), "102");

        val day1 = result.get(1);
        Assert.assertEquals(day1.getDate(), GeneralDate.ymd(2020, 1, 2));
        Assert.assertEquals(day1.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "101");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "101");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "102");
    }

    //Case 4
    // 勤務サイクル　→　祝日
    @Test
    public void test_04() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);
        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getpHolidayWhileDate(startDate,endDate);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                result = holidays;

            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY));
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "101");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode().v(), "101");

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode().v(), "102");

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "101");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "102");
    }

    //Case 5
    // 祝日　→　勤務サイクル
    @Test
    public void test_05() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);
        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getpHolidayWhileDate(startDate,endDate);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                result = holidays;

            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.PUB_HOLIDAY,WorkCreateMethod.WORK_CYCLE));
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "102");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "101");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode().v(), "102");

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode().v(), "101");

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "102");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "101");
    }

    //Case 6
    // 勤務サイクル -> 週間勤務 -> 祝日
    @Test
    public void test_06() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate(startDate,endDate);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,4), "Dummy"));
                result = holidays;
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
        val day0 = result.get(0);
        Assert.assertEquals(day0.getDate(), GeneralDate.ymd(2020, 1, 1));
        Assert.assertEquals(day0.getWorkCreateMethod(), WorkCreateMethod.PUB_HOLIDAY);
        Assert.assertEquals(day0.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day0.getWorkInformation().getWorkTimeCode(), null);

        val day1 = result.get(1);
        Assert.assertEquals(day1.getDate(), GeneralDate.ymd(2020, 1, 2));
        Assert.assertEquals(day1.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "101");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "101");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "102");
    }

    //Case 7
    // 勤務サイクル　→　祝日　→　週間勤務
    @Test
    public void test_07() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);

        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1),  new WorkInformation("002", "102")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate(startDate,endDate);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,4), "Dummy"));
                result = holidays;
            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.PUB_HOLIDAY, WorkCreateMethod.WEEKLY_WORK));
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode().v(), "101");

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.PUB_HOLIDAY);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "101");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode().v(), "102");
    }
    //Case 8
    // 祝日 -> 週間勤務 -> 勤務サイクル
    @Test
    public void test_08() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("002", "102")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("003", "103")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("004", "104")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("005", "105")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate((GeneralDate) any, (GeneralDate) any);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,4), "Dummy"));
                result = holidays;
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("005"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("105"));

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "101");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.PUB_HOLIDAY);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "102");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("003"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("103"));
    }
    //Case 9
    // 祝日　　→　勤務サイクル　→　週間勤務
    @Test
    public void test_09() {
        GeneralDate startDate = GeneralDate.ymd(2020,1,1);
        GeneralDate endDate = GeneralDate.ymd(2020,1,7);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("002", "102")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("003", "103")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("004", "104")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("005", "105")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate((GeneralDate) any, (GeneralDate) any);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,4), "Dummy"));
                result = holidays;
            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.PUB_HOLIDAY, WorkCreateMethod.WORK_CYCLE, WorkCreateMethod.WEEKLY_WORK));
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("005"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("105"));

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "101");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.PUB_HOLIDAY);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("003"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("103"));

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("004"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("104"));

    }



    //Case 10
    // 週間勤務　→　祝日　→　勤務サイクル
    @Test
    public void test_10() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("002", "102")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("003", "103")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("004", "104")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("005", "105")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate((GeneralDate) any, (GeneralDate) any);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,4), "Dummy"));
                result = holidays;
            }
        };
        List<WorkCreateMethod> createMethods = new ArrayList<>(Arrays.asList(WorkCreateMethod.WEEKLY_WORK, WorkCreateMethod.PUB_HOLIDAY, WorkCreateMethod.WORK_CYCLE));
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("005"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("105"));

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "101");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "102");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("003"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("103"));
    }

    //Case 11
    // 週間勤務　→　勤務サイクル　→　祝日
    @Test
    public void test_11() {
        GeneralDate startDate = GeneralDate.ymd(2020,01,01);
        GeneralDate endDate = GeneralDate.ymd(2020,01,07);

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        new Expectations() {
            {
                require.getWorkCycle((String) any);
                List<WorkCycleInfo> infos = new ArrayList<>();
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("001", "101")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("002", "102")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("003", "103")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("004", "104")));
                infos.add(new WorkCycleInfo(new NumOfWorkingDays(1), new WorkInformation("005", "105")));
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos));

                require.getWeeklyWorkSetting();
                result = Optional.of(WorkCycleTestHelper.WorkCycleHelper.createWeeklyWorkDayPattern());

                require.getpHolidayWhileDate((GeneralDate) any, (GeneralDate) any);
                List<PublicHoliday> holidays = new ArrayList<>();
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,1), "Dummy"));
                holidays.add(PublicHoliday.createFromJavaType("0000001", GeneralDate.ymd(2020,1,4), "Dummy"));
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
        Assert.assertEquals(day1.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("001"));
        Assert.assertEquals(day1.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("101"));

        val day2 = result.get(2);
        Assert.assertEquals(day2.getDate(), GeneralDate.ymd(2020, 1, 3));
        Assert.assertEquals(day2.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day2.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("002"));
        Assert.assertEquals(day2.getWorkInformation().getWorkTimeCode().v(), "102");

        val day3 = result.get(3);
        Assert.assertEquals(day3.getDate(), GeneralDate.ymd(2020, 1, 4));
        Assert.assertEquals(day3.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day3.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day3.getWorkInformation().getWorkTimeCode(), null);

        val day4 = result.get(4);
        Assert.assertEquals(day4.getDate(), GeneralDate.ymd(2020, 1, 5));
        Assert.assertEquals(day4.getWorkCreateMethod(), WorkCreateMethod.WEEKLY_WORK);
        Assert.assertEquals(day4.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("dummy"));
        Assert.assertEquals(day4.getWorkInformation().getWorkTimeCode(), null);

        val day5 = result.get(5);
        Assert.assertEquals(day5.getDate(), GeneralDate.ymd(2020, 1, 6));
        Assert.assertEquals(day5.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day5.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("003"));
        Assert.assertEquals(day5.getWorkInformation().getWorkTimeCode().v(), "103");

        val day6 = result.get(6);
        Assert.assertEquals(day6.getDate(), GeneralDate.ymd(2020, 1, 7));
        Assert.assertEquals(day6.getWorkCreateMethod(), WorkCreateMethod.WORK_CYCLE);
        Assert.assertEquals(day6.getWorkInformation().getWorkTypeCode(), new WorkTypeCode("004"));
        Assert.assertEquals(day6.getWorkInformation().getWorkTimeCode(), new WorkTimeCode("104"));
    }

}
