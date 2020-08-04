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

import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew.EmpStatWorkTimeSetDto.EmpStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpStatWorkTimeSetFinder.
 */
@Stateless
public class EmpStatWorkTimeSetFinder {

	/** The normal setting repository. */
	@Inject
	private EmpNormalSettingRepository normalSettingRepository;

	/** The flex setting repository. */
	@Inject
	private EmpFlexSettingRepository flexSettingRepository;

	/** The defor labor setting repository. */
	@Inject
	private EmpDeforLaborSettingRepository deforLaborSettingRepository;

	/** The trans work time repository. */
	@Inject
	private EmpTransWorkTimeRepository transWorkTimeRepository;

	/** The regular work time repository. */
	@Inject
	private EmpRegularWorkTimeRepository regularWorkTimeRepository;

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

		Optional<EmpNormalSetting> optEmpNormalSet = this.normalSettingRepository.find(companyId, emplCode, year);
		if (optEmpNormalSet.isPresent()) {
			dtoBuilder.normalSetting(EmpNormalSettingDto.fromDomain(optEmpNormalSet.get()));
		}

		Optional<EmpFlexSetting> optEmpFlexSet = this.flexSettingRepository.find(companyId, emplCode, year);
		if (optEmpFlexSet.isPresent()) {
			dtoBuilder.flexSetting(EmpFlexSettingDto.fromDomain(optEmpFlexSet.get()));
		}

		Optional<EmpDeforLaborSetting> optEmpDeforLaborSet = this.deforLaborSettingRepository.find(companyId, emplCode,
				year);
		if (optEmpDeforLaborSet.isPresent()) {
			dtoBuilder.deforLaborSetting(EmpDeforLaborSettingDto.fromDomain(optEmpDeforLaborSet.get()));
		}

		Optional<EmpTransLaborTime> optTransLaborTime = this.transWorkTimeRepository.find(companyId, emplCode);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get().getWorkingTimeSet()));
		}

		Optional<EmpRegularLaborTime> optEmpRegular = this.regularWorkTimeRepository.findById(companyId, emplCode);
		if (optEmpRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optEmpRegular.get().getWorkingTimeSet()));
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
		List<EmpRegularWorkHourDto> listEmpRegWorkHourDto = new ArrayList<>();

		// get list employment regular labor time
		List<EmpRegularLaborTime> listEmpRegLaborTime = this.regularWorkTimeRepository.findListByCid(companyId);

		// check list is not empty
		if (!listEmpRegLaborTime.isEmpty()) {
			listEmpRegWorkHourDto = listEmpRegLaborTime.stream().map(domain -> EmpRegularWorkHourDto.fromDomain(domain))
					.collect(Collectors.toList());
		}

		return listEmpRegWorkHourDto;
	}

}
