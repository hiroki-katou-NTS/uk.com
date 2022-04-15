package nts.uk.ctx.alarm.dom.byemployee.check.checkers;

import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly.ProspectYearlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multiday.MultiDayCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multimonth.MultiMonthlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.weekly.CheckErrorAlarmWeekly;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.single.ScheduleDailyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation.VacationCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.monthly.Agree36MontlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.multimonth.Agree36MultiMonthCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.yearly.Agree36YearlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.appapproval.AppApprovalCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.master.MasterCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.anyperiod.AnyPeriodCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily.DailyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.monthly.MonthlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly.ProspectMonthlyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件
 */
public interface AlarmListCheckerByEmployee {

    Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context);

    interface Require extends
            // 全CheckerのRequireCheckを追加する
            ScheduleDailyCheckerByEmployee.RequireCheck,
            ProspectMonthlyCheckerByEmployee.RequireCheck,
            ProspectYearlyCheckerByEmployee.RequireChack,
            DailyCheckerByEmployee.RequireCheck,
            CheckErrorAlarmWeekly.RequireCheck,
            MonthlyCheckerByEmployee.RequireCheck,
            MultiDayCheckerByEmployee.RequireCheck,
            MultiMonthlyCheckerByEmployee.RequireCheck,
            AnyPeriodCheckerByEmployee.RequireCheck,
            MasterCheckerByEmployee.RequireCheck,
            AppApprovalCheckerByEmployee.RequireCheck,
            VacationCheckerByEmployee.RequireCheck,
            Agree36MontlyCheckerByEmployee.RequireCheck,
            Agree36YearlyCheckerByEmployee.RequireCheck,
            Agree36MultiMonthCheckerByEmployee.RequireCheck
            {
    }
}
