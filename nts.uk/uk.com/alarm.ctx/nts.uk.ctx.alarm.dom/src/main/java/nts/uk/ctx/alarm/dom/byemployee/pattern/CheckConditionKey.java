package nts.uk.ctx.alarm.dom.byemployee.pattern;

import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;

/**
 * チェック条件のキー
 */
@Value
public class CheckConditionKey {

    /** カテゴリ */
    AlarmListCategoryByEmployee category;

    /** チェック条件コード */
    AlarmListCheckerCode checkerCode;
}
