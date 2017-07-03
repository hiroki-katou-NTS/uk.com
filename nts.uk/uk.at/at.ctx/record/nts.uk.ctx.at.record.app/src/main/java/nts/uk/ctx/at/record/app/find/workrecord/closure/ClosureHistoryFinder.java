/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryHeaderDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.record.dom.workrecord.closure.Closure;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository;
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
	
	/** The repository history. */
	@Inject
	private ClosureHistoryRepository repositoryHistory;
	
	

	public List<ClosureHistoryFindDto> getAllClosureHistory() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all closure
		List<Closure> closures = this.repository.getAllClosure(companyId);
		
		// get data
		List<ClosureHistory> closureHistories = new ArrayList<>();
		
		closures.forEach(closure->{
			Optional<ClosureHistory> closureHistoryLast = this.repositoryHistory.findBySelectedYearMonth(
					companyId, closure.getClosureId(), closure.getMonth().getProcessingDate().v());
			
			if(closureHistoryLast.isPresent()){
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
	 * Detail.
	 *
	 * @param master the master
	 * @return the closure history D dto
	 */
	public ClosureHistoryHeaderDto detail(ClosureHistoryInDto master){
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		Optional<ClosureHistory> historyHistory = this.repositoryHistory.findByHistoryId(companyId,
				master.getClosureId(), master.getHistoryId());
		
		ClosureHistoryHeaderDto dto = new ClosureHistoryHeaderDto();
		if(historyHistory.isPresent()){
			historyHistory.get().saveToMemento(dto);
		}
		return dto;
	}
	
}
