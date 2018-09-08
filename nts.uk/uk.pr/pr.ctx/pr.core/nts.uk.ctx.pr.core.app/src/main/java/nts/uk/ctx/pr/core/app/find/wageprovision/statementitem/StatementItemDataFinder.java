package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.ItemRangeSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StatementItemDataFinder {
	@Inject
	private StatementItemRepository statementItemRepository;
	@Inject
	private StatementItemNameRepository statementItemNameRepository;
	@Inject
	private PaymentItemSetRepository paymentItemSetRepository;
	@Inject
	private StatementItemDisplaySetRepository statementItemDisplaySetRepository;
	@Inject
	private ItemRangeSetRepository itemRangeSetRepository;
	@Inject
	private SetPeriodCycleRepository setPeriodCycleRepository;
	@Inject
	private BreakdownItemSetRepository breakdownItemSetRepository;
	@Inject
	private TaxExemptionLimitRepository taxExemptionLimitRepository;

	/**
	 * アルゴリズム「選択処理」
	 * 
	 * @param カテゴリ区分 categoryAtr
	 * @param 項目名コード itemNameCd
	 * @param 給与項目ID salaryItemId
	 * @param 内訳項目コード breakdownItemCode
	 * @param 非課税限度額コード taxFreeAmountCode
	 * @return
	 */
	public StatementItemDataDto getStatementItemData(int categoryAtr, int itemNameCd, String salaryItemId,
			Integer breakdownItemCode, String taxFreeAmountCode) {
		StatementItemDto statementItem = null;
		StatementItemNameDto statementItemName = null;
		PaymentItemSetDto paymentItemSet = null;
		StatementItemDisplaySetDto statementDisplaySet = null;
		ItemRangeSetDto itemRangeSet = null;
		ValidityPeriodAndCycleSetDto validityPeriodAndCycleSet = null;
		BreakdownItemSetDto breakdownItemSet = null;
		TaxExemptionLimitDto taxExemptionLimit = null;
		IntegratedItemDto integratedItem = null; // TODO Chưa tạo domain
		String cid = AppContexts.user().companyId();

		switch (EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class)) {
		case PAYMENT_ITEM:
			taxExemptionLimit = taxExemptionLimitRepository.getTaxExemptLimitById(cid, taxFreeAmountCode)
					.map(i -> TaxExemptionLimitDto.fromDomain(i)).orElse(null);
		case DEDUCTION_ITEM:
			validityPeriodAndCycleSet = setPeriodCycleRepository.getSetPeriodCycleById(salaryItemId)
					.map(i -> ValidityPeriodAndCycleSetDto.fromDomain(i)).orElse(null);
			breakdownItemSet = breakdownItemSetRepository.getBreakdownItemStById(salaryItemId, breakdownItemCode)
					.map(i -> BreakdownItemSetDto.fromDomain(i)).orElse(null);
		case ATTEND_ITEM:
			paymentItemSet = paymentItemSetRepository.getPaymentItemStById(cid, salaryItemId)
					.map(i -> PaymentItemSetDto.fromDomain(i)).orElse(null);
			itemRangeSet = itemRangeSetRepository.getItemRangeSetInitById(cid, salaryItemId)
					.map(i -> ItemRangeSetDto.fromDomain(i)).orElse(null);
		case REPORT_ITEM:
			statementDisplaySet = statementItemDisplaySetRepository.getSpecItemDispSetById(cid, salaryItemId)
					.map(i -> StatementItemDisplaySetDto.fromDomain(i)).orElse(null);
		case OTHER_ITEM:
			statementItem = statementItemRepository.getStatementItemById(cid, categoryAtr, itemNameCd, salaryItemId)
					.map(i -> StatementItemDto.fromDomain(i)).orElse(null);
			statementItemName = statementItemNameRepository.getStatementItemNameById(cid, salaryItemId)
					.map(i -> StatementItemNameDto.fromDomain(i)).orElse(null);
			integratedItem = null; // TODO Chưa tạo domain
		}

		return new StatementItemDataDto(statementItem, statementItemName, paymentItemSet, statementDisplaySet,
				itemRangeSet, validityPeriodAndCycleSet, breakdownItemSet, taxExemptionLimit, integratedItem);
	}
}
