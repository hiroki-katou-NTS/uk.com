/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew.ComStatWorkTimeSetDto.ComStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ComStatWorkTimeSetFinder.
 */
@Stateless
public class ComStatWorkTimeSetFinder {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeComRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeComRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

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

		Optional<DeforLaborTimeCom> optTransLaborTime = this.transLaborTimeRepository.find(companyId);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get()));
		}

		Optional<RegularLaborTimeCom> optComRegular = this.regularLaborTimeRepository.find(companyId);
		if (optComRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optComRegular.get()));
		}

		val regularSet = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		if (!regularSet.isEmpty()) {
			dtoBuilder.normalSetting(ComNormalSettingDto.with(year, regularSet));
		}
		
		val flexSet = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.FLEX, year);
		if (!flexSet.isEmpty()) {
			dtoBuilder.flexSetting(ComFlexSettingDto.with(year, flexSet));
		}

		val deforSet = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		if (!deforSet.isEmpty()) {
			dtoBuilder.deforLaborSetting(ComDeforLaborSettingDto.with(year, companyId, deforSet));
		}

		return dtoBuilder.build();
	}

}
