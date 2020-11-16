package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.FixedCheckMonthlyItemName;
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
    private FixedCheckMonthlyItemName fixedCheckMonthlyItemName;

    /**
     * アラームチェック区分
     */
    private AlarmCheckClassification alarmCheckCls;

    /**
     * 月次チェック名称
     */
    private String monthlyCheckName;

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
     * @param fixedCheckMonthlyItemName No
     * @param alarmCheckCls             アラームチェック区分
     * @param monthlyCheckName          月次チェック名称
     * @param firstMessageDisp          最初表示するメッセージ
     * @param messageColor              メッセージの色
     */
    public static FixedExtractionMonthlyItems create(int fixedCheckMonthlyItemName,
                                                     int alarmCheckCls,
                                                     String monthlyCheckName,
                                                     String firstMessageDisp,
                                                     String messageColor) {

        return new FixedExtractionMonthlyItems(EnumAdaptor.valueOf(fixedCheckMonthlyItemName, FixedCheckMonthlyItemName.class),
                EnumAdaptor.valueOf(alarmCheckCls, AlarmCheckClassification.class),
                monthlyCheckName,
                new DisplayMessage(firstMessageDisp),
                messageColor != null ? Optional.of(new ColorCode(messageColor)) : Optional.empty());
    }
}
