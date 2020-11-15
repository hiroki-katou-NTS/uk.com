package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * AggregateRoot: アラームリスト（職場）月次の固定抽出条件
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionMonthlyCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private FixedCheckMonthlyItemName fixedCheckMonthlyItemName;

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
     * @param errorAlarmWorkplaceId     職場のエラーアラームチェックID
     * @param fixedCheckMonthlyItemName No
     * @param useAtr                    使用区分
     * @param messageDisp               表示するメッセージ
     */
    public static FixedExtractionMonthlyCon create(String errorAlarmWorkplaceId,
                                                   int fixedCheckMonthlyItemName,
                                                   boolean useAtr,
                                                   String messageDisp) {

        return new FixedExtractionMonthlyCon(errorAlarmWorkplaceId,
                EnumAdaptor.valueOf(fixedCheckMonthlyItemName, FixedCheckMonthlyItemName.class),
                useAtr,
                new DisplayMessage(messageDisp));
    }
}
