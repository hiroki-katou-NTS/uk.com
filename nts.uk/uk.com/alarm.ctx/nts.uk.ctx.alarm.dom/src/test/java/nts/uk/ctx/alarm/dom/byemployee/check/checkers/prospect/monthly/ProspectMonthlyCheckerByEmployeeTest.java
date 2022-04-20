package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueComparison;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.closure.ClosureMonth;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

public class ProspectMonthlyCheckerByEmployeeTest {

    @RunWith(JMockit.class)
    public static class 条件値チェック {
        @Mocked
        CheckingPeriodMonthly checkingPeriodMonthly;
        @Mocked
        ProspectMonthlyCheckerByEmployee.RequireCheck requireCheck;
        @Mocked
        WorkingConditionItemWithPeriod workingConditionItemWithPeriod;
        @Mocked
        WorkingConditionItem workingConditionItem;

        @Test
        public void 対象データなし() {
            List<FixedLogicSetting<FixedLogicProspectMonthlyByEmployee>> fixedLogic = new ArrayList<>();
            val target = Helper.createTarget_condition(
                    ConditionValueProspectMonthlyByEmployee.支給額,
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    100000
            );
            val context = Helper.createContext();
            List<IntegrationOfDaily> integrationOfDaily = new ArrayList<>();

            new Expectations() {{
                checkingPeriodMonthly.calculatePeriod(requireCheck, anyString);
                result = Arrays.asList(Helper.closureMonth);

                requireCheck.getIntegrationOfDailyProspect(anyString, (DatePeriod)any);
                result = integrationOfDaily;

                requireCheck.getWorkingConditions(anyString, (GeneralDate) any);
                result = workingConditionItemWithPeriod;

                workingConditionItemWithPeriod.getWorkingConditionItem();
                result = new WorkingConditionItem("", ManageAtr.USE, "");

                workingConditionItem.getLaborSystem();
                result = WorkingSystem.REGULAR_WORK;
            }};

            val actual = target.check(requireCheck, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }

        @Test
        public void 条件値該当() {
            List<FixedLogicSetting<FixedLogicProspectMonthlyByEmployee>> fixedLogic = new ArrayList<>();
            val target = Helper.createTarget_condition(
                    ConditionValueProspectMonthlyByEmployee.支給額,
                    ConditionValueComparison.GREATER_THAN_OR_EQUAL,
                    100000
            );
            val context = Helper.createContext();

            val integrationOfDaily = Helper.createIntegrationOfDaily();

            new Expectations() {{
                checkingPeriodMonthly.calculatePeriod(requireCheck, anyString);
                result = Arrays.asList(Helper.closureMonth);

                requireCheck.getIntegrationOfDailyProspect(anyString, (DatePeriod)any);
                result = integrationOfDaily;

                requireCheck.getWorkingConditions(anyString, (GeneralDate) any);
                result = workingConditionItemWithPeriod;

                workingConditionItemWithPeriod.getWorkingConditionItem();
                result = new WorkingConditionItem("", ManageAtr.USE, "");

                workingConditionItem.getLaborSystem();
                result = WorkingSystem.REGULAR_WORK;
            }};

            val actual = target.check(requireCheck, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }
    }
    public static class 固定ロジック {
        @Mocked
        CheckingPeriodMonthly checkingPeriodMonthly;
        @Mocked
        ProspectMonthlyCheckerByEmployee.RequireCheck requireCheck;
        @Mocked
        WorkingConditionItemWithPeriod workingConditionItemWithPeriod;
        @Mocked
        WorkingConditionItem workingConditionItem;

        @Test
        public void 対象データなし() {
            List<FixedLogicSetting<FixedLogicProspectMonthlyByEmployee>> fixedLogic = new ArrayList<>();
            fixedLogic.add(new FixedLogicSetting(
                    FixedLogicProspectMonthlyByEmployee.目安金額比,
                    true, new AlarmListAlarmMessage("")));

            val target = Helper.createTarget_fixed(fixedLogic);
            val context = Helper.createContext();
            List<IntegrationOfDaily> integrationOfDaily = new ArrayList<>();

            new Expectations() {{
                checkingPeriodMonthly.calculatePeriod(requireCheck, anyString);
                result = Arrays.asList(Helper.closureMonth);

                requireCheck.getIntegrationOfDailyProspect(anyString, (DatePeriod) any);
                result = integrationOfDaily;

                requireCheck.getWorkingConditions(anyString, (GeneralDate) any);
                result = workingConditionItemWithPeriod;

                workingConditionItemWithPeriod.getWorkingConditionItem();
                result = workingConditionItem;

                workingConditionItem.getLaborSystem();
                result = WorkingSystem.REGULAR_WORK;
            }};

            val actual = target.check(requireCheck, context);

            Assert.assertFalse(actual.iterator().hasNext());
        }
    }

    private static class Helper {
        public static String companyId = "000000000001-0001";
        public static String employeeId = "12345";
        public static ClosureMonth closureMonth = new ClosureMonth(
                YearMonth.of(2022, 04), 1, new ClosureDate(30, true));

        public static ProspectMonthlyCheckerByEmployee createTarget_condition(
                ConditionValueProspectMonthlyByEmployee conditionLogic,
                ConditionValueComparison cmp, int threshold){
            val condition = new AlarmListConditionValue<>(
                    conditionLogic, true,
                    new ConditionValueExpression(cmp, threshold, Optional.empty()),
                    new AlarmListAlarmMessage("")
            );

            return new ProspectMonthlyCheckerByEmployee(
                    companyId,
                    new AlarmListCheckerCode("001"),
                    Arrays.asList(condition),
                    new ArrayList<>()
            );
        }

        public static ProspectMonthlyCheckerByEmployee createTarget_fixed(
                List<FixedLogicSetting<FixedLogicProspectMonthlyByEmployee>> fixedLogic){
            return new ProspectMonthlyCheckerByEmployee(
                    companyId,
                    new AlarmListCheckerCode("001"),
                    new ArrayList<>(),
                    fixedLogic
            );
        }

        public static CheckingContextByEmployee createContext() {
            val cp = new CheckingPeriod(
                    null,null,null,null, new CheckingPeriodMonthly(),null,null,null,null
            );
            return new CheckingContextByEmployee(employeeId, cp);
        }

        public static List<IntegrationOfDaily> createIntegrationOfDaily() {
            val workingTime = ActualWorkingTimeOfDaily.of(new TotalWorkingTime(
                            new AttendanceTime(50000),null, null, null,
                            null, null, null, null, null,
                            null, null, null, null, null),
                    0, 0, 0, 0);
            val at = new AttendanceTimeOfDailyAttendance(null, workingTime, null, null, null, null);
            List<IntegrationOfDaily> integrationOfDaily = closureMonth.defaultPeriod().datesBetween().stream()
                    .map(date -> {
                            val ret = new IntegrationOfDaily();
                            ret.setAttendanceTimeOfDailyPerformance(Optional.of(at));
                            return ret;
                    })
                    .collect(Collectors.toList());
            return integrationOfDaily;
        }
    }
}
