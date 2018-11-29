package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeCountAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WageTableFinder {

	@Inject
	private WageTableService wageTableService;

	@Inject
	private WageTableRepository wageTableRepo;

	@Inject
	private WageTableHistoryRepository wageTableHistRepo;

	@Inject
	private StatementItemRepository statementRepo;

	@Inject
	private TimeItemSetRepository timeItemRepo;

	public List<WageTableDto> getAll() {
		String companyId = AppContexts.user().companyId();
		List<WageTable> lstWage = wageTableRepo.getAllWageTable(companyId);
		List<String> codes = lstWage.stream().map(i -> i.getWageTableCode().v()).collect(Collectors.toList());
		List<WageTableHistory> lstHist = wageTableHistRepo.getWageTableHistByCodes(companyId, codes);
		return lstWage.stream()
				.map(i -> WageTableDto.fromDomainToDto(i, lstHist.stream()
						.filter(e -> e.getWageTableCode().v().equals(i.getWageTableCode().v())).findFirst()))
				.collect(Collectors.toList());
	}

	public List<WageTableDto> getWageTableByYearMonth(int yearMonth) {
		return wageTableService.getFormulaByYearMonth(new YearMonth(yearMonth)).stream()
				.map(WageTableDto::fromDomainToDto).collect(Collectors.toList());
	}

	public WageTableDto getWageTableById(String wageTableCode) {
		String cid = AppContexts.user().companyId();
		Optional<WageTable> domainOtp = wageTableRepo.getWageTableById(cid, wageTableCode);
		Optional<WageTableHistory> optHistory = wageTableHistRepo.getWageTableHistByCode(cid, wageTableCode);
		if (domainOtp.isPresent()) {
			WageTableDto dto = WageTableDto.fromDomainToDto(domainOtp.get(), optHistory);
			if (dto.getElementInformation().getOneDimensionalElement().getOptionalAdditionalElement() != null
					|| dto.getElementInformation().getTwoDimensionalElement().getOptionalAdditionalElement() != null
					|| dto.getElementInformation().getThreeDimensionalElement()
							.getOptionalAdditionalElement() != null) {
				List<StatementItemCustom> lstStatementItem = statementRepo.getItemCustomByCategoryAndDeprecated(cid,
						CategoryAtr.ATTEND_ITEM.value, false);
				if (dto.getElementInformation().getOneDimensionalElement().getOptionalAdditionalElement() != null) {
					Optional<StatementItemCustom> optElem = lstStatementItem.stream()
							.filter(i -> i.getItemNameCd().equals(dto.getElementInformation().getOneDimensionalElement()
									.getOptionalAdditionalElement()))
							.findFirst();
					dto.getElementInformation().getOneDimensionalElement()
							.setDisplayName(optElem.isPresent() ? optElem.get().getName() : null);
				}
				if (dto.getElementInformation().getTwoDimensionalElement().getOptionalAdditionalElement() != null) {
					Optional<StatementItemCustom> optElem = lstStatementItem.stream()
							.filter(i -> i.getItemNameCd().equals(dto.getElementInformation().getTwoDimensionalElement()
									.getOptionalAdditionalElement()))
							.findFirst();
					dto.getElementInformation().getTwoDimensionalElement()
							.setDisplayName(optElem.isPresent() ? optElem.get().getName() : null);
				}
				if (dto.getElementInformation().getThreeDimensionalElement().getOptionalAdditionalElement() != null) {
					Optional<StatementItemCustom> optElem = lstStatementItem.stream()
							.filter(i -> i.getItemNameCd().equals(dto.getElementInformation()
									.getThreeDimensionalElement().getOptionalAdditionalElement()))
							.findFirst();
					dto.getElementInformation().getThreeDimensionalElement()
							.setDisplayName(optElem.isPresent() ? optElem.get().getName() : null);
				}
			}
			if (dto.getElementInformation().getOneDimensionalElement().getFixedElement() != null) {
				dto.getElementInformation().getOneDimensionalElement()
						.setDisplayName(Arrays.stream(ElementType.values())
								.filter(item -> item.value.equals(
										dto.getElementInformation().getOneDimensionalElement().getFixedElement()))
								.findFirst().get().nameId);
			}
			if (dto.getElementInformation().getTwoDimensionalElement().getFixedElement() != null) {
				dto.getElementInformation().getTwoDimensionalElement()
						.setDisplayName(Arrays.stream(ElementType.values())
								.filter(item -> item.value.equals(
										dto.getElementInformation().getTwoDimensionalElement().getFixedElement()))
								.findFirst().get().nameId);
			}
			if (dto.getElementInformation().getThreeDimensionalElement().getFixedElement() != null) {
				dto.getElementInformation().getThreeDimensionalElement()
						.setDisplayName(Arrays.stream(ElementType.values())
								.filter(item -> item.value.equals(
										dto.getElementInformation().getThreeDimensionalElement().getFixedElement()))
								.findFirst().get().nameId);
			}
			return dto;
		} else
			return null;
	}

	public List<ElementItemNameDto> getElements() {
		String companyId = AppContexts.user().companyId();
		List<StatementItemCustom> lstStatementItem = statementRepo.getItemCustomByCategoryAndDeprecated(companyId,
				CategoryAtr.ATTEND_ITEM.value, false);
		List<TimeItemSet> lstTimeItem = timeItemRepo.getTimeItemStByCategoryAndCodes(companyId,
				TimeCountAtr.TIMES.value,
				lstStatementItem.stream().map(i -> i.getItemNameCd()).collect(Collectors.toList()));
		List<String> lsItemNameCode = lstTimeItem.stream().map(i -> i.getItemNameCode().v())
				.collect(Collectors.toList());
		lstStatementItem = lstStatementItem.stream().filter(i -> lsItemNameCode.contains(i.getItemNameCd()))
				.collect(Collectors.toList());
		return lstStatementItem.stream().map(i -> new ElementItemNameDto(i.getItemNameCd(), i.getName()))
				.collect(Collectors.toList());
	}

	public List<NumericElementItemDto> createOneDimensionWageTable(ElementRangeParam params) {
		List<NumericElementItemDto> result = new ArrayList<>();
		if (params == null) {
			// master item
			return result;
		} else {
			// numeric item
			while (params.getRangeLowerLimit() + params.getStepIncrement() < params.getRangeUpperLimit()) {
				result.add(new NumericElementItemDto(null, params.getRangeLowerLimit(),
						params.getRangeLowerLimit() + params.getStepIncrement() - 1));
				params.setRangeLowerLimit(params.getRangeLowerLimit() + params.getStepIncrement());
			}
			result.add(new NumericElementItemDto(null, params.getRangeLowerLimit(), params.getRangeUpperLimit()));
			return result;
		}
	}

}
