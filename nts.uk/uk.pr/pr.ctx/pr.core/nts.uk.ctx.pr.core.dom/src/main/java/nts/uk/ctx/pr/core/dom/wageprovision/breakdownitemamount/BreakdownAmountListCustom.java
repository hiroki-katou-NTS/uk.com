package nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemShortName;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemCode;

@AllArgsConstructor
@Getter
public class BreakdownAmountListCustom {

    /**
     * 内訳項目コード
     */
    private BreakdownItemCode breakdownItemCode;

    /**
     * 内訳項目名称
     */
    private ItemShortName breakdownItemName;

    /**
     * 金額
     */
    private ItemPriceType amount;

    public BreakdownAmountListCustom(String breakdownItemCode, String breakdownItemName, Long amount) {
        this.breakdownItemCode = new BreakdownItemCode(breakdownItemCode);
        this.breakdownItemName = new ItemShortName(breakdownItemName);
        this.amount = new ItemPriceType(amount);
    }


}
