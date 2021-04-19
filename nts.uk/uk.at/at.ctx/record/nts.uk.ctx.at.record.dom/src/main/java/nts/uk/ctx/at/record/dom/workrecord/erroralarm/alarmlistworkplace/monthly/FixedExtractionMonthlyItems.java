package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

import java.util.Optional;

/**
 * AggregateRoot: アラームリスト（職場）月次の固定抽出項目
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionMonthlyItems extends AggregateRoot {
    /**
     * No
     */
    private FixedCheckMonthlyItemName no;

    /**
     * アラームチェック区分
     */
    private AlarmCheckClassification alarmCheckCls;

    /**
     * 月次チェック名称
     */
    private String monthlyCheckName;

    /**
     * メッセージを太字にする
     */
    private boolean boldAtr;

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
     * @param no               No
     * @param alarmCheckCls    アラームチェック区分
     * @param monthlyCheckName 月次チェック名称
     * @param boldAtr                   メッセージを太字にする
     * @param firstMessageDisp 最初表示するメッセージ
     * @param messageColor     メッセージの色
     */
    public static FixedExtractionMonthlyItems create(int no,
                                                     int alarmCheckCls,
                                                     String monthlyCheckName,
                                                     boolean boldAtr,
                                                     String firstMessageDisp,
                                                     String messageColor) {

        return new FixedExtractionMonthlyItems(EnumAdaptor.valueOf(no, FixedCheckMonthlyItemName.class),
                EnumAdaptor.valueOf(alarmCheckCls, AlarmCheckClassification.class),
                monthlyCheckName,
                boldAtr,
                new DisplayMessage(firstMessageDisp),
                messageColor != null ? Optional.of(new ColorCode(messageColor)) : Optional.empty());
    }
}
