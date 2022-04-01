package nts.uk.ctx.alarm.dom.byemployee.check.checkers;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件
 */
public interface AlarmListCheckerByEmployee {

    AtomTask check(Require require, CheckingContextByEmployee context);

    interface Require extends
            // 全CheckerのRequireCheckを追加する
            ScheduleMonthlyChecker.RequireCheck {

    }
}
