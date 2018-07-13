package nts.uk.ctx.at.record.ws.workrecord.erroralarm.otkcustomize;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSetSaveCommand;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSetSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSetFinder;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.otkcustomize.OtkWorkTypeDto;

/**
 * @author tuannt-nws
 *
 */
@Path("at/record/erroralarm/otkcustomize")
@Produces("application/json")
public class ContinuousHolCheckSetWebservice {
	
	/** The save command. */
	@Inject
	private ContinuousHolCheckSetSaveCommandHandler continuousHolCheckSetSaveCommandHandler;
	
	/** The finder. */
	@Inject
	private ContinuousHolCheckSetFinder continuousHolCheckSetFinder;
	

	
	/**
	 * Gets the continuous hol check set.
	 *
	 * @return the continuous hol check set
	 */
	@POST
	@Path("findContinuousHolCheckSet")
	public ContinuousHolCheckSetDto getContinuousHolCheckSet() {
		return this.continuousHolCheckSetFinder.findContinousHolCheckSet();
	}
	
	/**
	 * Save continuous hol check set.
	 *
	 * @param continuousHolCheckSetSaveCommand the continuous hol check set save command
	 */
	@POST
	@Path("saveContinuousHolCheckSet")
	public void saveContinuousHolCheckSet(ContinuousHolCheckSetSaveCommand continuousHolCheckSetSaveCommand) {
			this.continuousHolCheckSetSaveCommandHandler.handle(continuousHolCheckSetSaveCommand);
	}
	
	/**
	 * Find all work type dto.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAllWorkTypeDto")
	public List<OtkWorkTypeDto> findAllWorkTypeDto(){
		return this.continuousHolCheckSetFinder.findAllWorkType();
	}
	
}
