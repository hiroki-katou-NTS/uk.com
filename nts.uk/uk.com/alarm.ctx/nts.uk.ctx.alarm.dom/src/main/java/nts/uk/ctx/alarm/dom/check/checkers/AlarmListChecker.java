package nts.uk.ctx.alarm.dom.check.checkers;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.check.context.CheckingContext;

/**
 * アラームリストのチェック条件
 */
public interface AlarmListChecker {

    AtomTask check(Require require, CheckingContext context);

    interface Require extends
            // 全CheckerのRequireCheckを追加する
            ScheduleMonthlyChecker.RequireCheck {

    }
}
