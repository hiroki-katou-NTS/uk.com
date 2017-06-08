/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureHistoryFinder.
 */
@Stateless
public class ClosureHistoryFinder {

	/** The repository. */
	@Inject
	private ClosureHistoryRepository repository;

	public List<ClosureHistoryFindDto> getAllClosureHistory() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// domain to dto
		return this.repository.findByCompanyId(companyId).stream().map(closureHistory -> {
			ClosureHistoryFindDto dto = new ClosureHistoryFindDto();
			closureHistory.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
