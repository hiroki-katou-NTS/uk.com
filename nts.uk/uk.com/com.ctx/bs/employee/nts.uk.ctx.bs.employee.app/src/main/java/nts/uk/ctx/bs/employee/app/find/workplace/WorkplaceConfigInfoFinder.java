/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkplaceConfigInfoFinder {

	@Inject
	WkpConfigInfoService wkpConfigInfoService;

	@Inject
	private WorkplaceConfigInfoRepository workplaceConfigInfoRepository;

	@Inject
	private WorkplaceRepository workplaceRepository;

	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;

	@Inject
	private WorkplaceConfigRepository wkpConfigRepository;

	public List<WorkplaceHierarchyDto> findAll(GeneralDate strD) {
		// TODO add new param config hist start date
		// histId: history of config
		// get all WorkplaceConfigInfo with StartDate
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceConfig> wkpConfig = wkpConfigRepository.findByStartDate(companyId,strD);
		if (wkpConfig.isPresent()) {
			String historyId = wkpConfig.get().getWkpConfigHistory().get(0).getHistoryId();
			Optional<WorkplaceConfigInfo> wkpConfigInfo = workplaceConfigInfoRepository.find(companyId, historyId);

			if (wkpConfigInfo.isPresent()) {
				if (wkpConfigInfo.get().getWkpHierarchy().isEmpty()) {
					throw new BusinessException("Msg_373");
				} else {
					// get list hierarchy
					List<WorkplaceHierarchy> lstHierarchy = wkpConfigInfo.get().getWkpHierarchy();
					List<WorkplaceInfo> lstWkpInfo = new ArrayList<>();
					lstHierarchy.stream().forEach(item -> {
						lstWkpInfo.add(workplaceInfoRepository.findLatestByWorkplaceId(item.getWorkplaceId().v()).get());
					});

					List<WorkplaceHierarchyDto> lst = this.convertToTree(lstWkpInfo, lstHierarchy);
					// TODO add list hierarchy to Dto
					return lst;
				}
			}
		}
		return null;
	}

	/**
	 * Convert to tree.
	 *
	 * @param lstWkpInfo
	 *            the lst wkp info
	 * @param lstHierarchy
	 *            the lst hierarchy
	 * @return the list
	 */
	private List<WorkplaceHierarchyDto> convertToTree(List<WorkplaceInfo> lstWkpInfo,
			List<WorkplaceHierarchy> lstHierarchy) {
		// define convert tree function
		List<WorkplaceHierarchyDto> lstReturn = new ArrayList<>();
		return createTree(lstWkpInfo, lstHierarchy, lstReturn);
	}

	/**
	 * Covert to workplace hierarchy dto.
	 *
	 * @param wkpHierarchy the wkp hierarchy
	 * @param wkpInfo the wkp info
	 * @return the workplace hierarchy dto
	 */
	private WorkplaceHierarchyDto covertToWorkplaceHierarchyDto(WorkplaceHierarchy wkpHierarchy,
			WorkplaceInfo wkpInfo) {
		WorkplaceHierarchyDto dto = new WorkplaceHierarchyDto();
		wkpHierarchy.saveToMemento(dto);
		dto.setCode(wkpInfo.getWorkplaceCode().v());
		dto.setName(wkpInfo.getWorkplaceName().v());
		return dto;
	}
	
	/**
	 * Creates the tree.
	 *
	 * @param lstWkpInfo the lst wkp info
	 * @return the list
	 */
	private List<WorkplaceHierarchyDto> createTree(List<WorkplaceInfo> lstWkpInfo,List<WorkplaceHierarchy> lstHierarchy,
			List<WorkplaceHierarchyDto> lstReturn) {
		// while have workplace
		while (!lstWkpInfo.isEmpty()) {
			// pop 1 item
			WorkplaceInfo workplaceInfo = lstWkpInfo.remove(0);
			// get workplace hierarchy by wkpId
			WorkplaceHierarchy wkpHierarchy = lstHierarchy.stream()
					.filter(w -> w.getWorkplaceId().equals(workplaceInfo.getWorkplaceId())).findFirst().get();
			// convert
			WorkplaceHierarchyDto dto = this.covertToWorkplaceHierarchyDto(wkpHierarchy, workplaceInfo);

			dto.setHierarchyCode(new HierarchyCode(wkpHierarchy.getHierarchyCode().v()));
			// build List
			this.pushToList(lstReturn, dto, wkpHierarchy.getHierarchyCode().v(), "");
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
	private void pushToList(List<WorkplaceHierarchyDto> lstReturn, WorkplaceHierarchyDto dto, String hierarchyCode,
			String preCode) {
		String searchCode = preCode + hierarchyCode.substring(0, 3);
		dto.setChilds(new ArrayList<>());
		if (hierarchyCode.length() == 3) {
			// check duplicate code
			if (lstReturn.isEmpty()) {
				lstReturn.add(dto);
			} else {
				for (WorkplaceHierarchyDto item : lstReturn) {
					if (!item.getCode().equals(dto.getCode())) {
						lstReturn.add(dto);
						break;
					}
				}
			}
		} else {
			Optional<WorkplaceHierarchyDto> optWorkplaceFindDto = lstReturn.stream()
					.filter(item -> item.getHierarchyCode().equals(searchCode)).findFirst();

			if (!optWorkplaceFindDto.isPresent()) {
				return;
			}

			List<WorkplaceHierarchyDto> currentItemChilds = optWorkplaceFindDto.get().getChilds();

			pushToList(currentItemChilds, dto, hierarchyCode.substring(3, hierarchyCode.length()), searchCode);
		}
	}
	
}
