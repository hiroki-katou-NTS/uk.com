package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.BreakdownItemSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.DeductionItemSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.IntegratedItemDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.ItemRangeSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.PaymentItemSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemDisplaySetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.TaxExemptionLimitDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.TimeItemSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.ValidityPeriodAndCycleSetDto;

@Value
public class StatementItemDataCommand {
	private StatementItemDto statementItem;
	private StatementItemNameDto statementItemName;
	private PaymentItemSetDto paymentItemSet;
	private DeductionItemSetDto deductionItemSet;
	private TimeItemSetDto timeItemSet;
	private StatementItemDisplaySetDto statementDisplaySet;
	private ItemRangeSetDto itemRangeSet;
	private ValidityPeriodAndCycleSetDto validityPeriodAndCycleSet;
	private BreakdownItemSetDto breakdownItemSet;
	private TaxExemptionLimitDto taxExemptionLimit;
	private IntegratedItemDto integratedItem;
	private boolean checkCreate;
}
