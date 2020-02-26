package nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemCode;

/**
 * 内訳金額一覧
 */
@AllArgsConstructor
@Getter
public class BreakdownAmountList extends DomainObject {

    /**
     * 内訳項目コード
     */
    private BreakdownItemCode breakdownItemCode;

    /**
     * 金額
     */
    private ItemPriceType amount;

    /**
     * @param breakdownItemCode 内訳項目コード
     * @param amount            金額
     */
    public BreakdownAmountList(String breakdownItemCode, long amount) {
        this.breakdownItemCode = new BreakdownItemCode(breakdownItemCode);
        this.amount = new ItemPriceType(amount);
    }

}
