/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew.EmpStatWorkTimeSetDto.EmpStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpStatWorkTimeSetFinder.
 */
@Stateless
public class EmpStatWorkTimeSetFinder {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeEmpRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param year
	 *            the year
	 * @param emplCode
	 *            the empl code
	 * @return the details
	 */
	public EmpStatWorkTimeSetDto getDetails(Integer year, String emplCode) {

		String companyId = AppContexts.user().companyId();
		EmpStatWorkTimeSetDtoBuilder dtoBuilder = EmpStatWorkTimeSetDto.builder();
		
		dtoBuilder.year(year);
		dtoBuilder.employmentCode(emplCode);

		Optional<DeforLaborTimeEmp> optTransLaborTime = this.transLaborTimeRepository.find(companyId, emplCode);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get()));
		}

		Optional<RegularLaborTimeEmp> optComRegular = this.regularLaborTimeRepository.findById(companyId, emplCode);
		if (optComRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optComRegular.get()));
		}

		val regularSet = monthlyWorkTimeSetRepo.findEmployment(companyId, emplCode, LaborWorkTypeAttr.REGULAR_LABOR, year);
		if (!regularSet.isEmpty()) {
			dtoBuilder.normalSetting(EmpNormalSettingDto.with(companyId, emplCode, year, regularSet));
		}
		
		val flexSet = monthlyWorkTimeSetRepo.findEmployment(companyId, emplCode, LaborWorkTypeAttr.FLEX, year);
		if (!flexSet.isEmpty()) {
			dtoBuilder.flexSetting(EmpFlexSettingDto.with(companyId, emplCode, year, flexSet));
		}

		val deforSet = monthlyWorkTimeSetRepo.findEmployment(companyId, emplCode, LaborWorkTypeAttr.DEFOR_LABOR, year);
		if (!deforSet.isEmpty()) {
			dtoBuilder.deforLaborSetting(EmpDeforLaborSettingDto.with(year, companyId, emplCode, deforSet));
		}

		return dtoBuilder.build();
	}

	/**
	 * Find all emp reg work hour.
	 *
	 * @return the list
	 */
	public List<EmpRegularWorkHourDto> findAllEmpRegWorkHour() {
		// get company id
		String companyId = AppContexts.user().companyId();		
		List<EmpRegularWorkHourDto> listShainRegWorkHourDto = new ArrayList<>();
		
		// get list employee regular labor time
		List<RegularLaborTimeEmp> listShainRegLaborTime = this.regularLaborTimeRepository.findListByCid(companyId);
		
		// check list is not empty
		if(!listShainRegLaborTime.isEmpty()){			
			listShainRegWorkHourDto = listShainRegLaborTime.stream().map(domain -> 
			EmpRegularWorkHourDto.fromDomain(domain))
			.collect(Collectors.toList());
		}
		
		return listShainRegWorkHourDto;
	}

}
