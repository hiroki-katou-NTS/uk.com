/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.info;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.Kcp010WorkplaceSearchData;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceInfoFinder.
 */
@Stateless
public class Kcp010Finder {

	/** The wkp info repo. */
	@Inject
	private WorkplaceInfoRepository wkpInfoRepo;
	
	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepo;

	/**
	 * Find wkp info by workplaceCode
	 *
	 * @return the list
	 */
	public Optional<Kcp010WorkplaceSearchData> searchByWorkplaceCode(String workplaceCode, GeneralDate baseDate) {
		
		// find workplace info
		List<WorkplaceInfo> listWkpInfo = 
				wkpInfoRepo.findByWkpCd(AppContexts.user().companyId(), workplaceCode, baseDate);

		// check null or empty
		if (CollectionUtil.isEmpty(listWkpInfo)) {
			throw new BusinessException("Msg_7");
		}
		WorkplaceInfo wkpInfo = listWkpInfo.get(0);
		
		return Optional.of(Kcp010WorkplaceSearchData.builder()
				.workplaceId(wkpInfo.getWorkplaceId())
				.workplaceCode(wkpInfo.getWorkplaceCode().v())
				.workplaceName(wkpInfo.getWkpDisplayName().v())
				.build());
	}
	
	public Optional<Kcp010WorkplaceSearchData> findBySid(String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepo
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(affWorkplaceHistories)) {
			return Optional.empty();
		}

		AffWorkplaceHistory affWorkplaceHistory = affWorkplaceHistories.get(0);

		String wkpId = affWorkplaceHistory.getWorkplaceId().v();

		// Get workplace info.
		Optional<WorkplaceInfo> optWorkplaceInfo = wkpInfoRepo.findByWkpId(wkpId, baseDate);

		// Check exist
		if (!optWorkplaceInfo.isPresent()) {
			return Optional.empty();
		}

		// Return workplace id
		WorkplaceInfo wkpInfo = optWorkplaceInfo.get();
		return Optional.of(Kcp010WorkplaceSearchData.builder()
				.workplaceId(wkpInfo.getWorkplaceId()).workplaceCode(wkpInfo.getWorkplaceCode().v())
				.workplaceName(wkpInfo.getWorkplaceName().v())
				.workplaceName(wkpInfo.getWkpDisplayName().v()).build());
	}
}
