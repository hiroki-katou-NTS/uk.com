/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryHeaderDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureHistoryFinder.
 */
@Stateless
public class ClosureHistoryFinder {

	/** The repository. */
	@Inject
	private ClosureRepository repository;


	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ClosureHistoryFindDto> findAll() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all closure
		List<Closure> closures = this.repository.findAll(companyId);

		// get data
		List<ClosureHistory> closureHistories = new ArrayList<>();

		closures.forEach(closure -> {
			Optional<ClosureHistory> closureHistoryLast = this.repository.findBySelectedYearMonth(companyId,
					closure.getClosureId().value, closure.getClosureMonth().getProcessingYm().v());

			if (closureHistoryLast.isPresent()) {
				closureHistories.add(closureHistoryLast.get());
			}
		});

		// domain to data
		return closureHistories.stream().map(closureHistory -> {
			ClosureHistoryFindDto dto = new ClosureHistoryFindDto();
			closureHistory.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Find by id.
	 *
	 * @param master the master
	 * @return the closure history header dto
	 */
	public ClosureHistoryHeaderDto findById(ClosureHistoryInDto master) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find closure history
		Optional<ClosureHistory> closureHistory = this.repository.findById(companyId, master.getClosureId(),
				master.getStartDate());

		// return data
		ClosureHistoryHeaderDto dto = new ClosureHistoryHeaderDto();
		if (closureHistory.isPresent()) {
			closureHistory.get().saveToMemento(dto);
		}
		return dto;
	}

}
