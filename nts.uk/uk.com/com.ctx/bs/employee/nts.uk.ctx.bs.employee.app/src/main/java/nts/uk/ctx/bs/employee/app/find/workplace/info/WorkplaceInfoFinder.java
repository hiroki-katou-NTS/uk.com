/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WkpInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceInfoDto;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceInfoFinder.
 */
@Stateless
public class WorkplaceInfoFinder {

	/** The wkp info repo. */
	@Inject
	private WorkplaceInfoRepository wkpInfoRepo;

	/**
	 * Find.
	 *
	 * @param findObj
	 *            the find obj
	 * @return the workplace info dto
	 */
	// find workplace information by companyId & wkpId & historyId
	public WorkplaceInfoDto find(WkpInfoFindObject findObj) {
		String companyId = AppContexts.user().companyId();
		// find
		Optional<WorkplaceInfo> opWkpInfo = wkpInfoRepo.find(companyId, findObj.getWorkplaceId(),
				findObj.getHistoryId());
		// convert to Dto
		if (opWkpInfo.isPresent()) {
			WorkplaceInfoDto dto = new WorkplaceInfoDto();
			opWkpInfo.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}

	/**
	 * Find wkp info by base date.
	 *
	 * @return the list
	 */
	public List<WorkplaceInfoDto> findWkpInfoByBaseDate(GeneralDate baseDate) {
		
		String companyId = AppContexts.user().companyId();

		// find workplace info
		List<WorkplaceInfo> listWkpInfo = wkpInfoRepo.findAll(companyId, baseDate);

		// check null or empty
		if (CollectionUtil.isEmpty(listWkpInfo)) {
			return new ArrayList<>();
		}
		
		// convert to Dto
		return listWkpInfo.stream()
				.map(wplInfo -> {
					WorkplaceInfoDto dto = new WorkplaceInfoDto();
					wplInfo.saveToMemento(dto);
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by wkp id and base date.
	 *
	 * @param findObj the find obj
	 * @return the workplace info dto
	 */
	public WorkplaceInfoDto findByWkpIdAndBaseDate(WkpInfoFindObject findObj) {
		// find
		Optional<WorkplaceInfo> opWkpInfo = wkpInfoRepo.findByWkpId(findObj.getWorkplaceId(), findObj.getBaseDate());
		// convert to Dto
		if (opWkpInfo.isPresent()) {
			WorkplaceInfoDto dto = new WorkplaceInfoDto();
			opWkpInfo.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
}
