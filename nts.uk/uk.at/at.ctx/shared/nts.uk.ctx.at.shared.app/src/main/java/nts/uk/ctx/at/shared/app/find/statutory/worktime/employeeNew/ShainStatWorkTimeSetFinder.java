/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew.ShainStatWorkTimeSetDto.ShainStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ComStatWorkTimeSetFinder.
 */
@Stateless
public class ShainStatWorkTimeSetFinder {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeShaRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param year
	 *            the year
	 * @return the details
	 */
	public ShainStatWorkTimeSetDto getDetails(Integer year, String empId) {

		String companyId = AppContexts.user().companyId();
		ShainStatWorkTimeSetDtoBuilder dtoBuilder = ShainStatWorkTimeSetDto.builder();
		
		dtoBuilder.year(year);
		dtoBuilder.employeeId(empId);

		Optional<DeforLaborTimeSha> optTransLaborTime = this.transLaborTimeRepository.find(companyId, empId);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get()));
		}

		Optional<RegularLaborTimeSha> optComRegular = this.regularLaborTimeRepository.find(companyId, empId);
		if (optComRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optComRegular.get()));
		}

		val regularSet = monthlyWorkTimeSetRepo.findEmployee(companyId, empId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		if (!regularSet.isEmpty()) {
			dtoBuilder.normalSetting(ShainNormalSettingDto.with(companyId, empId, year, regularSet));
		}
		
		val flexSet = monthlyWorkTimeSetRepo.findEmployee(companyId, empId, LaborWorkTypeAttr.FLEX, year);
		if (!flexSet.isEmpty()) {
			dtoBuilder.flexSetting(ShainFlexSettingDto.with(companyId, empId, year, flexSet));
		}

		val deforSet = monthlyWorkTimeSetRepo.findEmployee(companyId, empId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		if (!deforSet.isEmpty()) {
			dtoBuilder.deforLaborSetting(ShainDeforLaborSettingDto.with(year, companyId, empId, deforSet));
		}

		return dtoBuilder.build();
	}
	

	/**
	 * Find all shain reg labor time.
	 *
	 * @return the list
	 */
	public List<ShainRegularWorkHourDto> findAllShainRegLaborTime(){
		
		// get company id
		String companyId = AppContexts.user().companyId();		
		List<ShainRegularWorkHourDto> listShainRegWorkHourDto = new ArrayList<>();
		
		// get list employee regular labor time
		List<RegularLaborTimeSha> listShainRegLaborTime = this.regularLaborTimeRepository.findAll(companyId);
		
		// check list is not empty
		if(!listShainRegLaborTime.isEmpty()){			
			listShainRegWorkHourDto = listShainRegLaborTime.stream().map(domain -> 
				ShainRegularWorkHourDto.fromDomain(domain))
			.collect(Collectors.toList());
		}
		
		return listShainRegWorkHourDto;
	}
	
}
