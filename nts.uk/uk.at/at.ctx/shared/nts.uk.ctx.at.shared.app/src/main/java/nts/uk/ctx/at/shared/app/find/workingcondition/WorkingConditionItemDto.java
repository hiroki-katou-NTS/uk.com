package nts.uk.ctx.at.shared.app.find.workingcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 時間休暇残数
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkingConditionItemDto {

    /** The history id. */
    // 履歴ID
    private String historyId;
//
//    /** The hourly ppayment atr. */
//    // 時給者区分
//    private int hourlyPaymentAtr;
//
//    /** The schedule management atr. */
//    // 予定管理区分
//    private int scheduleManagementAtr;
//
//    /** The work day of week. */
//    // 曜日別勤務
//    private PersonalDayOfWeek workDayOfWeek;
//
//    /** The work category. */
//    // 区分別勤務
//    private PersonalWorkCategory workCategory;
//
//    /** The auto stamp set atr. */
//    // 自動打刻セット区分
//    private int autoStampSetAtr;
//
//    /** The auto interval set atr. */
//    // 就業時間帯の自動設定区分
//    private int autoIntervalSetAtr;
//
//    /** The employee id. */
//    // 社員ID
//    private String employeeId;
//
//    /** The vacation added time atr. */
//    // 休暇加算時間利用区分
//    private int vacationAddedTimeAtr;
//
//    /** The contract time. */
//    // 契約時間
//    private LaborContractTime contractTime;
//
//    /** The labor system. */
//    // 労働制
//    private WorkingSystem laborSystem = WorkingSystem.REGULAR_WORK;
//
//    /** The holiday add time set. */
//    // 休暇加算時間設定
//    private Optional<BreakdownTimeDay> holidayAddTimeSet;
//
//    /** The schedule method. */
//    // 予定作成方法
//    private Optional<ScheduleMethod> scheduleMethod;
//
//    /** The time apply. */
//    // 加給時間帯
//    private Optional<BonusPaySettingCode> timeApply;
//
//    /** The monthly pattern. */
//    // 月間パターン
//    private Optional<MonthlyPatternCode> monthlyPattern;

    public static WorkingConditionItemDto fromOutput(WorkingConditionItem domain) {
        return new WorkingConditionItemDto();
    }

    public WorkingConditionItem toDomain() {
        return null;
    }

}
