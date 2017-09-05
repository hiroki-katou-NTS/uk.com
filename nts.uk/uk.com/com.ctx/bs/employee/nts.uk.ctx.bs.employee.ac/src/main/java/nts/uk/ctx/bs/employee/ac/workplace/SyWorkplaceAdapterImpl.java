/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ac.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.company.pub.workplace.WorkplaceExport;
import nts.uk.ctx.bs.company.pub.workplace.WorkplacePub;
import nts.uk.ctx.bs.employee.dom.access.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.WorkplaceImport;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.WorkplaceHierarchyImport;

/**
 * The Class WorkplaceAdapterImpl.
 */
@Stateless
public class SyWorkplaceAdapterImpl implements SyWorkplaceAdapter {

	/** The workplace pub. */
	@Inject
	private WorkplacePub workplacePub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.workplace.WorkplaceAdapter#findByWkpId(
	 * java.lang.String)
	 */
	@Override
	public WorkplaceImport findByWkpId(String workplaceId) {

		WorkplaceExport item = workplacePub.findByWkpId(workplaceId);

		// Check exist
		if (item == null) {
			return null;
		}

		// Return
		return new WorkplaceImport(item.getCompanyId(), item.getWorkplaceId(),
				item.getWorkplaceCode(), item.getWorkplaceName(), item.getStartDate(),
				item.getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.access.workplace.WorkplaceAdapter#
	 * findAllWorkplaceOfCompany(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<WorkplaceImport> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate) {
		return workplacePub.findAllWorkplaceOfCompany(companyId, baseDate).stream()
				.map(item -> new WorkplaceImport(item.getCompanyId(), item.getWorkplaceId(),
						item.getWorkplaceCode(), item.getWorkplaceName(), item.getStartDate(),
						item.getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.access.workplace.WorkplaceAdapter#
	 * findAllHierarchyChild(java.lang.String, java.lang.String)
	 */
	@Override
	public List<WorkplaceHierarchyImport> findAllHierarchyChild(String companyId,
			String workplaceId) {
		return workplacePub.findAllHierarchyChild(companyId, workplaceId).stream().map(
				item -> new WorkplaceHierarchyImport(item.getWorkplaceId(), item.getHierarchyCode()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.workplace.WorkplaceAdapter#findByWkpIds
	 * (java.util.List)
	 */
	@Override
	public List<WorkplaceImport> findByWkpIds(List<String> workplaceIds) {
		return workplacePub.findByWkpIds(workplaceIds).stream()
				.map(item -> new WorkplaceImport(item.getCompanyId(), item.getWorkplaceId(),
						item.getWorkplaceCode(), item.getWorkplaceName(), item.getStartDate(),
						item.getEndDate()))
				.collect(Collectors.toList());
	}

}
