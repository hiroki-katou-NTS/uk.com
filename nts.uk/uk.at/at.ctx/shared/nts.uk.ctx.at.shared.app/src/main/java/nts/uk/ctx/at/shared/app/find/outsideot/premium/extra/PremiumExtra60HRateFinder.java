/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.premium.extra;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;
import nts.uk.ctx.at.shared.dom.outsideot.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.outsideot.premium.extra.PremiumExtra60HRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class PremiumExtra60HRateFinder.
 */
@Stateless
public class PremiumExtra60HRateFinder {
	
	/** The repository. */
	@Inject
	private PremiumExtra60HRateRepository repository;
	
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
		List<PremiumExtra60HRate> premiumExtraRates = this.repository.findAll(companyId);

		return premiumExtraRates.stream().map(domain -> {
			PremiumExtra60HRateDto dto = new PremiumExtra60HRateDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
	
	
}
