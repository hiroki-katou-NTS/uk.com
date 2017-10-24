/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.workrecord.actuallock;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.actuallock.AcLockHistSaveCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.actuallock.ActualLockHistSaveCommand;
import nts.uk.ctx.at.record.app.command.workrecord.actuallock.ActualLockSaveCommand;
import nts.uk.ctx.at.record.app.command.workrecord.actuallock.ActualLockSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.actuallock.ActualLockFindDto;
import nts.uk.ctx.at.record.app.find.workrecord.actuallock.ActualLockFinder1;
import nts.uk.ctx.at.record.app.find.workrecord.actuallock.ActualLockFinderDto;
import nts.uk.ctx.at.record.app.find.workrecord.actuallock.ActualLockHistFindDto;

/**
 * The Class ActualLockWebService.
 */
@Path("ctx/at/record/workrecord/actuallock/")
@Produces(MediaType.APPLICATION_JSON)
public class ActualLockWebService extends WebService {

	// @Inject
	// private ActualLockFinder actualLockRepo;
	//
	// @Inject
	// private ActualLockHistFinder actualLockHistRepo;

	/** The lock finder. */
	@Inject
	private ActualLockFinder1 lockFinder;
	
	/** The actual lock save handle. */
	@Inject
	private ActualLockSaveCommandHandler actualLockSaveHandle;

	/** The lock hist save handle. */
	@Inject
	private AcLockHistSaveCommandHandler lockHistSaveHandle;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<ActualLockFinderDto> findAll() {
		return this.lockFinder.findAll();
	}

	/**
	 * Find hist by target YM.
	 *
	 * @param closureId the closure id
	 * @param targetYM the target YM
	 * @return the list
	 */
	@POST
	@Path("findHistByTargetYM/{closureId}/{targetYM}")
	public List<ActualLockHistFindDto> findHistByTargetYM(@PathParam("closureId") int closureId,
			@PathParam("targetYM") Integer targetYM) {
		return this.lockFinder.findHistByTargetYM(closureId, targetYM);
	}
	
	@POST
	@Path("findHistByClosure/{closureId}/{targetYM}")
	public List<ActualLockHistFindDto> findHistByClosure(@PathParam("closureId") int closureId,
			@PathParam("targetYM") Integer targetYM) {
		return this.lockFinder.findHistByClosure(closureId, targetYM);
	}

	/**
	 * Find lock by closure id.
	 *
	 * @param closureId the closure id
	 * @return the actual lock find dto
	 */
	@POST
	@Path("findLockByClosureId/{closureId}")
	public ActualLockFindDto findLockByClosureId(@PathParam("closureId") int closureId) {
		return this.lockFinder.findById(closureId);
	}
	
	/**
	 * Save lock.
	 *
	 * @param command the command
	 */
	@POST
	@Path("saveLock")
	public void saveLock(ActualLockSaveCommand command) {
		this.actualLockSaveHandle.handle(command);
	}
	
	/**
	 * Save lock hist.
	 *
	 * @param command the command
	 */
	@POST
	@Path("saveLockHist")
	public void saveLockHist(ActualLockHistSaveCommand command) {
		this.lockHistSaveHandle.handle(command);
	}

}
