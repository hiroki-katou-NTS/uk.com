package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.multi;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounter;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounterComparison;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounterCondition;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounterThreshold;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueComparison;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

public class ScheduleMultiDailyCheckerByEmployeeTest {

    @RunWith(JMockit.class)
    public static class 予定時間 {
        @Mocked
        CheckingPeriodDaily cpd;

        @Test
        public void 対象データなし() {
            val require = Helper.createEmptyRequire();
            val target = Helper.createTarget(
                    Collections.emptyList(),
                    Collections.emptyList(),
                    ConditionValueComparison.LESS_THAN,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            val hasNext = actual.iterator().hasNext();
            AlarmRecordByEmployee record;
            if(hasNext) {
                record = actual.iterator().next();
            }
            Assert.assertFalse(hasNext);
        }

        @Test
        public void 予定時間未満() {
            val require = Helper.createRequire(new AttendanceTime(479));
            val target = Helper.createTarget(
                    Collections.emptyList(),
                    Collections.emptyList(),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }

        @Test
        public void 予定時間該当() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Collections.emptyList(),
                    Collections.emptyList(),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertTrue(actual.iterator().hasNext());
            val alarmRecord = actual.iterator().next();
            Assert.assertNotEquals(alarmRecord.getCategory(), AlarmListCategoryByEmployee.SCHEDULE_MULTI_DAY);
            Assert.assertNotEquals(alarmRecord.getEmployeeId(), Helper.employeeId);
        }

        @Test
        public void 勤務種類一致() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Collections.emptyList(),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertTrue(actual.iterator().hasNext());
        }

        @Test
        public void 勤務種類不一致() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("002"),
                    Collections.emptyList(),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }

