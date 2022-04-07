package nts.uk.ctx.alarm.dom.byemployee.check.result;

import lombok.Value;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;

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

    public ExtractEmployeeErAlData toPersist(String executeId) {
        String recordId = IdentifierUtil.randomUniqueId();
        String comment = "";
        String endDate = "";
        return new ExtractEmployeeErAlData(
                executeId,
                employeeId,
                recordId,
                date,
                category.value,
                category.categoryName(),
                checkItemName,
                message,
                comment,
                alarmCondition,
                endDate);
    }
}
