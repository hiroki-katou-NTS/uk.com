package nts.uk.ctx.alarm.dom.byemployee.check;

import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;

/**
 * アラーム検出情報(社員別)
 */
@Value
public class AlarmRecordByEmployee {

    /** 社員ID */
    String employeeId;

    /** 日付（年月、期間など） */
    String date;

    /** カテゴリ */
    AlarmListCategoryByEmployee category;

    /** チェック項目名 */
    String checkItemName;

    /** アラーム条件 */
    String alarmCondition;

    /** メッセージ */
    String message;
}
