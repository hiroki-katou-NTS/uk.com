package nts.uk.ctx.bs.employee.ws.jobtitle;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.RemoveSequenceCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.RemoveSequenceCommandHandler;
import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.SaveSequenceCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.SaveSequenceCommandHandler;
import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.dto.SequenceMasterDto;
import nts.uk.ctx.bs.employee.app.find.jobtitle.sequence.SequenceMasterFinder;

/**
 * The Class JobTitleWebService.
 */
@Path("bs/employee/jobtitle")
@Produces(MediaType.APPLICATION_JSON)
public class JobTitleWebService extends WebService {

	/** The sequence master finder. */
	@Inject
	private SequenceMasterFinder sequenceMasterFinder;
	
	/** The save sequence command handler. */
	@Inject
	private SaveSequenceCommandHandler saveSequenceCommandHandler;
	
	/** The Remove sequence command handler. */
	@Inject
	private RemoveSequenceCommandHandler removeSequenceCommandHandler;
	
	/**
	 * Find max order.
	 *
	 * @return the short
	 */
	@Path("sequence/maxOrder")
	@POST
	public short findMaxOrder() {
		return this.sequenceMasterFinder.findMaxOrder();
	}
	
	/**
	 * Find all sequence.
	 *
	 * @return the list
	 */
	@Path("sequence/findAll")
	@POST
	public List<SequenceMasterDto> findAllSequence() {
		return this.sequenceMasterFinder.findAll();
	}
	
	/**
	 * Find sequence by sequence code.
	 *
	 * @param findObj the find obj
	 * @return the sequence master dto
	 */
	@Path("sequence/find")
	@POST
	public SequenceMasterDto findSequenceBySequenceCode(SequenceMasterDto findObj) {
		return this.sequenceMasterFinder.findSequenceBySequenceCode(findObj.getSequenceCode());
	}
	
	/**
	 * Save sequence.
	 *
	 * @param command the command
	 */
	@Path("sequence/save")
	@POST
	public void saveSequence(SaveSequenceCommand command) {
		this.saveSequenceCommandHandler.handle(command);;
	}
	
	/**
	 * Removes the sequence.
	 *
	 * @param command the command
	 */
	@Path("sequence/remove")
	@POST
	public void removeSequence(RemoveSequenceCommand command) {
		this.removeSequenceCommandHandler.handle(command);;
	}
}
