package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.BreakdownItemUseAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.DetailAmountErrorAlarmRangeSetting;
import nts.uk.shr.com.primitive.Memo;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 控除項目設定
 */
@Getter
public class DeductionItemSet extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * カテゴリ区分
     */
    private CategoryAtr categoryAtr;

    /**
     * 項目名コード
     */
    private ItemNameCode itemNameCode;

    /**
     * 控除項目区分
     */
    private DeductionItemAtr deductionItemAtr;

    /**
     * 内訳項目利用区分
     */
    private BreakdownItemUseAtr breakdownItemUseAtr;

    /**
     * 備考
     */
    private Optional<Memo> note;

    /**
     * 控除エラー範囲設定
     */
    private DetailAmountErrorAlarmRangeSetting errorRangeSetting;

    /**
     * 控除アラーム範囲設定
     */
    private DetailAmountErrorAlarmRangeSetting alarmRangeSetting;

    public DeductionItemSet(String cid, int categoryAtr, String itemNameCode,
                            int deductionItemAtr, int breakdownItemUseAtr,
                            int errorUpperLimitSetAtr, BigDecimal errorUpRangeVal,
                            int errorLowerLimitSetAtr, BigDecimal errorLoRangeVal,
                            int alarmUpperLimitSetAtr, BigDecimal alarmUpRangeVal,
                            int alarmLowerLimitSetAtr, BigDecimal alarmLoRangeVal,
                            String note) {
        super();
        this.cid = cid;
        this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
        this.itemNameCode = new ItemNameCode(itemNameCode);
        this.deductionItemAtr = EnumAdaptor.valueOf(deductionItemAtr, DeductionItemAtr.class);
        this.breakdownItemUseAtr = EnumAdaptor.valueOf(breakdownItemUseAtr, BreakdownItemUseAtr.class);
        this.errorRangeSetting = new DetailAmountErrorAlarmRangeSetting(errorUpperLimitSetAtr, errorUpRangeVal,
                errorLowerLimitSetAtr, errorLoRangeVal);
        this.alarmRangeSetting = new DetailAmountErrorAlarmRangeSetting(alarmUpperLimitSetAtr, alarmUpRangeVal,
                alarmLowerLimitSetAtr, alarmLoRangeVal);
        this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
    }

}
