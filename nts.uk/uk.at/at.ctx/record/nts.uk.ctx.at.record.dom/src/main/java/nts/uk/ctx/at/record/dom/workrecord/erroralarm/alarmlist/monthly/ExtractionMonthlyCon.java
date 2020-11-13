package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.CheckMonthlyItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;

/**
 * AggregateRoot: アラームリスト（職場）月次の抽出条件
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class ExtractionMonthlyCon extends AggregateRoot {
    /**
     * 月次抽出条件ID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private int orderNumber;

    /**
     * チェック項目
     */
    private CheckMonthlyItemsType checkMonthlyItemsType;

    /**
     * 使用区分
     */
    private boolean useAtr;

    /**
     * 勤務実績のエラーアラームチェックID
     */
    private String errorAlarmCheckID;

    /**
     * 勤務項目のチェック条件
     */
    private CheckConditions checkConditions;

    /**
     * 平均値
     */
    private AverageValueItem averageValueItem;

    /**
     * 月次抽出条件名称
     */
    private NameAlarmExtractionCondition monExtracConName;

    /**
     * 表示するメッセージ
     */
    private DisplayMessage messageDisp;

    /**
     * 作成する
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @param orderNumber           No
     * @param checkMonthlyItemsType チェック項目
     * @param useAtr                使用区分
     * @param errorAlarmCheckID     勤務実績のエラーアラームチェックID
     * @param checkConditions       勤務項目のチェック条件
     * @param averageValueItem      平均値
     * @param monExtracConName      月次抽出条件名称
     * @param messageDisp           表示するメッセージ
     */
    public static ExtractionMonthlyCon create(String errorAlarmWorkplaceId,
                                              int orderNumber,
                                              CheckMonthlyItemsType checkMonthlyItemsType,
                                              boolean useAtr,
                                              String errorAlarmCheckID,
                                              CheckConditions checkConditions,
                                              AverageValueItem averageValueItem,
                                              NameAlarmExtractionCondition monExtracConName,
                                              DisplayMessage messageDisp) {

        return new ExtractionMonthlyCon(errorAlarmWorkplaceId, orderNumber,
                checkMonthlyItemsType, useAtr,
                errorAlarmCheckID, checkConditions,
                averageValueItem, monExtracConName,
                messageDisp);
    }
}
