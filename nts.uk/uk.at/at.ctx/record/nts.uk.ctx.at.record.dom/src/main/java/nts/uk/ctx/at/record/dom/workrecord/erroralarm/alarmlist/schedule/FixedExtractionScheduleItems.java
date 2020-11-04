package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

import java.util.Optional;

/**
 * AggregateRoot: アラームリスト（職場別）スケジュール／日次の固定抽出項目
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionScheduleItems extends AggregateRoot {
    /**
     * No
     */
    private FixedCheckDayItemName fixedCheckDayItemName;

    /**
     * アラームチェック区分
     */
    private AlarmCheckClassification alarmCheckCls;

    /**
     * メッセージを太字にする
     */
    private boolean boldAtr;

    /**
     * スケジュール／日次チェック名称
     */
    private String scheduleCheckName;

    /**
     * 最初表示するメッセージ
     */
    private DisplayMessage firstMessageDisp;

    /**
     * メッセージの色
     */
    private Optional<ColorCode> messageColor;

    /**
     * 作成する
     *
     * @param fixedCheckDayItemName No
     * @param alarmCheckCls         アラームチェック区分
     * @param boldAtr               メッセージを太字にする
     * @param scheduleCheckName     スケジュール／日次チェック名称
     * @param firstMessageDisp      最初表示するメッセージ
     * @param messageColor          メッセージの色
     */
    public static FixedExtractionScheduleItems create(FixedCheckDayItemName fixedCheckDayItemName,
                                                      AlarmCheckClassification alarmCheckCls,
                                                      boolean boldAtr,
                                                      String scheduleCheckName,
                                                      DisplayMessage firstMessageDisp,
                                                      Optional<ColorCode> messageColor) {

        return new FixedExtractionScheduleItems(fixedCheckDayItemName, alarmCheckCls,
                boldAtr, scheduleCheckName,
                firstMessageDisp, messageColor);
    }
}
