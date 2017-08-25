/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.workplace.Workplace;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.workplace.WorkplacePub#findByWpkId(java.lang.
	 * String)
	 */
	@Override
	public PubWorkplaceDto findByWkpId(String workplaceId) {
		// Query
		Optional<Workplace> optWorkplace = workplaceRepository.findByWkpId(workplaceId);

		// Check exist
		if (!optWorkplace.isPresent()) {
			return null;
		}

		// Get item
		Workplace item = optWorkplace.get();

		// Return
		return new PubWorkplaceDto(item.getCompanyId().v(), item.getWorkplaceId().v(),
				item.getWorkplaceCode().v(), item.getWorkplaceName().v(),
				item.getPeriod().getStartDate(), item.getPeriod().getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.workplace.WorkplacePub#findByWpkIds(java.util.
	 * List)
	 */
	@Override
	public List<PubWorkplaceDto> findByWkpIds(List<String> workplaceIds) {
		return workplaceRepository.findByWkpIds(workplaceIds).stream()
				.map(item -> new PubWorkplaceDto(item.getCompanyId().v(), item.getWorkplaceId().v(),
						item.getWorkplaceCode().v(), item.getWorkplaceName().v(),
						item.getPeriod().getStartDate(), item.getPeriod().getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.workplace.WorkplacePub#findWpkIds(java.lang.
	 * String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<String> findWpkIdsByWkpCode(String companyId, String wpkCode,
			GeneralDate baseDate) {
		return workplaceRepository.findByWkpCd(companyId, wpkCode, baseDate).stream()
				.map(item -> item.getWorkplaceId().v()).collect(Collectors.toList());
	}

}
