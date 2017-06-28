/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.workplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.app.find.company.organization.workplace.dto.WorkplaceFindDto;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchy;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHistory;
import nts.uk.ctx.basic.dom.company.organization.workplace.Workplace;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkplaceFinder.
 */
@Stateless
public class WorkplaceFinder {
	
	/** The repository. */
	@Inject
	private WorkplaceRepository repository;

	/**
	 * Find all.
	 *
	 * @param date
	 *            the date
	 * @param format
	 *            the format
	 * @return the list
	 */
	public List<WorkplaceFindDto> findAll(GeneralDate generalDate) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		// format date => general date
		List<WorkPlaceHierarchy> lstHierarchy = new ArrayList<>();
		List<Workplace> lstWorkplace = new ArrayList<>();
		List<WorkplaceFindDto> lstReturn = new ArrayList<>();
		//find all history by generalDate
		List<WorkPlaceHistory> lstHistory = this.repository.findAllHistory(companyId, generalDate);
		//find all Hierarchy by list History
		lstHistory.stream()
				.forEach(item -> lstHierarchy.addAll(this.repository.findAllHierarchy(item.getHistoryId().v())));

		Collections.sort(lstHierarchy,
				(left, right) -> left.getHierarchyCode().v().compareTo(right.getHierarchyCode().v()));
		//find all workplace from list hierarchy
		lstHierarchy.stream()
				.forEach(item -> lstWorkplace.addAll(this.repository.findAllWorkplace(item.getWorkplaceId().v())));

		// map hierarchy code and work place
		lstReturn = this.convertToTree(lstWorkplace, lstHierarchy);
		return lstReturn;
	}

	private List<WorkplaceFindDto> convertToTree(List<Workplace> workplaces, List<WorkPlaceHierarchy> lstHierarchy) {
		//define convert tree function
		Function<Workplace, WorkplaceFindDto> convertFunction = e -> {
			WorkplaceFindDto dto = new WorkplaceFindDto();
			e.saveToMemento(dto);
			return dto;
		};
		List<WorkplaceFindDto> lstReturn = new ArrayList<>();
		return createTree(workplaces, convertFunction, lstHierarchy, lstReturn);
	}

	private List<WorkplaceFindDto> createTree(List<Workplace> workplaces,
			Function<Workplace, WorkplaceFindDto> convertFunction, List<WorkPlaceHierarchy> lstHierarchy,
			List<WorkplaceFindDto> lstReturn) {
		// while have workplace
		while (!workplaces.isEmpty()) {
			//pop 1 item
			Workplace workplace = workplaces.remove(0);
			//convert
			WorkplaceFindDto dto = convertFunction.apply(workplace);
			WorkPlaceHierarchy hierarchy = lstHierarchy.stream()
					.filter(c -> c.getWorkplaceId().v().equals(workplace.getWorkplaceId().v())).findFirst().get();
			dto.setHeirarchyCode(hierarchy.getHierarchyCode().v());
			//build List
			this.pushToList(lstReturn, dto, hierarchy.getHierarchyCode().v(),"");
		}
		return lstReturn;
	}

	private void pushToList(List<WorkplaceFindDto> lstReturn, WorkplaceFindDto dto, String hierarchyCode,String preCode) {
		String searchCode = preCode+hierarchyCode.substring(0,3);
		dto.setChilds(new ArrayList<>());
		if (hierarchyCode.length() == 3) {
			//check duplicate code 
			if (lstReturn.isEmpty()) {
				lstReturn.add(dto);
			} else {
				for (WorkplaceFindDto item : lstReturn) {
					if (!item.getCode().equals(dto.getCode())) {
						lstReturn.add(dto);
						break;
					}
				}
			}
		} else {
			List<WorkplaceFindDto> currentItemChilds = lstReturn.stream()
					.filter(item -> item.getHeirarchyCode().equals(searchCode)).findFirst().get().getChilds();
			pushToList(currentItemChilds, dto, hierarchyCode.substring(3, hierarchyCode.length()),searchCode);
		}
	}
}
