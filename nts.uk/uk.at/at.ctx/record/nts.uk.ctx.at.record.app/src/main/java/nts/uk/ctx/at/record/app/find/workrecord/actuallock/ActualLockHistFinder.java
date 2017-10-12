/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.actuallock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistory;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ActualLockHistFinder.
 */
@Stateless
public class ActualLockHistFinder {

	/** The repository. */
	@Inject
	private ActualLockHistoryRepository repository;
	
	/** The login user context. */
	private LoginUserContext loginUserContext = AppContexts.user();

	/** The company id. */
	private String companyId = loginUserContext.companyId();
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ActualLockHistFindDto> findAll() {

		// FindAll ActualLock
		List<ActualLockHistory> actualLockHistList = this.repository.findAll(companyId);

		// Convert to Dto
		return actualLockHistList.stream().map(actualLockHist -> {
			ActualLockHistFindDto dto = new ActualLockHistFindDto();
			actualLockHist.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find by closure id.
	 *
	 * @param closureId the closure id
	 * @return the list
	 */
	public List<ActualLockHistFindDto> findByClosureId(int closureId) {
		// FindAll ActualLock By ClosureId
		List<ActualLockHistory> actualLockHistList = this.repository.findByClosureId(companyId, closureId);

		// Convert to Dto
		return actualLockHistList.stream().map(actualLockHist -> {
			ActualLockHistFindDto dto = new ActualLockHistFindDto();
			actualLockHist.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find by lock date.
	 *
	 * @param closureId the closure id
	 * @param lockDate the lock date
	 * @return the actual lock hist find dto
	 */
	public ActualLockHistFindDto findByLockDate(int closureId, GeneralDateTime lockDate) {
		// New ActualLockHistFindDto
		ActualLockHistFindDto dto = new ActualLockHistFindDto();
		
		// Find ActualLockHistory By ClosureId
		Optional<ActualLockHistory> actualLockHistOpt = this.repository.findByLockDate(companyId, closureId, lockDate);
		if (!actualLockHistOpt.isPresent()) {
			return null;
		}
		// Save To Memento
		actualLockHistOpt.get().saveToMemento(dto);
		return dto;
	} 
	
}
