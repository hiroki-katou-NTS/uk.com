package nts.uk.ctx.at.shared.ws.entranceexit;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.entranceexit.ManageEntryExitCommand;
import nts.uk.ctx.at.shared.app.command.entranceexit.ManageEntryExitCommandHandler;
import nts.uk.ctx.at.shared.app.find.entranceexit.ManageEntryExitDto;
import nts.uk.ctx.at.shared.app.find.entranceexit.ManageEntryExitFinder;

/**
 * The Class ManageEntryExitWS.
 *
 * @author hoangdd
 */
@Path("at/shared/workrecord/entranceexit")
@Produces(MediaType.APPLICATION_JSON)
public class ManageEntryExitWS extends WebService{
	
	/** The finder. */
	@Inject
	private ManageEntryExitFinder finder;
	
	/** The save handler. */
	@Inject
	private ManageEntryExitCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the manage entry exit dto
	 */
	@Path("find")
	@POST
	public ManageEntryExitDto findData() {
		return this.finder.findData();
	}
	
	/**
	 * Save data.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveData(ManageEntryExitCommand command) {
		this.saveHandler.handle(command);
	}
}

