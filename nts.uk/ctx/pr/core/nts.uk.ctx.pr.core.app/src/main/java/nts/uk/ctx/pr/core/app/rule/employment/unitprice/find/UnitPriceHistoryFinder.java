/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;

/**
 * The Class UnitPriceHistoryFinder.
 */
@Stateless
public class UnitPriceHistoryFinder {

	/** The repository. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepo;

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the optional
	 */
	public Optional<UnitPriceHistoryDto> find(CompanyCode companyCode, UnitPriceCode cUnitpriceCd, String id) {
		Optional<UnitPriceHistory> unitPriceHistory = unitPriceHistoryRepo.findById(companyCode, cUnitpriceCd, id);
		UnitPriceHistoryDto dto = UnitPriceHistoryDto.builder().build();
		if (unitPriceHistory.isPresent()) {
			unitPriceHistory.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<UnitPriceHistoryDto> findAll(CompanyCode companyCode) {
		return unitPriceHistoryRepo.findAll(companyCode).stream().map(domain -> {
			UnitPriceHistoryDto dto = UnitPriceHistoryDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
