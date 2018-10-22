package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import java.util.List;

import lombok.Value;

@Value
public class StatementItemDataCommand {
	private StatementItemCommand statementItem;
	private StatementItemNameCommand statementItemName;
	private PaymentItemSetCommand paymentItemSet;
	private DeductionItemSetCommand deductionItemSet;
	private TimeItemSetCommand timeItemSet;
	private StatementItemDisplaySetCommand statementItemDisplaySet;
	private ItemRangeSetCommand itemRangeSet;
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
}
