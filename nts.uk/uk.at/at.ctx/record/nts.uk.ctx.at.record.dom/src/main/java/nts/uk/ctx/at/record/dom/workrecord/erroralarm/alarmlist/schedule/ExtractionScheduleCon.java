package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;

/**
 * AggregateRoot: アラームリスト（職場別）スケジュール／日次の抽出条件
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class ExtractionScheduleCon extends AggregateRoot {
    /**
     * スケジュール／日次抽出条件ID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private int orderNumber;

    /**
     * チェック項目
     */
    private CheckDayItemsType checkDayItemsType;

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
     * 対比チェック項目
     */
    private ComparisonCheckItems comparisonCheckItems;

    /**
     * 日次抽出条件名称
     */
    private NameAlarmExtractionCondition daiExtracConName;

    /**
     * 表示するメッセージ
     */
    private DisplayMessage messageDisp;

    /**
     * 作成する
     *
     * @param errorAlarmWorkplaceId スケジュール／日次抽出条件ID
     * @param orderNumber           No
     * @param checkDayItemsType     チェック項目
     * @param useAtr                使用区分
     * @param errorAlarmCheckID     勤務実績のエラーアラームチェックID
     * @param checkConditions       勤務項目のチェック条件
     * @param checkTarget           チェック対象
     * @param contrastType          対比チェック対象
     * @param daiExtracConName      日次抽出条件名称
     * @param messageDisp           表示するメッセージ
     */
    public static ExtractionScheduleCon create(String errorAlarmWorkplaceId,
                                               int orderNumber,
                                               int checkDayItemsType,
                                               boolean useAtr,
                                               String errorAlarmCheckID,
                                               CheckConditions checkConditions,
                                               String checkTarget,
                                               Integer contrastType,
                                               String daiExtracConName,
                                               String messageDisp) {

        return new ExtractionScheduleCon(errorAlarmWorkplaceId,
                orderNumber,
                EnumAdaptor.valueOf(checkDayItemsType, CheckDayItemsType.class),
                useAtr,
                errorAlarmCheckID,
                checkConditions,
                ComparisonCheckItems.create(checkTarget, contrastType),
                new NameAlarmExtractionCondition(daiExtracConName),
                new DisplayMessage(messageDisp));
    }
}
