package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import java.util.List;

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
	private List<BreakdownItemSetDto> breakdownItemSet;
	private TaxExemptionLimitDto taxExemptionLimit;
	private IntegratedItemDto integratedItem;
	private boolean checkCreate;
	/**
	 * 給与項目ID
	 */
	private String salaryItemId;
	
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
