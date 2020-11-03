package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * AggregateRoot: アラームリスト（職場別）スケジュール／日次の固定抽出条件
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionScheduleCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private FixedCheckDayItemName fixedCheckDayItemName;

    /**
     * 使用区分
     */
    private boolean useAtr;

    /**
     * 表示するメッセージ
     */
    private DisplayMessage messageDisp;

    /**
     * 作成する
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @param fixedCheckDayItemName No
     * @param useAtr                使用区分
     * @param messageDisp           表示するメッセージ
     */
    public static FixedExtractionScheduleCon create(String errorAlarmWorkplaceId,
                                                    FixedCheckDayItemName fixedCheckDayItemName,
                                                    boolean useAtr,
                                                    DisplayMessage messageDisp) {

        return new FixedExtractionScheduleCon(errorAlarmWorkplaceId, fixedCheckDayItemName, useAtr, messageDisp);
    }
}
