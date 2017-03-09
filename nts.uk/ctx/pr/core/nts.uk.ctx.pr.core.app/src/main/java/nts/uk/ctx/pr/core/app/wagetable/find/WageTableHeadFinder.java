/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.wagetable.find.dto.WageTableHistoryItemDto;
import nts.uk.ctx.pr.core.app.wagetable.find.dto.WageTableItemDto;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;

/**
 * The Class WageTableHeadFinder.
 */
@Stateless
public class WageTableHeadFinder {

	/** The wage table head repo. */
	@Inject
	private WageTableHeadRepository wageTableHeadRepo;

	/** The wage table history repo. */
	@Inject
	private WageTableHistoryRepository wageTableHistoryRepo;

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<WageTableItemDto> findAll(String companyCode) {
		// Get list of unit price.
		List<WageTableHead> headers = this.wageTableHeadRepo.findAll(companyCode);

		// Get list of unit price history.
		List<WageTableHistory> wageTableHistories = this.wageTableHistoryRepo.findAll(companyCode);

		// Group histories by unit code.
		Map<WageTableCode, List<WageTableHistoryItemDto>> historyMap = wageTableHistories.stream()
				.collect(
						Collectors
								.groupingBy(WageTableHistory::getWageTableCode, Collectors.mapping(
										res -> WageTableHistoryItemDto.builder()
												.id(res.getHistoryId())
												.startMonth(res.getApplyRange().getStartMonth().v())
												.endMonth(res.getApplyRange().getEndMonth().v())
												.build(),
										Collectors.toList())));

		// Return
		return headers.stream()
				.map(item -> WageTableItemDto.builder().wageTableCode(item.getCode().v())
						.wageTableName(item.getName().v()).histories(historyMap.get(item.getCode()))
						.build())
				.collect(Collectors.toList());
	}
}
