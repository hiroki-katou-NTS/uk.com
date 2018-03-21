/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanyRegularLaborHourFinder.
 */
@Stateless
public class ComRegularLaborTimeFinder {

	@Inject
	private ComRegularLaborTimeRepository repository;
	
	public ComRegularLaborTimeDto find() {

		String companyId = AppContexts.user().companyId();

		Optional<ComRegularLaborTime> optComRegular = this.repository.find(companyId);
		ComRegularLaborTimeDto dto = new ComRegularLaborTimeDto();

		if (optComRegular.isPresent()) {
			dto = ComRegularLaborTimeDto.fromDomain(optComRegular.get());
		}
		return dto;

	}

}
