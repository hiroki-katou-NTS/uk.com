package nts.uk.ctx.alarm.dom.byemployee.check.checkers;

import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.appapproval.AppApprovalCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.daily.DailyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.master.MasterCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.monthly.MonthlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.schemonthly.ScheduleMonthlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件
 */
public interface AlarmListCheckerByEmployee {

    Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context);

    interface Require extends
            // 全CheckerのRequireCheckを追加する
            ScheduleMonthlyCheckerByEmployee.RequireCheck,
            DailyCheckerByEmployee.RequireCheck,
            MonthlyCheckerByEmployee.RequireCheck,
            MasterCheckerByEmployee.RequireCheck,
            AppApprovalCheckerByEmployee.RequireCheck
            {
    }
}
