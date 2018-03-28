/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew.ComStatWorkTimeSetDto.ComStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ComStatWorkTimeSetFinder.
 */
@Stateless
public class ComStatWorkTimeSetFinder {

	/** The trans labor time repository. */
	@Inject
	private ComTransLaborTimeRepository transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private ComRegularLaborTimeRepository regularLaborTimeRepository;

	/** The normal setting repository. */
	@Inject
	private ComNormalSettingRepository normalSettingRepository;

	/** The flex setting repository. */
	@Inject
	private ComFlexSettingRepository flexSettingRepository;

	/** The defor labor setting repository. */
	@Inject
	private ComDeforLaborSettingRepository deforLaborSettingRepository;

	/**
	 * Gets the details.
	 *
	 * @param year
	 *            the year
	 * @return the details
	 */
	public ComStatWorkTimeSetDto getDetails(Integer year) {

		String companyId = AppContexts.user().companyId();
		ComStatWorkTimeSetDtoBuilder dtoBuilder = ComStatWorkTimeSetDto.builder();
		
		dtoBuilder.year(year);

		Optional<ComTransLaborTime> optTransLaborTime = this.transLaborTimeRepository.find(companyId);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get().getWorkingTimeSet()));
		}

		Optional<ComRegularLaborTime> optComRegular = this.regularLaborTimeRepository.find(companyId);
		if (optComRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optComRegular.get().getWorkingTimeSet()));
		}

		Optional<ComNormalSetting> optComNormalSet = this.normalSettingRepository.find(companyId, year);
		if (optComNormalSet.isPresent()) {
			dtoBuilder.normalSetting(ComNormalSettingDto.fromDomain(optComNormalSet.get()));
		}

		Optional<ComFlexSetting> optComFlexSet = this.flexSettingRepository.find(companyId, year);
		if (optComFlexSet.isPresent()) {
			dtoBuilder.flexSetting(ComFlexSettingDto.fromDomain(optComFlexSet.get()));
		}

		Optional<ComDeforLaborSetting> optComDeforLaborSet = this.deforLaborSettingRepository.find(companyId, year);
		if (optComDeforLaborSet.isPresent()) {
			dtoBuilder.deforLaborSetting(ComDeforLaborSettingDto.fromDomain(optComDeforLaborSet.get()));
		}

		return dtoBuilder.build();
	}

}
