package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

import java.util.Optional;

/**
 * AggregateRoot: アラームリスト（職場）日別の固定抽出項目
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionDayItems extends AggregateRoot {
    /**
     * No
     */
    private FixedCheckDayItems fixedCheckDayItems;

    /**
     * アラームチェック区分
     */
    private AlarmCheckClassification alarmCheckCls;

    /**
     * メッセージを太字にする
     */
    private boolean boldAtr;

    /**
     * 日別チェック名称
     */
    private String dailyCheckName;

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
     * @param fixedCheckDayItems No
     * @param alarmCheckCls      アラームチェック区分
     * @param boldAtr            メッセージを太字にする
     * @param dailyCheckName     日別チェック名称
     * @param firstMessageDisp   最初表示するメッセージ
     * @param messageColor       メッセージの色
     */
    public static FixedExtractionDayItems create(int fixedCheckDayItems,
                                                 int alarmCheckCls,
                                                 boolean boldAtr,
                                                 String dailyCheckName,
                                                 String firstMessageDisp,
                                                 String messageColor) {

        return new FixedExtractionDayItems(EnumAdaptor.valueOf(fixedCheckDayItems, FixedCheckDayItems.class),
                EnumAdaptor.valueOf(alarmCheckCls, AlarmCheckClassification.class),
                boldAtr,
                dailyCheckName,
                new DisplayMessage(firstMessageDisp),
                messageColor != null ? Optional.of(new ColorCode(messageColor)) : Optional.empty());
    }
}
