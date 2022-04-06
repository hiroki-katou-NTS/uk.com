package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.TargetEmployeesFilter;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.atditem.CheckBySummingAttendanceItem;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * アラームリストのチェック条件(スケ月次)
 */
public class ScheduleMonthlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private TargetEmployeesFilter employeesFilter;

    private CheckBySummingAttendanceItem checkBySummingAttendanceItem;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        DatePeriod period = context.getCheckingPeriod().getMonthly().calclateDatePeriod();
        String employeeId = context.getTargetEmployeeId();
        AggregateIntegrationOfDaily aggregateService = new AggregateIntegrationOfDaily(period, employeeId);

        // 時間：予定時間＋総労働時間
        Double check01Value = aggregateService.aggregate(require, data -> {
            return (double) data.getAttendanceTimeOfDailyPerformance()
                    .get()
                    .getActualWorkingTimeOfDaily()
                    .getTotalWorkingTime().getTotalTime().valueAsMinutes();
        });

        // （予定時間＋総労働時間）×基準単価
        Double check02Value = aggregateService.aggregate(require, data -> {
            val totalTime = (double) data.getAttendanceTimeOfDailyPerformance()
                    .get()
                    .getActualWorkingTimeOfDaily()
                    .getTotalWorkingTime().getTotalTime().valueAsMinutes();
            val baseUnitPrice = require.getBaseUnitPrice(data.getYmd(), data.getEmployeeId());
            return totalTime * baseUnitPrice;
        });

        // 対比：基準時間比（通常勤務）
        val workingConditionItem = require.getWorkingConditions(employeeId, period).stream()
                .filter(wc -> wc.getWorkingConditionItem().getLaborSystem() == WorkingSystem.REGULAR_WORK)
                .collect(Collectors.toList());
        for (WorkingConditionItemWithPeriod wc : workingConditionItem) {
            AggregateIntegrationOfDaily aggregateService2 = new AggregateIntegrationOfDaily(wc.getDatePeriod(), employeeId);
            Double check03Value = aggregateService2.calcRatioValue(require,
                    data -> {
                        // TODO:どこから値を持ってくるのかまだ未定
                        return (double) data.getAttendanceTimeOfDailyPerformance()
                                .get()
                                .getActualWorkingTimeOfDaily()
                                .getTotalWorkingTime().getTotalTime().valueAsMinutes();
                    },
                    data -> (double) wc.getWorkingConditionItem().getContractTime().valueAsMinutes());
        }

        throw new RuntimeException("not implemented");
    }

    public interface RequireCheck extends AggregateIntegrationOfDaily.AggregationRequire {
        int getBaseUnitPrice(GeneralDate date, String employeeId);
        List<IntegrationOfDaily> getIntegrationOfDaily(DatePeriod period, String employeeId);
        List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period);
    }
}
