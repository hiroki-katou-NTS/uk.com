package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.CheckMonthlyItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageTime;
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
public class ExtractionMonthlyCon<V> extends AggregateRoot {
    /**
     * 月次抽出条件ID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private int no;

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
     * @param no                    No
     * @param checkMonthlyItemsType チェック項目
     * @param useAtr                使用区分
     * @param errorAlarmCheckID     勤務実績のエラーアラームチェックID
     * @param checkConditions       勤務項目のチェック条件
     * @param checkTarget           チェック対象
     * @param averageNumberOfDays   平均日数
     * @param averageNumberOfTimes  平均回数
     * @param averageTime           平均時間
     * @param averageRatio          平均比率
     * @param monExtracConName      月次抽出条件名称
     * @param messageDisp           表示するメッセージ
     */
    public static ExtractionMonthlyCon create(String errorAlarmWorkplaceId,
                                              int no,
                                              int checkMonthlyItemsType,
                                              boolean useAtr,
                                              String errorAlarmCheckID,
                                              CheckConditions checkConditions,
                                              String checkTarget,
                                              Integer averageNumberOfDays,
                                              Integer averageNumberOfTimes,
                                              Integer averageTime,
                                              Integer averageRatio,
                                              String monExtracConName,
                                              String messageDisp) {

        return new ExtractionMonthlyCon(errorAlarmWorkplaceId,
                no,
                EnumAdaptor.valueOf(checkMonthlyItemsType, CheckMonthlyItemsType.class),
                useAtr,
                errorAlarmCheckID,
                checkConditions,
                AverageValueItem.create(checkTarget, averageNumberOfDays, averageNumberOfTimes, averageTime, averageRatio),
                new NameAlarmExtractionCondition(monExtracConName),
                new DisplayMessage(messageDisp));
    }

    public boolean checkTarget(Double target) {
        return this.getCheckConditions().check(target, x -> getVValue((V) x));
    }

    private Double getVValue(V target) {
        switch (this.checkMonthlyItemsType) {
            case AVERAGE_TIME:
            case TIME_FREEDOM:
                return ((AverageTime) target).v().doubleValue();
            case AVERAGE_NUMBER_DAY:
            case AVERAGE_DAY_FREE:
                return ((AverageNumberDays) target).v().doubleValue();
            case AVERAGE_NUMBER_TIME:
            case AVERAGE_TIME_FREE:
                return Double.valueOf(((AverageNumberTimes) target).v());
            case AVERAGE_RATIO:
            case AVERAGE_RATIO_FREE:
                return Double.valueOf(((AverageRatio) target).v());
            default:
                throw new RuntimeException("Invalid チェック項目: " + this.checkMonthlyItemsType);
        }
    }
}
