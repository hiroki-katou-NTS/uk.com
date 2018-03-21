/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComAddCommand;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComAddCommandHandler;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComDeleteCommand;
import nts.uk.screen.at.app.kmk004.company.command.Kmk004ComDeleteCommandHandler;
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
	private Kmk004ComAddCommandHandler add;

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
	@Path("find")
	public Kmk004ComDto finder(Kmk004ComDto cm) {
		return this.find.findKmk004Dto();
	}

	/**
	 * Adds the.
	 *
	 * @param cm
	 *            the cm
	 */
	@POST
	@Path("add")
	public void add(Kmk004ComAddCommand cm) {
		this.add.handle(cm);
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
