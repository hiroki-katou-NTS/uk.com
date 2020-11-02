package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

import java.util.Optional;

/**
 * AggregateRoot: アラームリスト（職場）申請承認の固定抽出項目
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionAppapvItems extends AggregateRoot {
    /**
     * No
     */
    private CheckItemAppapv checkItemAppapv;

    /**
     * アラームチェック区分
     */
    private AlarmCheckClassification alarmCheckCls;

    /**
     * メッセージを太字にする
     */
    private boolean boldAtr;

    /**
     * 申請承認チェック名称
     */
    private String appapvCheckName;

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
     * @param checkItemAppapv  No
     * @param alarmCheckCls    アラームチェック区分
     * @param boldAtr          メッセージを太字にする
     * @param appapvCheckName  申請承認チェック名称
     * @param firstMessageDisp 最初表示するメッセージ
     * @param messageColor     メッセージの色
     */
    public static FixedExtractionAppapvItems create(CheckItemAppapv checkItemAppapv,
                                                    AlarmCheckClassification alarmCheckCls,
                                                    boolean boldAtr,
                                                    String appapvCheckName,
                                                    DisplayMessage firstMessageDisp,
                                                    Optional<ColorCode> messageColor) {

        return new FixedExtractionAppapvItems(checkItemAppapv, alarmCheckCls,
                boldAtr, appapvCheckName,
                firstMessageDisp, messageColor);
    }
}
