/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.find;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;

/**
 * The Class UnitPriceHistoryFinder.
 */
@Stateless
public class UnitPriceHistoryFinder {

	@Inject
	private UnitPriceRepository unitPriceRepo;

	/** The unit price history repo. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepo;

	/**
	 * Find.
	 *
	 * @param companyCode
	 *            the company code
	 * @param cUnitpriceCd
	 *            the c unitprice cd
	 * @param id
	 *            the id
	 * @return the optional
	 */
	public Optional<UnitPriceHistoryDto> find(CompanyCode companyCode, String id) {
		// Get the detail history.
		Optional<UnitPriceHistory> optUnitPriceHistory = unitPriceHistoryRepo.findById(companyCode, id);
		UnitPriceHistoryDto dto = null;

		// Check exsit.
		if (optUnitPriceHistory.isPresent()) {
			UnitPriceHistory unitPrisceHistory = optUnitPriceHistory.get();
			Optional<UnitPrice> optUnitPrice = unitPriceRepo.findByCode(companyCode,
					unitPrisceHistory.getUnitPriceCode());
			dto = UnitPriceHistoryDto.builder().build();
			unitPrisceHistory.saveToMemento(dto);
			dto.setUnitPriceName(optUnitPrice.get().getName());
		}

		// Return
		return Optional.ofNullable(dto);
	}

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<UnitPriceItemDto> findAll(CompanyCode companyCode) {
		// Get list of unit price.
		List<UnitPrice> unitPrices = this.unitPriceRepo.findAll(companyCode);

		// Get list of unit price history.
		List<UnitPriceHistory> unitPriceHistories = this.unitPriceHistoryRepo.findAll(companyCode);

		// Group histories by unit code.
		Map<UnitPriceCode, List<UnitPriceHistoryItemDto>> historyMap = unitPriceHistories.stream()
				.collect(Collectors.groupingBy(UnitPriceHistory::getUnitPriceCode, Collectors.mapping((res) -> {
					return new UnitPriceHistoryItemDto(res.getId(), res.getApplyRange().getStartMonth().v(),
							res.getApplyRange().getEndMonth().v());
				}, Collectors.toList())));

		// Return
		return unitPrices.stream().map(item -> {
			List<UnitPriceHistoryItemDto> histories = ListUtil.isEmpty(historyMap.get(item.getCode()))
					? Collections.emptyList() : historyMap.get(item.getCode());
			Collections.reverse(histories);
			return new UnitPriceItemDto(item.getCode().v(), item.getName().v(), histories);
		}).collect(Collectors.toList());
	}
}
