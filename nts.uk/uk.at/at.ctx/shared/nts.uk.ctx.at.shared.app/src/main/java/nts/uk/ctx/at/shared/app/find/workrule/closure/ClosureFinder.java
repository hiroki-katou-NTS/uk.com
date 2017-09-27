/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDetailDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryMasterDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureFinder.
 */
@Stateless
public class ClosureFinder {

	/** The repository. */
	@Inject
	private ClosureRepository repository;

	/** The repository history. */
	@Inject
	private ClosureHistoryRepository repositoryHistory;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ClosureFindDto> findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		return this.repository.findAll(companyId).stream().map(closure -> {
			ClosureFindDto dto = new ClosureFindDto();
			closure.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Find by id.
	 *
	 * @param closureId
	 *            the closure id
	 * @return the closure find dto
	 */
	public ClosureFindDto findById(int closureId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call service
		Optional<Closure> closure = this.repository.findById(companyId, closureId);

		ClosureFindDto dto = new ClosureFindDto();

		List<ClosureHistory> closureHistories = this.repositoryHistory.findByClosureId(companyId,
				closureId);

		// exist data
		if (closure.isPresent()) {

			// to data
			closure.get().setClosureHistories(closureHistories);
			closure.get().saveToMemento(dto);

			Optional<ClosureHistory> closureHisory = this.repositoryHistory.findBySelectedYearMonth(
					companyId, closureId, closure.get().getClosureMonth().getProcessingYm().v());

			if (closureHisory.isPresent()) {
				ClosureHistoryMasterDto closureSelected = new ClosureHistoryMasterDto();
				closureHisory.get().saveToMemento(closureSelected);
				dto.setClosureSelected(closureSelected);
			}
		}

		return dto;
	}

	/**
	 * Detail master.
	 *
	 * @param master
	 *            the master
	 * @return the closure detail dto
	 */
	public ClosureDetailDto findByMaster(ClosureHistoryInDto master) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call service
		Optional<Closure> closure = this.repository.findById(companyId, master.getClosureId());

		ClosureDetailDto dto = new ClosureDetailDto();

		Optional<ClosureHistory> closureHistory = this.repositoryHistory.findById(companyId,
				master.getClosureId(), master.getStartDate());

		// exist data
		if (closure.isPresent()) {
			closure.get().saveToMemento(dto);
		}

		if (closureHistory.isPresent()) {
			closureHistory.get().saveToMemento(dto);
		}

		return dto;
	}

}
