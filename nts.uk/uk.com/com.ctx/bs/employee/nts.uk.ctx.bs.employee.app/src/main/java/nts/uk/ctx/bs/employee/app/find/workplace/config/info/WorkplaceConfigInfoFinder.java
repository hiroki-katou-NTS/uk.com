/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.config.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceConfigInfoFinder.
 */
@Stateless
public class WorkplaceConfigInfoFinder {

	/** The wkp config info service. */
	@Inject
	WkpConfigInfoService wkpConfigInfoService;

	/** The wkp config info repo. */
	@Inject
	private WorkplaceConfigInfoRepository wkpConfigInfoRepo;

	/** The wkp info repo. */
	@Inject
	private WorkplaceInfoRepository wkpInfoRepo;

	/** The wkp config repository. */
	@Inject
	private WorkplaceConfigRepository wkpConfigRepository;

	/** The Constant HIERARCHY_LENGTH. */
	private static final Integer HIERARCHY_LENGTH = 3;

	/**
	 * Find all by base date.
	 *
	 * @param baseD
	 *            the str D
	 * @return the list
	 */
	public List<WorkplaceHierarchyDto> findAllByBaseDate(GeneralDate baseD) {
		// get all WorkplaceConfigInfo with StartDate
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceConfig> optionalWkpConfig = wkpConfigRepository.findByBaseDate(companyId,
				baseD);
		if (!optionalWkpConfig.isPresent()) {
			return null;
		}
		WorkplaceConfig wkpConfig = optionalWkpConfig.get();
		String historyId = wkpConfig.getWkpConfigHistoryLatest().identifier();
		Optional<WorkplaceConfigInfo> opWkpConfigInfo = wkpConfigInfoRepo.find(companyId,
				historyId);
		if (!opWkpConfigInfo.isPresent()) {
			return Collections.emptyList();
		}
		return this.initTree(baseD, opWkpConfigInfo.get());
	}

	/**
	 * Find all by start date.
	 *
	 * @param strD
	 *            the str D
	 * @return the list
	 */
	public List<WorkplaceHierarchyDto> findAllByStartDate(GeneralDate strD) {
		// get all WorkplaceConfigInfo with StartDate
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceConfig> optionalWkpConfig = wkpConfigRepository.findByStartDate(companyId,
				strD);
		if (!optionalWkpConfig.isPresent()) {
			return null;
		}
		WorkplaceConfig wkpConfig = optionalWkpConfig.get();
		String historyId = wkpConfig.getWkpConfigHistoryLatest().identifier();

		Optional<WorkplaceConfigInfo> opWkpConfigInfo = wkpConfigInfoRepo.find(companyId,
				historyId);
		if (!opWkpConfigInfo.isPresent()) {
			throw new BusinessException("Msg_373");
		}
		
		return this.initTree(strD, opWkpConfigInfo.get());
	}

	/**
	 * Inits the tree.
	 *
	 * @param wkpConfigInfo the wkp config info
	 * @return the list
	 */
	private List<WorkplaceHierarchyDto> initTree(GeneralDate startDWkpConfigHist, WorkplaceConfigInfo wkpConfigInfo) {
		String companyId = AppContexts.user().companyId();

		// get list hierarchy
		List<WorkplaceHierarchy> lstHierarchy = wkpConfigInfo.getLstWkpHierarchy();
		
		// filter workplace infor latest
		List<WorkplaceInfo> lstWkpInfo = this.wkpInfoRepo.findDetailLatestByWkpIds(companyId, startDWkpConfigHist);
		return this.createTree(lstHierarchy.iterator(), lstWkpInfo, new ArrayList<>());
	}

	/**
	 * Creates the tree.
	 *
	 * @param lstWkpInfo
	 *            the lst wkp info
	 * @param lstHierarchy
	 *            the lst hierarchy
	 * @param lstReturn
	 *            the lst return
	 * @return the list
	 */
	private List<WorkplaceHierarchyDto> createTree(
			Iterator<WorkplaceHierarchy> iteratorWkpHierarchy, List<WorkplaceInfo> lstHWkpInfo,
			List<WorkplaceHierarchyDto> lstReturn) {
		
		List<WorkplaceHierarchy> lstWkpHierarchyRemove = new ArrayList<>();
		
		// while have workplace
		while (iteratorWkpHierarchy.hasNext()) {
			// pop 1 item
			WorkplaceHierarchy wkpHierarchy = iteratorWkpHierarchy.next();
			
			// convert
			WorkplaceHierarchyDto dto = new WorkplaceHierarchyDto();
			wkpHierarchy.saveToMemento(dto);
			
			// get workplace hierarchy by wkpId
			Optional<WorkplaceInfo> opWkpInfo = lstHWkpInfo.stream()
					.filter(w -> w.getWorkplaceId().equals(wkpHierarchy.getWorkplaceId()))
					.findFirst();
			if (opWkpInfo.isPresent()) {
				WorkplaceInfo wkpInfo = opWkpInfo.get();
				dto.setCode(wkpInfo.getWorkplaceCode().v());
				dto.setName(wkpInfo.getWorkplaceName().v());
			} else {
				lstWkpHierarchyRemove.add(wkpHierarchy);
			}
			
			// ignore workplace that don't have code and name.
			if (lstWkpHierarchyRemove.contains(wkpHierarchy)) {
				continue;
			}

			dto.setHierarchyCode(new HierarchyCode(wkpHierarchy.getHierarchyCode().v()));
			// build List
			this.pushToList(lstReturn, dto, wkpHierarchy.getHierarchyCode().v(), Strings.EMPTY);
		}
		return lstReturn;
	}

	/**
	 * Push to list.
	 *
	 * @param lstReturn
	 *            the lst return
	 * @param dto
	 *            the dto
	 * @param hierarchyCode
	 *            the hierarchy code
	 * @param preCode
	 *            the pre code
	 */
	private void pushToList(List<WorkplaceHierarchyDto> lstReturn, WorkplaceHierarchyDto dto,
			String hierarchyCode, String preCode) {
		String searchCode = preCode + hierarchyCode.substring(0, HIERARCHY_LENGTH);
		dto.setChilds(new ArrayList<>());
		if (hierarchyCode.length() == HIERARCHY_LENGTH) {
			// check duplicate code
			if (lstReturn.isEmpty()) {
				lstReturn.add(dto);
				return;
			}
			for (WorkplaceHierarchyDto item : lstReturn) {
				if (!item.getCode().equals(dto.getCode())) {
					lstReturn.add(dto);
					break;
				}
			}
		} else {
			Optional<WorkplaceHierarchyDto> optWorkplaceFindDto = lstReturn.stream()
					.filter(item -> item.getHierarchyCode().equals(searchCode)).findFirst();

			if (!optWorkplaceFindDto.isPresent()) {
				return;
			}

			List<WorkplaceHierarchyDto> currentItemChilds = optWorkplaceFindDto.get().getChilds();

			pushToList(currentItemChilds, dto,
					hierarchyCode.substring(HIERARCHY_LENGTH, hierarchyCode.length()), searchCode);
		}
	}

}
