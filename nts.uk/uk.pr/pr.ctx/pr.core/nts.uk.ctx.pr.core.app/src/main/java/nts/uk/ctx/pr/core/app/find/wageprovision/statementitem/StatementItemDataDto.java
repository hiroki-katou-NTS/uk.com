package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;

@Value
public class StatementItemDataDto {
	private StatementItemDto statementItem;
	private StatementItemNameDto statementItemName;
	private PaymentItemSetDto paymentItemSet;
	private StatementItemDisplaySetDto statementDisplaySet;
	private ItemRangeSetDto itemRangeSet;
	private ValidityPeriodAndCycleSetDto validityPeriodAndCycleSet;
	private BreakdownItemSetDto breakdownItemSet;
	private TaxExemptionLimitDto taxExemptionLimit;
	private IntegratedItemDto integratedItem;
}
