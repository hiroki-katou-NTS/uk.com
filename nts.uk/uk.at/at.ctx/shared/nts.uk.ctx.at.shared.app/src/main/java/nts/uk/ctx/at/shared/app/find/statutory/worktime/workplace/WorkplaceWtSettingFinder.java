/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplace;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.DeformationLaborSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.FlexSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.NormalSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceWtSettingFinder.
 */
@Stateless
public class WorkplaceWtSettingFinder {

	/** The repository. */
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	@Inject 
	private DeforLaborTimeWkpRepo deforLaborTimeEmpRepo;
	
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeEmpRepo;

	/**
	 * Find.
	 *
	 * @param request
	 *            the request
	 * @return the workplace wt setting dto
	 */
	public WorkplaceWtSettingDto find(WorkplaceWtSettingRequest request) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();
		
		WorkplaceWtSettingDto dto = null;
		String wkpId = request.getWorkplaceId();
		int year = request.getYear();
		
		val defor = deforLaborTimeEmpRepo.find(companyId, wkpId);
		val regular = regularLaborTimeEmpRepo.find(companyId, wkpId);
		val flexWorkTime = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.FLEX, year);
		val deforWorkTime = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		val regularWorkTime = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		
		// Update mode.
		if(defor.isPresent() && regular.isPresent()) {
			dto = WorkplaceWtSettingDto.builder()
					.flexSetting(FlexSettingDto.with(flexWorkTime))
					.deformationLaborSetting(DeformationLaborSettingDto.with(defor.get(), deforWorkTime))
					.normalSetting(NormalSettingDto.with(regular.get(), regularWorkTime))
					.workplaceId(wkpId)
					.year(year)
					.build();
		}
		// New mode.
		return dto;
	}

	/**
	 * Find all.
	 *
	 * @param year
	 *            the year
	 * @return the list
	 */
	public List<String> findAll(int year) {
		return monthlyWorkTimeSetRepo.findWorkplaceID(AppContexts.user().companyId(), year);
	}
}
