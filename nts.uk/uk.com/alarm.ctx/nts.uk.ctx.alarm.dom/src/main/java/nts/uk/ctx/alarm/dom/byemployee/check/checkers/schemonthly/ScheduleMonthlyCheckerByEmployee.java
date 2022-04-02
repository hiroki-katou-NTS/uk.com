package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schemonthly;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.TargetEmployeesFilter;
import nts.uk.ctx.alarm.dom.byemployee.check.atditem.CheckBySummingAttendanceItem;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件(スケ月次)
 */
public class ScheduleMonthlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private TargetEmployeesFilter employeesFilter;

    private CheckBySummingAttendanceItem checkBySummingAttendanceItem;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        throw new RuntimeException("not implemented");
    }

    public interface RequireCheck {

    }
}