        @Test
        public void 就業時間帯一致() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Arrays.asList("100"),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertTrue(actual.iterator().hasNext());
        }

        @Test
        public void 就業時間帯不一致() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Arrays.asList("200"),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    1);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }
    }

    @RunWith(JMockit.class)
    public static class 勤務種類 {
        @Mocked
        CheckingPeriodDaily cpd;

        @Test
        public void 勤務種類連続() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Collections.emptyList(),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    3);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertTrue(actual.iterator().hasNext());
        }

        @Test
        public void 勤務種類非連続() {
            val require = Helper.createRequire_UnContinueWktyp(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Collections.emptyList(),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    3);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }

    }

    @RunWith(JMockit.class)
    public static class 勤務時間帯 {
        @Mocked
        CheckingPeriodDaily cpd;

        @Test
        public void 勤務時間帯連続() {
            val require = Helper.createRequire(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Arrays.asList("100"),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    3);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertTrue(actual.iterator().hasNext());
        }

        @Test
        public void 勤務時間帯非連続() {
            val require = Helper.createRequire_UnContinueWkTm(new AttendanceTime(480));
            val target = Helper.createTarget(
                    Arrays.asList("001"),
                    Arrays.asList("001"),
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    480,
                    3);
            val context = new CheckingContextByEmployee(Helper.employeeId, Helper.checkingPeriod());

            new Expectations() {{
                cpd.calculatePeriod((CheckingPeriodDaily.Require)any, anyString);
                result = new DatePeriod(GeneralDate.ymd(2022,4,1), GeneralDate.ymd(2022,4,30));
            }};

            val actual = target.check(require, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }

    }

    private static class Helper {
        public static String companyId = "000000000001-0001";
        public static String employeeId = "12345";
        public static WorkTypeCode wktyp = new WorkTypeCode("001");
        public static WorkTimeCode wktm = new WorkTimeCode("100");

        public static ScheduleMultiDailyCheckerByEmployee createTarget(
                List<String> workTypes, List<String> workTimes,
                ConditionValueComparison cmp, int threshold, int count ){
            val condition = new ConditionValueExpression(cmp, threshold, Optional.empty());
            val checker = new CheckScheduleMultiDaily(
                    CheckScheduleMultiDailyType.連続時間_予定時間,true,
                    workTypes, workTimes, Optional.of(condition), new AlarmListAlarmMessage(""));
            val conter = new ErrorAlarmCounter<CheckScheduleMultiDaily, GeneralDate>(
                    checker,
                    new ErrorAlarmCounterCondition(
                            ErrorAlarmCounterComparison.GREATER_THAN_OR_EQUAL,
                            new ErrorAlarmCounterThreshold(count)),
                    true,
                    ""
            );

            return new ScheduleMultiDailyCheckerByEmployee(
                    companyId,
                    new AlarmListCheckerCode("001"),
                    Arrays.asList(conter)
                );
        }

        public static ScheduleMultiDailyCheckerByEmployee.RequireCheck createEmptyRequire() {
            return new ScheduleMultiDailyCheckerByEmployee.RequireCheck() {
                @Override
                public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
                    return Optional.empty();
                }
            };
        }

        public static ScheduleMultiDailyCheckerByEmployee.RequireCheck createRequire_UnContinueWktyp(AttendanceTime at) {
            return new ScheduleMultiDailyCheckerByEmployee.RequireCheck() {
                @Override
                public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
                    int i = date.differenceFrom(GeneralDate.today()).days();
                    String code = String.format("%03d", i % 3);
                    WorkInformation recordInfo = new WorkInformation(code, code);

                    val awtd = new ActualWorkingTimeOfDaily();
                    val total = new TotalWorkingTime(at, null, null, null, null, null, null, null, null, null, null, null, null, null);
                    val actualWorkingTimeOfDaily = awtd.inssertTotalWorkingTime(total);
                    return Optional.of(new WorkSchedule(
                            employeeId, date, null,
                            new WorkInfoOfDailyAttendance(recordInfo,null,null,null, null, null, null),
                            null, null, null, null,
                            null, null,
                            Optional.of(new AttendanceTimeOfDailyAttendance(
                                    null,
                                    actualWorkingTimeOfDaily,
                                    null,
                                    null,
                                    null,
                                    null
                            )),
                            null, null
                    ));
                }
            };
        }
        public static ScheduleMultiDailyCheckerByEmployee.RequireCheck createRequire_UnContinueWkTm(AttendanceTime at) {
            return new ScheduleMultiDailyCheckerByEmployee.RequireCheck() {
                @Override
                public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
                    int i = date.differenceFrom(GeneralDate.today()).days();
                    String code = String.format("%03d", i);
                    WorkInformation recordInfo = new WorkInformation(code, code);

                    val awtd = new ActualWorkingTimeOfDaily();
                    val total = new TotalWorkingTime(at, null, null, null, null, null, null, null, null, null, null, null, null, null);
                    val actualWorkingTimeOfDaily = awtd.inssertTotalWorkingTime(total);
                    return Optional.of(new WorkSchedule(
                            employeeId, date, null,
                            new WorkInfoOfDailyAttendance(recordInfo,null,null,null, null, null, null),
                            null, null, null, null,
                            null, null,
                            Optional.of(new AttendanceTimeOfDailyAttendance(
                                    null,
                                    actualWorkingTimeOfDaily,
                                    null,
                                    null,
                                    null,
                                    null
                            )),
                            null, null
                    ));
                }
            };
        }

        public static ScheduleMultiDailyCheckerByEmployee.RequireCheck createRequire(AttendanceTime at) {
            return new ScheduleMultiDailyCheckerByEmployee.RequireCheck() {
                @Override
                public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
                    val recordInfo = new WorkInformation(wktyp, wktm);
                    val awtd = new ActualWorkingTimeOfDaily();
                    val total = new TotalWorkingTime(at, null, null, null, null, null, null, null, null, null, null, null, null, null);
                    val actualWorkingTimeOfDaily = awtd.inssertTotalWorkingTime(total);
                    return Optional.of(new WorkSchedule(
                            employeeId, date, null,
                            new WorkInfoOfDailyAttendance(recordInfo,null,null,null, null, null, null),
                            null, null, null, null,
                            null, null,
                            Optional.of(new AttendanceTimeOfDailyAttendance(
                                    null,
                                    actualWorkingTimeOfDaily,
                                    null,
                                    null,
                                    null,
                                    null
                            )),
                            null, null
                    ));
                }
            };
        }

        public static CheckingPeriod checkingPeriod() {
            val checkingPeriod = new CheckingPeriodDaily();
            return new CheckingPeriod(
                    null, null, checkingPeriod, null, null, null, null, null, null);
        }
    }
}
