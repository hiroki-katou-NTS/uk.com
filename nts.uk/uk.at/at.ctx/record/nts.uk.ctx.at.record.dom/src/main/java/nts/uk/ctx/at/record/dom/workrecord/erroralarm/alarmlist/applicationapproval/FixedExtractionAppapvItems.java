package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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
    public static FixedExtractionAppapvItems create(int checkItemAppapv,
                                                    int alarmCheckCls,
                                                    boolean boldAtr,
                                                    String appapvCheckName,
                                                    String firstMessageDisp,
                                                    String messageColor) {

        return new FixedExtractionAppapvItems(EnumAdaptor.valueOf(checkItemAppapv, CheckItemAppapv.class),
                EnumAdaptor.valueOf(alarmCheckCls, AlarmCheckClassification.class),
                boldAtr,
                appapvCheckName,
                new DisplayMessage(firstMessageDisp),
                messageColor != null ? Optional.of(new ColorCode(messageColor)) : Optional.empty());
    }
}
