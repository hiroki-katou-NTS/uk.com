/**
 * 
 */
package nts.uk.ctx.at.record.ws.workrecord.temporarywork;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.temporarywork.ManageWorkTemporaryCommand;
import nts.uk.ctx.at.record.app.command.workrecord.temporarywork.ManageWorkTemporaryCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.temporarywork.ManageWorkTemporaryDto;
import nts.uk.ctx.at.record.app.find.workrecord.temporarywork.ManageWorkTemporaryFinder;

/**
 * The Class ManageWorkTemporary.
 *
 * @author hoangdd
 */
@Path("at/record/workrecord/temporarywork")
@Produces(MediaType.APPLICATION_JSON)
public class ManageWorkTemporaryWS extends WebService{
	
	/** The finder. */
	@Inject
	private ManageWorkTemporaryFinder finder;
	
	/** The save handler. */
	@Inject
	private ManageWorkTemporaryCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the manage work temporary dto
	 */
	@Path("find")
	@POST
	public ManageWorkTemporaryDto findData() {
		return this.finder.findData();
	}
	
	/**
	 * Save data.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveData(ManageWorkTemporaryCommand command) {
		this.saveHandler.handle(command);
	}
}

