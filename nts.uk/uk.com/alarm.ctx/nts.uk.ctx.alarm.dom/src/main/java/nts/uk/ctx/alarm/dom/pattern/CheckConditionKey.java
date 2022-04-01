package nts.uk.ctx.alarm.dom.pattern;

import lombok.Value;
import nts.uk.ctx.alarm.dom.check.checkers.AlarmListCategory;
import nts.uk.ctx.alarm.dom.check.checkers.AlarmListCheckerCode;

/**
 * チェック条件のキー
 */
@Value
public class CheckConditionKey {

    /** カテゴリ */
    AlarmListCategory category;

    /** チェック条件コード */
    AlarmListCheckerCode checkerCode;
}
