package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.CheckMonthlyItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * AggregateRoot: アラームリスト（職場）月次の抽出条件
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class ExtractionMonthlyCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
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
     * 範囲との比較
     */
    private CompareRange compareRange;

    /**
     * 単一値との比較
     */
    private CompareSingleValue compareSingleValue;

    /**
     * 作成する
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @param orderNumber           No
     * @param checkMonthlyItemsType チェック項目
     * @param useAtr                使用区分
     * @param averageValueItem      平均値
     * @param monExtracConName      月次抽出条件名称
     * @param messageDisp           表示するメッセージ
     * @param compareRange          範囲との比較
     * @param compareSingleValue    単一値との比較
     */
    public static ExtractionMonthlyCon create(String errorAlarmWorkplaceId,
                                              int orderNumber,
                                              CheckMonthlyItemsType checkMonthlyItemsType,
                                              boolean useAtr,
                                              AverageValueItem averageValueItem,
                                              NameAlarmExtractionCondition monExtracConName,
                                              DisplayMessage messageDisp,
                                              CompareRange compareRange,
                                              CompareSingleValue compareSingleValue) {

        return new ExtractionMonthlyCon(errorAlarmWorkplaceId, orderNumber,
                checkMonthlyItemsType, useAtr,
                averageValueItem, monExtracConName,
                messageDisp, compareRange,
                compareSingleValue);
    }
}
