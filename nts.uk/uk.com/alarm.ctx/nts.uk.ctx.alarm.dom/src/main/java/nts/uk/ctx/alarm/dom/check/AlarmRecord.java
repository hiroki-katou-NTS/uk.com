package nts.uk.ctx.alarm.dom.check;

import lombok.Value;
import nts.uk.ctx.alarm.dom.check.checkers.AlarmListCategory;

/**
 * 1つ分のアラーム検出情報
 */
@Value
public class AlarmRecord {

    /** 社員ID */
    String employeeId;

    /** 日付（年月、期間など） */
    String date;

    /** カテゴリ */
    AlarmListCategory category;

    /** チェック項目名 */
    String checkItemName;

    /** アラーム条件 */
    String alarmCondition;

    /** メッセージ */
    String message;
}
