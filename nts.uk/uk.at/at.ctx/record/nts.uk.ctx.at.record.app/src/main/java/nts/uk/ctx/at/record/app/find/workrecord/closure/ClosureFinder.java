/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureDetailDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureFindDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryMDto;
import nts.uk.ctx.at.record.dom.workrecord.closure.Closure;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository;
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
	public List<ClosureFindDto> findAll(){
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		//get company id
		String companyId = loginUserContext.companyId();
		
		return this.repository.getAllClosure(companyId).stream().map(closure->{
			ClosureFindDto dto = new ClosureFindDto();
			closure.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Gets the by closure.
	 *
	 * @param closureId the closure id
	 * @return the by closure
	 */
	public ClosureFindDto getByClosure(int closureId){
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		//get company id
		String companyId = loginUserContext.companyId();
		
		// call service
		Optional<Closure> closure = this.repository.getClosureById(companyId, closureId); 
		
		ClosureFindDto dto = new ClosureFindDto();
		
		List<ClosureHistory> closureHistories = this.repositoryHistory.findByClosureId(companyId,
				closureId);
		
		// exist data
		if(closure.isPresent()){

			// to data
			closure.get().setClosureHistories(closureHistories);
			closure.get().saveToMemento(dto);
			
			Optional<ClosureHistory> closureHisory = this.repositoryHistory.
					findBySelectedYearMonth(companyId, closureId,
							closure.get().getMonth().getProcessingDate().v());
			
			if(closureHisory.isPresent()){
				ClosureHistoryMDto closureSelected = new ClosureHistoryMDto();
				closureHisory.get().saveToMemento(closureSelected);
				dto.setClosureSelected(closureSelected);
			}
		}
		
		return dto;
	}
	
	/**
	 * Detail master.
	 *
	 * @param master the master
	 * @return the closure detail dto
	 */
	public ClosureDetailDto detailMaster(ClosureHistoryInDto master){
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call service
		Optional<Closure> closure = this.repository.getClosureById(companyId, master.getClosureId());

		ClosureDetailDto dto = new ClosureDetailDto();

		Optional<ClosureHistory> closureHistory = this.repositoryHistory.findByHistoryId(companyId,
				master.getClosureId(), master.getHistoryId());

		// exist data
		if (closure.isPresent()) {
			closure.get().saveToMemento(dto);
		}
		
		if(closureHistory.isPresent()){
			closureHistory.get().saveToMemento(dto);
		}
		return dto;
	}
}
