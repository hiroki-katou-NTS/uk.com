package nts.uk.ctx.alarm.dom.conditionvalue;

import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;

public interface ConditionValueContext {

    AlarmListCategoryByEmployee getCategory();

    String getEmployeeId();

    DateInfo getDateInfo();
}
