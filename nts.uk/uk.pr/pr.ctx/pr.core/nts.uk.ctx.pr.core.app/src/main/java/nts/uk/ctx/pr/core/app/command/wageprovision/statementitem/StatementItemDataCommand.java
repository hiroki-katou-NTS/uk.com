package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;

import java.util.List;

@Value
public class StatementItemDataCommand {
	private StatementItemCommand statementItem;
	private StatementItemNameCommand statementItemName;
	private PaymentItemSetCommand paymentItemSet;
	private DeductionItemSetCommand deductionItemSet;
	private TimeItemSetCommand timeItemSet;
	private StatementItemDisplaySetCommand statementItemDisplaySet;
	private ValidityPeriodAndCycleSetCommand validityPeriodAndCycleSet;
	private List<BreakdownItemSetCommand> breakdownItemSet;
	private TaxExemptionLimitCommand taxExemptionLimit;
	private IntegratedItemCommand integratedItem;
	private boolean checkCreate;
	
	/**
	 * カテゴリ区分
	 */
	private int categoryAtr;

	/**
	 * 項目名コード
	 */
	private String itemNameCd;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 廃止区分
	 */
	private Integer deprecatedAtr;

    public PaymentItemSet toPaymentItemSet(String cid) {
        return paymentItemSet.toDomain(cid, this.categoryAtr, statementItem.getItemNameCd());
    }

    public DeductionItemSet toDeductionItemSet(String cid) {
        return deductionItemSet.toDomain(cid, this.categoryAtr, statementItem.getItemNameCd());
    }

    public TimeItemSet toTimeItemSet(String cid) {
        return timeItemSet.toDomain(cid, this.categoryAtr, statementItem.getItemNameCd());
    }
}
