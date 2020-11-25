/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.DeformationLaborSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.FlexSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.NormalSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingFinder.
 */
@Stateless
public class CompanyWtSettingFinder {

	/** The repository. */
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	@Inject 
	private DeforLaborTimeComRepo deforLaborTimeComRepo;
	
	@Inject
	private RegularLaborTimeComRepo regularLaborTimeComRepo;

	/**
	 * Find.
	 *
	 * @return the company setting dto
	 */
	public CompanyWtSettingDto find(int year) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();
		
		CompanyWtSettingDto dto = null;
		
		val defor = deforLaborTimeComRepo.find(companyId);
		val regular = regularLaborTimeComRepo.find(companyId);
		val flexWorkTime = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.FLEX, year);
		val deforWorkTime = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		val regularWorkTime = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		
		// Update mode.
		if(defor.isPresent() && regular.isPresent()) {
			dto = new CompanyWtSettingDto(
					FlexSettingDto.with(flexWorkTime), 
					DeformationLaborSettingDto.with(defor.get(), deforWorkTime),
					year, 
					NormalSettingDto.with(regular.get(), regularWorkTime));
		}
		// New mode.
		return dto;
	}
}
