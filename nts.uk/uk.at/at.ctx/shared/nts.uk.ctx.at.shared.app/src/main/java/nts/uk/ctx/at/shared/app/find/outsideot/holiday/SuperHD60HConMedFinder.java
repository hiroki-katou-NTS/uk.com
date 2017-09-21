/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.holiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.outsideot.dto.SuperHD60HConMedDto;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHD60HConMedRepository;
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

		
		SuperHD60HConMedDto dto = new SuperHD60HConMedDto();
		
		dto.setSetting(false);
		
		// check exist data
		if(superHoliday.isPresent()){
			superHoliday.get().saveToMemento(dto);
			dto.setSetting(true);
		}
		
		return dto;

	}
}
