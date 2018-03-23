/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComDeleteCommand;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComDeleteCommandHandler;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComSaveCommand;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComSaveCommandHandler;
import nts.uk.screen.at.app.kmk004.company.find.Kmk004ComDto;
import nts.uk.screen.at.app.kmk004.company.find.Kmk004ComDtoFinder;

/**
 * The Class Kmk004WebService.
 */
@Path("screen/at/kmk004/company")
@Produces("application/json")
public class Kmk004ComWebService extends WebService {

	/** The find. */
	@Inject
	private Kmk004ComDtoFinder find;

	/** The add. */
	@Inject
	private Kmk004ComSaveCommandHandler saveHandler;

	/** The delete. */
	@Inject
	private Kmk004ComDeleteCommandHandler delete;

	/**
	 * Finder.
	 *
	 * @param cm
	 *            the cm
	 * @return the kmk 004 dto
	 */
	@POST
	@Path("getDetails/{year}")
	public Kmk004ComDto getDetails(@PathParam("year") Integer year) {
		return this.find.findKmk004Dto(year);
	}

	/**
	 * Adds the.
	 *
	 * @param cm
	 *            the cm
	 */
	@POST
	@Path("save")
	public void add(Kmk004ComSaveCommand cm) {
		this.saveHandler.handle(cm);
	}

	/**
	 * Delete.
	 *
	 * @param cm
	 *            the cm
	 */
	@POST
	@Path("delete")
	public void delete(Kmk004ComDeleteCommand cm) {
		this.delete.handle(cm);
	}

}
