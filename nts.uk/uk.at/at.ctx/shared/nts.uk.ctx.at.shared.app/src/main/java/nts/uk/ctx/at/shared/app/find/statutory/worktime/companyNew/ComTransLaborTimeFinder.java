/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanyTransLaborHourFinder.
 */
@Stateless
public class ComTransLaborTimeFinder {

	@Inject
	private ComTransLaborTimeRepository repository;

	public ComTransLaborTimeDto find() {

		String companyId = AppContexts.user().companyId();

		Optional<ComTransLaborTime> optTransLaborTime = this.repository.find(companyId);
		ComTransLaborTimeDto dto = new ComTransLaborTimeDto();
		
		if(optTransLaborTime.isPresent()) {
			dto = ComTransLaborTimeDto.fromDomain(optTransLaborTime.get());
		}
		return dto;
	}

}
