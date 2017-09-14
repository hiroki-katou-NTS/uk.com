/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.workplace;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace_old.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceRepository;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.bs.employee.pub.workplace.WkpCdNameExport;

/**
 * The Class WorkplacePubImp.
 */
@Stateless
public class WorkplacePubImp implements SyWorkplacePub {

	/** The workplace repository. */
	@Inject
	private WorkplaceRepository workplaceRepository;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.workplace.WorkplacePub#findWpkIds(java.lang.
	 * String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<String> findWpkIdsByWkpCode(String companyId, String wpkCode,
			GeneralDate baseDate) {
		return workplaceRepository.findByWkpCd(companyId, wpkCode, baseDate).stream()
				.map(item -> item.getWorkplaceId().v()).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.workplace.WorkplacePub#findByWkpId(java.lang.
	 * String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<WkpCdNameExport> findByWkpId(String companyId, String workplaceId,
			GeneralDate baseDate) {
		return workplaceRepository.findByWkpId(companyId, workplaceId, baseDate).stream()
				.map(item -> WkpCdNameExport.builder().wkpCode(item.getWorkplaceCode().v())
						.wkpName(item.getWorkplaceName().v()).build())
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.workplace.WorkplacePub#getWorkplaceId(java.
	 * lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		List<String> wkpIds = affWorkplaceHistories.stream().map(item -> item.getWorkplaceId().v())
				.collect(Collectors.toList());

		List<Workplace> acWorkplaceDtos = workplaceRepository.findByWkpIds(wkpIds);

		Map<String, String> comWkpMap = acWorkplaceDtos.stream()
				.collect(Collectors.toMap((item) -> {
					return item.getCompanyId().v();
				}, (item) -> {
					return item.getWorkplaceId().v();
				}));

		// Return workplace id
		return comWkpMap.get(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.workplace.WorkplacePub#findWpkIdsBySid(java.
	 * lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<String> findWpkIdsBySid(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// Return
		return affWorkplaceHistories.stream().map(item -> {
			return item.getWorkplaceId().v();
		}).collect(Collectors.toList());
	}
}
