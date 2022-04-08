package nts.uk.ctx.alarm.dom.byemployee.result;

import lombok.Value;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * アラーム検出情報(社員別)
 */
@Value
public class AlarmRecordByEmployee {

    /** 社員ID */
    String employeeId;

    /** 日付情報 */
    DateInfo dateInfo;

    /** カテゴリ */
    AlarmListCategoryByEmployee category;

    /** チェック項目名 */
    String checkItemName;

    /** アラーム条件 */
    String alarmCondition;

    /** メッセージ */
    String message;

    /**
     * エラーアラームの情報から作る
     * @param require
     * @param employeeId
     * @param dateInfo
     * @param category
     * @param errorAlarmName
     * @param attendanceItemIds
     * @param message
     * @return
     */
    public static AlarmRecordByEmployee fromErrorAlarm(
            RequireFromErrorAlarm require,
            String employeeId,
            DateInfo dateInfo,
            AlarmListCategoryByEmployee category,
            String errorAlarmName,
            List<Integer> attendanceItemIds,
            String message) {

        String itemNames = getItemNames(require, category, attendanceItemIds);
        return new AlarmRecordByEmployee(employeeId, dateInfo, category, errorAlarmName, itemNames, message);
    }

    private static String getItemNames(RequireFromErrorAlarm require, AlarmListCategoryByEmployee category, List<Integer> attendanceItemIds) {

        TypeOfItem type = CATEGORY_TO_TYPE_OF_ITEM.get(category);
        if (type == null) {
            throw new RuntimeException("unknown: " + category);
        }

        return attendanceItemIds.stream()
                .map(id -> require.getAttendanceItemName(type, id))
                .collect(Collectors.joining(", "));
    }

    private static final Map<AlarmListCategoryByEmployee, TypeOfItem> CATEGORY_TO_TYPE_OF_ITEM;
    static {
        CATEGORY_TO_TYPE_OF_ITEM = new HashMap<>();
        CATEGORY_TO_TYPE_OF_ITEM.put(AlarmListCategoryByEmployee.RECORD_DAILY, TypeOfItem.Daily);
        CATEGORY_TO_TYPE_OF_ITEM.put(AlarmListCategoryByEmployee.RECORD_WEEKLY, TypeOfItem.Weekly);
        CATEGORY_TO_TYPE_OF_ITEM.put(AlarmListCategoryByEmployee.RECORD_MONTHLY, TypeOfItem.Monthly);
        CATEGORY_TO_TYPE_OF_ITEM.put(AlarmListCategoryByEmployee.RECORD_ANY_PERIOD, TypeOfItem.AnyPeriod);
    }

    public interface RequireFromErrorAlarm {
        String getAttendanceItemName(TypeOfItem typeOfItem, int itemId);
    }

    public ExtractEmployeeErAlData toPersist(String executeId) {
        String recordId = IdentifierUtil.randomUniqueId();
        String comment = "";
        String endDate = "";
        return new ExtractEmployeeErAlData(
                executeId,
                employeeId,
                recordId,
                dateInfo.getFormatted(),
                category.value,
                category.categoryName(),
                checkItemName,
                message,
                comment,
                alarmCondition,
                endDate);
    }

}
