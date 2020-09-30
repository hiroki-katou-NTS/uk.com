/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew.WkpStatWorkTimeSetDto.WkpStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ComStatWorkTimeSetFinder.
 */
@Stateless
public class WkpStatWorkTimeSetFinder {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeWkpRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param year
	 *            the year
	 * @return the details
	 */
	public WkpStatWorkTimeSetDto getDetails(Integer year, String wkpId) {

		String companyId = AppContexts.user().companyId();
		WkpStatWorkTimeSetDtoBuilder dtoBuilder = WkpStatWorkTimeSetDto.builder();
		
		dtoBuilder.year(year);
		dtoBuilder.workplaceId(wkpId);

		Optional<DeforLaborTimeWkp> optTransLaborTime = this.transLaborTimeRepository.find(companyId, wkpId);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get()));
		}

		Optional<RegularLaborTimeWkp> optComRegular = this.regularLaborTimeRepository.find(companyId, wkpId);
		if (optComRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optComRegular.get()));
		}

		val regularSet = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		if (!regularSet.isEmpty()) {
			dtoBuilder.normalSetting(WkpNormalSettingDto.with(companyId, wkpId, year, regularSet));
		}
		
		val flexSet = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.FLEX, year);
		if (!flexSet.isEmpty()) {
			dtoBuilder.flexSetting(WkpFlexSettingDto.with(companyId, wkpId, year, flexSet));
		}

		val deforSet = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		if (!deforSet.isEmpty()) {
			dtoBuilder.deforLaborSetting(WkpDeforLaborSettingDto.with(year, companyId, wkpId, deforSet));
		}

		return dtoBuilder.build();
	}

	/**
	 * Find all wkp reg work hour dto.
	 *
	 * @return the list
	 */
	public List<WkpRegularWorkHourDto> findAllWkpRegWorkHourDto() {

		// get company id
		String companyId = AppContexts.user().companyId();
		List<WkpRegularWorkHourDto> listWkpRegWorkHourDto = new ArrayList<>();

		
		// get list employee regular labor time
		List<RegularLaborTimeWkp> listWkpRegLaborTime = this.regularLaborTimeRepository.findAll(companyId);
		
		// check list is not empty
		if(!listWkpRegLaborTime.isEmpty()){			
			listWkpRegWorkHourDto = listWkpRegLaborTime.stream()
					.map(domain -> WkpRegularWorkHourDto.fromDomain(domain))
					.collect(Collectors.toList());
		}
		
		return listWkpRegWorkHourDto;

	}

}
