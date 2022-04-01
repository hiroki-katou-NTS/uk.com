package nts.uk.ctx.alarm.dom.check.checkers;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.check.TargetEmployeesFilter;
import nts.uk.ctx.alarm.dom.check.atditem.CheckBySummingAttendanceItem;
import nts.uk.ctx.alarm.dom.check.context.CheckingContext;

/**
 * アラームリストのチェック条件(スケ月次)
 */
public class ScheduleMonthlyChecker implements AlarmListChecker, DomainAggregate {

    private TargetEmployeesFilter employeesFilter;

    private CheckBySummingAttendanceItem checkBySummingAttendanceItem;

    @Override
    public AtomTask check(Require require, CheckingContext context) {
        throw new RuntimeException("not implemented");
    }

    public interface RequireCheck {

    }
}
