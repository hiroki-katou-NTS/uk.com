package nts.uk.ctx.bs.employee.ws.jobtitle;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.jobtitle.SaveSequenceCommandHandler;
import nts.uk.ctx.bs.employee.app.find.jobtitle.SequenceMasterFinder;
import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.SaveSequenceCommand;
import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.SequenceMasterDto;

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
	 * @param findObj the find obj
	 */
	@Path("sequence/save")
	@POST
	public void saveSequence(SaveSequenceCommand command) {
		this.saveSequenceCommandHandler.handle(command);;
	}
}
