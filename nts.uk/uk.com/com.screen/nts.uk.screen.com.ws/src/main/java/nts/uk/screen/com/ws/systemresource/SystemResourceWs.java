/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.ws.systemresource;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.systemresource.SystemResourceSaveCommand;
import nts.uk.screen.com.app.command.systemresource.SystemResourceSaveCommandHandler;

/**
 * The Class SystemResourceWs.
 */
@Path("screen/com/systemresource")
@Produces("application/json")
public class SystemResourceWs extends WebService {
	
	/** The system resource save handler. */
	@Inject
	private SystemResourceSaveCommandHandler systemResourceSaveHandler;
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(SystemResourceSaveCommand command) {
		this.systemResourceSaveHandler.handle(command);
	}
}
