/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.ws.systemresource;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.systemresource.SystemResourceSaveCommand;
import nts.uk.screen.com.app.command.systemresource.SystemResourceSaveCommandHandler;
import nts.uk.screen.com.app.find.systemresource.SystemResourceFinder;
import nts.uk.screen.com.app.systemresource.dto.SystemResourceDto;

/**
 * The Class SystemResourceWs.
 */
@Path("screen/com/systemresource")
@Produces("application/json")
public class SystemResourceWs extends WebService {
	
	/** The system resource save handler. */
	@Inject
	private SystemResourceSaveCommandHandler systemResourceSaveHandler;
	
	/** The finder. */
	@Inject
	private SystemResourceFinder finder;
		
	/**
	 * Find list system resource.
	 *
	 * @return the list
	 */
	@POST
	@Path("findList")
	public List<SystemResourceDto> findListSystemResource(){
		return this.finder.findList();
	}
	
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
