/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.Kcp010WorkplaceSearchData;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
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
	
	/**AffWorkplaceHistoryItemRepository*/
	@Inject
	private AffWorkplaceHistoryItemRepository workplaceHistoryItemRepository;
	
	@Inject
	private WorkplaceInformationRepository workplaceInforRepo;
	
	@Inject
	private WorkplaceExportService workplaceExportService;

	/**
	 * Find wkp info by workplaceCode
	 *
	 * @return the list
	 */
	public Optional<Kcp010WorkplaceSearchData> searchByWorkplaceCode(String workplaceCode, GeneralDate baseDate) {
		
		// find workplace info
//		List<WorkplaceInfo> listWkpInfo = 
//				wkpInfoRepo.findByWkpCd(AppContexts.user().companyId(), workplaceCode, baseDate);
		
		// [No.575]職場コードから職場IDを取得する
		Optional<String> workplaceId = workplaceInforRepo.getWkpNewByCdDate(AppContexts.user().companyId(), workplaceCode, baseDate)
				.map(c -> c.getWorkplaceId());
	
		if (!workplaceId.isPresent()) {
			throw new BusinessException("Msg_7");
		}
		
		// [No.560]職場IDから職場の情報をすべて取得する
		List<String> workplaceIds = new ArrayList<>();
		workplaceIds.add(workplaceId.get());
		List<WorkplaceInforParam> listWkpInfo = workplaceExportService.getWorkplaceInforFromWkpIds(AppContexts.user().companyId(), workplaceIds, baseDate);

		// check null or empty
		if (CollectionUtil.isEmpty(listWkpInfo)) {
			throw new BusinessException("Msg_7");
		}
		WorkplaceInforParam wkpInfo = listWkpInfo.get(0);
		
		return Optional.of(Kcp010WorkplaceSearchData.builder()
				.workplaceId(wkpInfo.getWorkplaceId())
				.code(wkpInfo.getWorkplaceCode())
				.name(wkpInfo.getDisplayName())
				.build());
	}
	
	public Optional<Kcp010WorkplaceSearchData> findBySid(String employeeId, GeneralDate baseDate) {
		String companyID = AppContexts.user().companyId();
		//get AffWorkplaceHistory
		Optional<AffWorkplaceHistory> affWrkPlc = workplaceHistoryRepo.getByEmpIdAndStandDate(employeeId, baseDate);
		if(!affWrkPlc.isPresent()) 
			return Optional.empty();
		
		//get AffWorkplaceHistoryItem
		String historyId = affWrkPlc.get().getHistoryItems().get(0).identifier();
		Optional<AffWorkplaceHistoryItem> affWrkPlcItem = workplaceHistoryItemRepository.getByHistId(historyId);
		if(!affWrkPlcItem.isPresent())
			return Optional.empty();
		
		// Get workplace info.
		Optional<WorkplaceInformation> optWorkplaceInfo = workplaceInforRepo.getWkpNewByIdDate(companyID, affWrkPlcItem.get().getWorkplaceId(), baseDate);

		// Check exist
		if (!optWorkplaceInfo.isPresent()) {
			return Optional.empty();
		}

		// Return workplace id
		WorkplaceInformation wkpInfo = optWorkplaceInfo.get();
		return Optional.of(Kcp010WorkplaceSearchData.builder()
				.workplaceId(wkpInfo.getWorkplaceId())
				.code(wkpInfo.getWorkplaceCode().v())
				.name(wkpInfo.getWorkplaceDisplayName().v()).build());
	}
}
