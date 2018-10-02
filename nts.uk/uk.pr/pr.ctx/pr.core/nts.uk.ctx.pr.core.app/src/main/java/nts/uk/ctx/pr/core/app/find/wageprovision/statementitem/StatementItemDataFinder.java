package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.ItemRangeSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.Abolition;

@Stateless
public class StatementItemDataFinder {
	@Inject
	private StatementItemRepository statementItemRepository;
	@Inject
	private StatementItemNameRepository statementItemNameRepository;
	@Inject
	private PaymentItemSetRepository paymentItemSetRepository;
	@Inject
	private DeductionItemSetRepository deductionItemSetRepository;
	@Inject
	private TimeItemSetRepository timeItemSetRepository;
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
	 * @param カテゴリ区分
	 *            categoryAtr
	 * @param 項目名コード
	 *            itemNameCd
	 * @param 給与項目ID
	 *            salaryItemId
	 * @param 内訳項目コード
	 *            breakdownItemCode
	 * @param 非課税限度額コード
	 *            taxFreeAmountCode
	 * @return
	 */
	public StatementItemDataDto getStatementItemData(int categoryAtr, String itemNameCd, String salaryItemId) {
		StatementItemDto statementItem = null;
		StatementItemNameDto statementItemName = null;
		PaymentItemSetDto paymentItemSet = null;
		DeductionItemSetDto deductionItemSet = null;
		TimeItemSetDto timeItemSet = null;
		StatementItemDisplaySetDto statementDisplaySet = null;
		ItemRangeSetDto itemRangeSet = null;
		ValidityPeriodAndCycleSetDto validityPeriodAndCycleSet = null;
		List<BreakdownItemSetDto> breakdownItemSet = null;
		IntegratedItemDto integratedItem = null; // TODO Chưa tạo domain
		String cid = AppContexts.user().companyId();
		String name = null;
		Integer deprecatedAtr = null;
		statementItem = statementItemRepository.getStatementItemById(cid, categoryAtr, itemNameCd, salaryItemId)
				.map(i -> StatementItemDto.fromDomain(i)).orElse(null);
		if (statementItem != null) {
			deprecatedAtr = statementItem.getDeprecatedAtr();
		}

		statementItemName = statementItemNameRepository.getStatementItemNameById(cid, salaryItemId)
				.map(i -> StatementItemNameDto.fromDomain(i)).orElse(null);
		if (statementItemName != null) {
			name = statementItemName.getName();
		}

		integratedItem = null; // TODO Chưa tạo domain

		switch (EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class)) {
		case PAYMENT_ITEM:
			statementDisplaySet = statementItemDisplaySetRepository.getSpecItemDispSetById(cid, salaryItemId)
					.map(i -> StatementItemDisplaySetDto.fromDomain(i)).orElse(null);
			itemRangeSet = itemRangeSetRepository.getItemRangeSetInitById(cid, salaryItemId)
					.map(i -> ItemRangeSetDto.fromDomain(i)).orElse(null);
			validityPeriodAndCycleSet = setPeriodCycleRepository.getSetPeriodCycleById(salaryItemId)
					.map(i -> ValidityPeriodAndCycleSetDto.fromDomain(i)).orElse(null);
			breakdownItemSet = breakdownItemSetRepository.getBreakdownItemStBySalaryId(salaryItemId).stream().map(i -> {
				return BreakdownItemSetDto.fromDomain(i);
			}).collect(Collectors.toList());
			val paymentItemOpt = paymentItemSetRepository.getPaymentItemStById(cid, salaryItemId);
			if (paymentItemOpt.isPresent()) {
				val taxLimitAmountCode = paymentItemOpt.get().getLimitAmountSetting().getTaxLimitAmountCode()
						.map(i -> i.v()).orElse(null);
				val taxExemptOpt = taxExemptionLimitRepository.getTaxExemptLimitById(cid, taxLimitAmountCode);
				paymentItemSet = paymentItemOpt.map(i -> PaymentItemSetDto.fromDomain(i, taxExemptOpt)).orElse(null);
			}

			break;

		case DEDUCTION_ITEM:
			deductionItemSet = deductionItemSetRepository.getDeductionItemStById(cid, salaryItemId)
					.map(i -> DeductionItemSetDto.fromDomain(i)).orElse(null);
			statementDisplaySet = statementItemDisplaySetRepository.getSpecItemDispSetById(cid, salaryItemId)
					.map(i -> StatementItemDisplaySetDto.fromDomain(i)).orElse(null);
			itemRangeSet = itemRangeSetRepository.getItemRangeSetInitById(cid, salaryItemId)
					.map(i -> ItemRangeSetDto.fromDomain(i)).orElse(null);
			validityPeriodAndCycleSet = setPeriodCycleRepository.getSetPeriodCycleById(salaryItemId)
					.map(i -> ValidityPeriodAndCycleSetDto.fromDomain(i)).orElse(null);
			breakdownItemSet = breakdownItemSetRepository.getBreakdownItemStBySalaryId(salaryItemId).stream().map(i -> {
				return BreakdownItemSetDto.fromDomain(i);
			}).collect(Collectors.toList());
			break;

		case ATTEND_ITEM:
			timeItemSet = timeItemSetRepository.getTimeItemStById(cid, salaryItemId)
					.map(i -> TimeItemSetDto.fromDomain(i)).orElse(null);
			statementDisplaySet = statementItemDisplaySetRepository.getSpecItemDispSetById(cid, salaryItemId)
					.map(i -> StatementItemDisplaySetDto.fromDomain(i)).orElse(null);
			itemRangeSet = itemRangeSetRepository.getItemRangeSetInitById(cid, salaryItemId)
					.map(i -> ItemRangeSetDto.fromDomain(i)).orElse(null);
			break;

		case REPORT_ITEM:
			statementDisplaySet = statementItemDisplaySetRepository.getSpecItemDispSetById(cid, salaryItemId)
					.map(i -> StatementItemDisplaySetDto.fromDomain(i)).orElse(null);
			break;

		case OTHER_ITEM:
			break;

		}

		return new StatementItemDataDto(statementItem, statementItemName, paymentItemSet, deductionItemSet, timeItemSet,
				statementDisplaySet, itemRangeSet, validityPeriodAndCycleSet, breakdownItemSet, integratedItem,
				salaryItemId, categoryAtr, itemNameCd, name, deprecatedAtr);
	}

	public List<StatementItemDataDto> getAllStatementItemData(Integer categoryAtr, boolean isIncludeDeprecated) {
		String cid = AppContexts.user().companyId();
		boolean isEnumValue = EnumAdaptor.convertToValueNameList(CategoryAtr.class).stream()
				.anyMatch(c -> c.getValue() == categoryAtr);
		List<StatementItem> listStatementItem = isEnumValue ? statementItemRepository.getByCategory(cid, categoryAtr)
				: statementItemRepository.getAllItemByCid(cid);

		List<StatementItemDataDto> result = new ArrayList<StatementItemDataDto>();

		for (StatementItem item : listStatementItem) {
			if (!isIncludeDeprecated && item.getDeprecatedAtr() == Abolition.ABOLISH) {
				continue;
			}
			val data = this.getStatementItemData(item.getCategoryAtr().value, item.getItemNameCd().v(),
					item.getSalaryItemId());
			if (data != null) {
				result.add(data);
			}
		}
		return result;
	}
}
