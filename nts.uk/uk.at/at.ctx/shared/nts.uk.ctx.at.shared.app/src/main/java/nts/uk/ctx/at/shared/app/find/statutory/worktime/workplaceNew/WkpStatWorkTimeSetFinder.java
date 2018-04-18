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

import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew.WkpStatWorkTimeSetDto.WkpStatWorkTimeSetDtoBuilder;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ComStatWorkTimeSetFinder.
 */
@Stateless
public class WkpStatWorkTimeSetFinder {

	/** The normal setting repository. */
	@Inject
	private WkpNormalSettingRepository normalSettingRepository;

	/** The flex setting repository. */
	@Inject
	private WkpFlexSettingRepository flexSettingRepository;

	/** The defor labor setting repository. */
	@Inject
	private WkpDeforLaborSettingRepository deforLaborSettingRepository;

	/** The trans labor time repository. */
	@Inject
	private WkpTransLaborTimeRepository transWorkTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private WkpRegularLaborTimeRepository regularWorkTimeRepository;

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

		Optional<WkpNormalSetting> optWkpNormalSet = this.normalSettingRepository.find(companyId, wkpId, year);
		if (optWkpNormalSet.isPresent()) {
			dtoBuilder.normalSetting(WkpNormalSettingDto.fromDomain(optWkpNormalSet.get()));
		}

		Optional<WkpFlexSetting> optWkpFlexSet = this.flexSettingRepository.find(companyId, wkpId, year);
		if (optWkpFlexSet.isPresent()) {
			dtoBuilder.flexSetting(WkpFlexSettingDto.fromDomain(optWkpFlexSet.get()));
		}

		Optional<WkpDeforLaborSetting> optWkpDeforLaborSet = this.deforLaborSettingRepository.find(companyId, wkpId,
				year);
		if (optWkpDeforLaborSet.isPresent()) {
			dtoBuilder.deforLaborSetting(WkpDeforLaborSettingDto.fromDomain(optWkpDeforLaborSet.get()));
		}

		Optional<WkpTransLaborTime> optTransLaborTime = this.transWorkTimeRepository.find(companyId, wkpId);
		if (optTransLaborTime.isPresent()) {
			dtoBuilder.transLaborTime(WorkingTimeSettingDto.fromDomain(optTransLaborTime.get().getWorkingTimeSet()));
		}

		Optional<WkpRegularLaborTime> optWkpRegular = this.regularWorkTimeRepository.find(companyId, wkpId);
		if (optWkpRegular.isPresent()) {
			dtoBuilder.regularLaborTime(WorkingTimeSettingDto.fromDomain(optWkpRegular.get().getWorkingTimeSet()));
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

		// get list wkp regular labor time
		List<WkpRegularLaborTime> listWkpRegLaborTime = this.regularWorkTimeRepository.findAll(companyId);

		// check list is not empty
		if (!listWkpRegLaborTime.isEmpty()) {
			listWkpRegWorkHourDto = listWkpRegLaborTime.stream()
					.map(domain -> WkpRegularWorkHourDto.fromDomain(domain)).collect(Collectors.toList());
		}
		
		return listWkpRegWorkHourDto;

	}

}
