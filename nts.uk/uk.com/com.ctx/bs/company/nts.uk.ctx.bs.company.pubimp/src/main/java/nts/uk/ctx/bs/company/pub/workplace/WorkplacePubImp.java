/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;

/**
 * The Class WorkplacePubImp.
 */
@Stateless
public class WorkplacePubImp implements WorkplacePub {

	/** The workplace repository. */
	@Inject
	private WorkplaceRepository workplaceRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.company.pub.workplace.WorkplacePub#
	 * findAllWorkplaceOfCompany(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<PubWorkplaceDto> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate) {
		return workplaceRepository.findAllWorkplaceOfCompany(companyId, baseDate).stream()
				.map(item -> new PubWorkplaceDto(item.getCompanyId().v(), item.getWorkplaceId().v(),
						item.getWorkplaceCode().v(), item.getWorkplaceName().v(),
						item.getPeriod().getStartDate(), item.getPeriod().getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.workplace.WorkplacePub#findAllHierarchyChild(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<PubWorkplaceHierarchyDto> findAllHierarchyChild(String companyId,
			String workplaceId) {
		return workplaceRepository.findAllHierarchyChild(companyId, workplaceId).stream()
				.map(item -> new PubWorkplaceHierarchyDto(item.getWorkplaceId().v(),
						item.getHierarchyCode()))
				.collect(Collectors.toList());
	}

}
