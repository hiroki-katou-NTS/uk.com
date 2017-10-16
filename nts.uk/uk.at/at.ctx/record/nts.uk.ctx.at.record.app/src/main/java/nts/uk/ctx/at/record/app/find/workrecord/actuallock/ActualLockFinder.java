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

import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ActualLockFinder.
 */
@Stateless
public class ActualLockFinder {

	/** The repository. */
	@Inject
	private ActualLockRepository repository;
	
	/** The login user context. */
	private LoginUserContext loginUserContext = AppContexts.user();

	/** The company id. */
	private String companyId = loginUserContext.companyId();
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ActualLockFindDto> findAll() {
		
		// FindAll ActualLock
		List<ActualLock> actualLockList = this.repository.findAll(companyId);
		
		// Convert to Dto
		return actualLockList.stream().map(actualLock -> {
			ActualLockFindDto dto = new ActualLockFindDto();
			actualLock.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find by id.
	 *
	 * @param closureId the closure id
	 * @return the actual lock find dto
	 */
	public ActualLockFindDto findById(int closureId) {
		ActualLockFindDto dto = new ActualLockFindDto();
		
		// Find ActualLock By ClosureId
		Optional<ActualLock> actualLockOpt = this.repository.findById(companyId, closureId);
		if (!actualLockOpt.isPresent()) {
			return null;
		}
		// Save To Memento
		actualLockOpt.get().saveToMemento(dto);
		return dto;
	}
}
