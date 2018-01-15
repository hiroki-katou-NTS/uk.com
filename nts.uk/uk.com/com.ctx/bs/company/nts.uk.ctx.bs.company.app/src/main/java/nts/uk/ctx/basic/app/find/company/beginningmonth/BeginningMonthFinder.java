/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.beginningmonth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonth;
import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class BeginningMonthFinder.
 */
@Stateless
public class BeginningMonthFinder {

	/** The repository. */
	@Inject
	private BeginningMonthRepository repository;

	/**
	 * Find.
	 *
	 * @return the beginning month dto
	 */
	public BeginningMonthDto find() {
		// Get beginning month.
		Optional<BeginningMonth> domain = this.repository.find(AppContexts.user().companyId());

		// Convert to dto.
		BeginningMonthDto dto = new BeginningMonthDto();
		if (domain.isPresent()) {
			domain.get().saveToMemento(dto);
		}
		return dto;
	}

}
