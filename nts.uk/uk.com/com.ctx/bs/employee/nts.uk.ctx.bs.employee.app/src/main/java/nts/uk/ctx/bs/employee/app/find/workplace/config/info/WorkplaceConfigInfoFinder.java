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
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WkpConfigInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.dom.access.role.SyRoleAdapter;
import nts.uk.ctx.bs.employee.dom.access.role.WorkplaceIDImport;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
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

	@Inject
	private SyRoleAdapter syRoleWorkplaceAdapter;

	/**
	 * Find all by base date.
	 *
	 * @param object
	 *            the object
	 * @return the list
	 */
	public List<WorkplaceHierarchyDto> findAllByBaseDate(WkpConfigInfoFindObject object) {
		
		// Check system type.
		if (object.getSystemType() == null) {
			return Collections.emptyList();
		}
		
		// Check if is restrictionOfReferenceRange.
		List<String> workplaceIdsCanReference = new ArrayList<>();
		if (object.getRestrictionOfReferenceRange()) {
			workplaceIdsCanReference = this.syRoleWorkplaceAdapter.findListWkpIdByRoleId(object.getSystemType(), object.getBaseDate())
					.getListWorkplaceIds();
		}
		
		// Find Workplace Config.
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceConfig> optionalWkpConfig = wkpConfigRepository.findByBaseDate(companyId, object.getBaseDate());

		if (!optionalWkpConfig.isPresent()) {
			return Collections.emptyList();
		}
		
		// Find workplace config info.
		List<String> configHisIds = optionalWkpConfig.get().items().stream().map(item -> item.identifier())
				.collect(Collectors.toList());
		List<WorkplaceConfigInfo> workplaceConfigInfos = this.wkpConfigInfoRepo.findByHistoryIdsAndWplIds(companyId,
				configHisIds, workplaceIdsCanReference);

		if (CollectionUtil.isEmpty(workplaceConfigInfos)) {
			return Collections.emptyList();
		}
		
		List<WorkplaceHierarchy> workplaceHierarchies = workplaceConfigInfos.stream()
				.map(info -> info.getLstWkpHierarchy()).flatMap(list -> list.stream())
				.sorted((a,b) -> a.getHierarchyCode().v().compareTo(b.getHierarchyCode().v()))
				.collect(Collectors.toList());
		return this.initTree(object.getBaseDate(), workplaceHierarchies);
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
		Optional<WorkplaceConfig> optionalWkpConfig = wkpConfigRepository.findByStartDate(companyId, strD);
		if (!optionalWkpConfig.isPresent()) {
			return null;
		}
		WorkplaceConfig wkpConfig = optionalWkpConfig.get();
		String historyId = wkpConfig.getWkpConfigHistoryLatest().identifier();

		Optional<WorkplaceConfigInfo> opWkpConfigInfo = wkpConfigInfoRepo.find(companyId, historyId);
		if (!opWkpConfigInfo.isPresent()) {
			throw new BusinessException("Msg_373");
		}
		return this.initTree(strD, opWkpConfigInfo.get().getLstWkpHierarchy());
	}

	/**
	 * Inits the tree.
	 *
	 * @param startDWkpConfigHist
	 *            the start D wkp config hist
	 * @param lstHierarchy
	 *            the lst hierarchy
	 * @return the list
	 */
	private List<WorkplaceHierarchyDto> initTree(GeneralDate startDWkpConfigHist,
			List<WorkplaceHierarchy> lstHierarchy) {
		String companyId = AppContexts.user().companyId();

		// filter workplace infor latest
		List<WorkplaceInfo> lstWkpInfo = this.wkpInfoRepo.findAll(companyId, startDWkpConfigHist);
		return this.createTree(lstHierarchy, lstWkpInfo);
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
	private List<WorkplaceHierarchyDto> createTree(List<WorkplaceHierarchy> lstHierarchy,
			List<WorkplaceInfo> lstHWkpInfo) {

		List<WorkplaceHierarchyDto> lstReturn = new ArrayList<>();
		// Higher hierarchyCode has shorter length
		int highestHierarchy = lstHierarchy.stream()
				.min((a, b) -> a.getHierarchyCode().v().length() - b.getHierarchyCode().v().length()).get()
				.getHierarchyCode().v().length();
		Iterator<WorkplaceHierarchy> iteratorWkpHierarchy = lstHierarchy.iterator();

		// while have workplace
		while (iteratorWkpHierarchy.hasNext()) {
			// pop 1 item
			WorkplaceHierarchy wkpHierarchy = iteratorWkpHierarchy.next();

			// convert
			WorkplaceHierarchyDto dto = new WorkplaceHierarchyDto();
			wkpHierarchy.saveToMemento(dto);

			// get workplace hierarchy by wkpId
			Optional<WorkplaceInfo> opWkpInfo = lstHWkpInfo.stream()
					.filter(w -> w.getWorkplaceId().equals(wkpHierarchy.getWorkplaceId())).findFirst();
			if (opWkpInfo.isPresent()) {
				WorkplaceInfo wkpInfo = opWkpInfo.get();
				dto.setCode(wkpInfo.getWorkplaceCode().v());
				dto.setName(wkpInfo.getWorkplaceName().v());
			} else {
				// ignore workplace that don't have code and name.
				continue;
			}

			// build List
			this.pushToList(lstReturn, dto, wkpHierarchy.getHierarchyCode().v(), Strings.EMPTY, highestHierarchy);
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
			String preCode, int highestHierarchy) {
		dto.setChilds(new ArrayList<>());
		if (hierarchyCode.length() == highestHierarchy) {
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
			String searchCode = preCode.isEmpty() ? preCode + hierarchyCode.substring(0, highestHierarchy)
					: preCode + hierarchyCode.substring(0, HIERARCHY_LENGTH);

			Optional<WorkplaceHierarchyDto> optWorkplaceFindDto = lstReturn.stream()
					.filter(item -> item.getHierarchyCode().equals(searchCode)).findFirst();

			if (!optWorkplaceFindDto.isPresent()) {
				return;
			}

			List<WorkplaceHierarchyDto> currentItemChilds = optWorkplaceFindDto.get().getChilds();

			pushToList(currentItemChilds, dto, hierarchyCode.substring(HIERARCHY_LENGTH, hierarchyCode.length()),
					searchCode, highestHierarchy);
		}
	}

	/**
	 * Find all by base date.
	 *
	 * @param object
	 *            the object
	 * @return the list
	 */
	public List<WorkplaceHierarchyDto> findAllByBaseDateForKcp010(WkpConfigInfoFindObject object) {

		// get base date
		GeneralDate baseD = object.getBaseDate();

		// get all WorkplaceConfigInfo with StartDate
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceConfig> optionalWkpConfig = wkpConfigRepository.findByBaseDate(companyId, baseD);
		if (!optionalWkpConfig.isPresent()) {
			return null;
		}
		WorkplaceConfig wkpConfig = optionalWkpConfig.get();
		String historyId = wkpConfig.getWkpConfigHistoryLatest().identifier();
		Optional<WorkplaceConfigInfo> opWkpConfigInfo = wkpConfigInfoRepo.find(companyId, historyId);
		if (!opWkpConfigInfo.isPresent()) {
			return Collections.emptyList();
		}

		// get list hierarchy
		List<WorkplaceHierarchy> lstHierarchy = opWkpConfigInfo.get().getLstWkpHierarchy();

		WorkplaceIDImport workplaceIDImport = syRoleWorkplaceAdapter.findListWkpId(object.getSystemType());

		List<WorkplaceHierarchy> result = new ArrayList<>();

		// if listWorkplaceIds is empty
		if (workplaceIDImport.getListWorkplaceIds().isEmpty()) {
			return this.initTree(baseD, result);
		}

		// if listWorkplaceIds is not empty
		for (WorkplaceHierarchy item : lstHierarchy) {
			if (workplaceIDImport.getListWorkplaceIds().contains(item.getWorkplaceId())) {
				// if get part of list workplace id
				if (!workplaceIDImport.getIsAllEmp()) {
					// if list workplace id just have childs workplace id
					if (item.getHierarchyCode().v().length() == 3) {
						result.add(item);
					} else if (item.getHierarchyCode().v().length() > 3) {
						Optional<WorkplaceConfigInfo> opWorkplaceConfigInfo = wkpConfigInfoRepo
								.findAllParentByWkpId(companyId, baseD, item.getWorkplaceId());
						// find parents workplace id from childs workplace id
						List<WorkplaceHierarchy> listWorkplaceHierarchy = opWorkplaceConfigInfo.get()
								.getLstWkpHierarchy();
						// add parents workplace id to list
						result.addAll(listWorkplaceHierarchy);
						// add childs workplace id to list
						result.add(item);
					}
					// if get all of list workplace id
				} else {
					result.add(item);
				}
			}

			// remove dublicate element in list
			result = result.stream().distinct().collect(Collectors.toList());

		}
		return this.initTree(baseD, result);
	}

	/**
	 * Find flat list. 会社の基準日時点の職場を取得する
	 */
	public List<WorkplaceHierarchyDto> findFlatList(GeneralDate baseDate) {
		// Find all workplace in compant
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceConfig> optionalWkpConfig = wkpConfigRepository.findByBaseDate(companyId, baseDate);
		if (!optionalWkpConfig.isPresent()) {
			return Collections.emptyList();
		}

		// Find Workplace config info
		WorkplaceConfig wkpConfig = optionalWkpConfig.get();
		String historyId = wkpConfig.getWkpConfigHistoryLatest().identifier();
		Optional<WorkplaceConfigInfo> opWkpConfigInfo = wkpConfigInfoRepo.find(companyId, historyId);
		if (!opWkpConfigInfo.isPresent()) {
			return Collections.emptyList();
		}

		// Convert list to tree. -> special process for sort workplace list.
		List<WorkplaceHierarchy> lstHierarchy = opWkpConfigInfo.get().getLstWkpHierarchy();
		List<WorkplaceHierarchyDto> treeList = this.initTree(baseDate, lstHierarchy);

		// flat workplace tree. -> special process for sort workplace list.
		List<WorkplaceHierarchyDto> flatList = new ArrayList<>();
		treeList.stream().forEach(wpl -> {
			// Convert tree to flat list.
			flatList.addAll(this.convertToFlatList(wpl));
		});

		return flatList;
	}

	/**
	 * Convert to flat list.
	 *
	 * @param dto
	 *            the dto
	 * @return the list
	 */
	private List<WorkplaceHierarchyDto> convertToFlatList(WorkplaceHierarchyDto dto) {
		List<WorkplaceHierarchyDto> resultList = new ArrayList<>();
		;
		resultList.add(dto);
		if (CollectionUtil.isEmpty(dto.getChilds())) {
			return resultList;
		}
		dto.getChilds().stream().forEach(wpl -> {
			resultList.addAll(this.convertToFlatList(wpl));
		});
		return resultList;
	}
}
