/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.holiday;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.SuperHD60HConMedDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class SuperHD60HConMedFinder.
 */
@Stateless
public class SuperHD60HConMedFinder {

	/** The repository. */
	@Inject
	private SuperHD60HConMedRepository repository;
	
	@Inject
	private OutsideOTSettingRepository outSiteRepo;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public SuperHD60HConMedDto findById() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		Optional<SuperHD60HConMed> superHoliday = this.repository.findById(companyId);

		return superHoliday.map(c -> SuperHD60HConMedDto.of(c)).orElse(new SuperHD60HConMedDto());

	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<PremiumExtra60HRateDto> findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OutsideOTBRDItem> premiumExtraRates = this.outSiteRepo.findAllUseBRDItem(companyId);

		return premiumExtraRates.stream().map(d -> {
			return d.getPremiumExtra60HRates().stream().map(c -> PremiumExtra60HRateDto.of(d.getBreakdownItemNo().value, c))
											.collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());

	}
}
