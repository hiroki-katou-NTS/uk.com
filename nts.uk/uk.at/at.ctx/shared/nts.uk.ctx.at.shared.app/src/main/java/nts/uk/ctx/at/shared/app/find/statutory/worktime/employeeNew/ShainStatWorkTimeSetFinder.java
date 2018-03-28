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

import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew.ShainStatWorkTimeSetDto.ShainStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ComStatWorkTimeSetFinder.
 */
@Stateless
public class ShainStatWorkTimeSetFinder {

	/** The normal setting repository. */
	@Inject
	private ShainNormalSettingRepository normalSettingRepository;

	/** The flex setting repository. */
	@Inject
	private ShainFlexSettingRepository flexSettingRepository;

	/** The defor labor setting repository. */
	@Inject
	private ShainDeforLaborSettingRepository deforLaborSettingRepository;

	/** The trans labor time repository. */
	@Inject
	private ShainTransLaborTimeRepository speDeforLaborTimeRepository; 

	/** The regular labor time repository. */
	@Inject
	private ShainRegularWorkTimeRepository regularWorkTimeRepository;

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

		Optional<ShainNormalSetting> optComNormalSet = this.normalSettingRepository.find(companyId, empId, year);
		if (optComNormalSet.isPresent()) {
			dtoBuilder.normalSetting(ShainNormalSettingDto.fromDomain(optComNormalSet.get()));
		}

		Optional<ShainFlexSetting> optComFlexSet = this.flexSettingRepository.find(companyId,empId, year);
		if (optComFlexSet.isPresent()) {
			dtoBuilder.flexSetting(ShainFlexSettingDto.fromDomain(optComFlexSet.get()));
		}

		Optional<ShainDeforLaborSetting> optComDeforLaborSet = this.deforLaborSettingRepository.find(companyId,empId, year);
		if (optComDeforLaborSet.isPresent()) {
			dtoBuilder.deforLaborSetting(ShainDeforLaborSettingDto.fromDomain(optComDeforLaborSet.get()));
		}
		
		Optional<ShainTransLaborTime> optTransLaborTime = this.speDeforLaborTimeRepository.find(companyId, empId);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get().getWorkingTimeSet()));
		}

		Optional<ShainRegularLaborTime> optComRegular = this.regularWorkTimeRepository.find(companyId, empId);
		if (optComRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optComRegular.get().getWorkingTimeSet()));
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
		List<ShainRegularLaborTime> listShainRegLaborTime = this.regularWorkTimeRepository.findAll(companyId);
		
		// check list is not empty
		if(!listShainRegLaborTime.isEmpty()){			
			listShainRegWorkHourDto = listShainRegLaborTime.stream().map(domain -> ShainRegularWorkHourDto.fromDomain(domain)).collect(Collectors.toList());
		}
		
		return listShainRegWorkHourDto;
	}
	
}
