/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employment;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.DeformationLaborSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.FlexSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.NormalSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentWtSettingFinder.
 */
@Stateless
public class EmploymentWtSettingFinder {

	/** The repository. */
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	@Inject 
	private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;
	
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;

	/**
	 * Find.
	 *
	 * @param request the request
	 * @return the employment wt setting dto
	 */
	public EmploymentWtSettingDto find(int year, String employmentCode) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();
		
		EmploymentWtSettingDto dto = null;
		
		val defor = deforLaborTimeEmpRepo.find(companyId, employmentCode);
		val regular = regularLaborTimeEmpRepo.findById(companyId, employmentCode);
		val flexWorkTime = monthlyWorkTimeSetRepo.findEmployment(companyId, employmentCode, LaborWorkTypeAttr.FLEX, year);
		val deforWorkTime = monthlyWorkTimeSetRepo.findEmployment(companyId, employmentCode, LaborWorkTypeAttr.DEFOR_LABOR, year);
		val regularWorkTime = monthlyWorkTimeSetRepo.findEmployment(companyId, employmentCode, LaborWorkTypeAttr.REGULAR_LABOR, year);
		
		// Update mode.
		if(defor.isPresent() && regular.isPresent()) {
			dto = EmploymentWtSettingDto.builder()
					.flexSetting(FlexSettingDto.with(flexWorkTime))
					.deformationLaborSetting(DeformationLaborSettingDto.with(defor.get(), deforWorkTime))
					.normalSetting(NormalSettingDto.with(regular.get(), regularWorkTime))
					.employmentCode(employmentCode)
					.year(year)
					.build();
		}
		// New mode.
		return dto;
	}

	/**
	 * Findall.
	 *
	 * @param year the year
	 * @return the list
	 */
	public List<String> findall(int year) {
		return this.monthlyWorkTimeSetRepo.findEmploymentCD(AppContexts.user().companyId(), year);
	}
}
