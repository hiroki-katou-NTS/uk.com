/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew.WkpRegularWorkHourDto;
import nts.uk.screen.at.app.kmk004.workplace.command.Kmk004WkpDeleteCommand;
import nts.uk.screen.at.app.kmk004.workplace.command.Kmk004WkpDeleteCommandHandler;
import nts.uk.screen.at.app.kmk004.workplace.command.Kmk004WkpSaveCommand;
import nts.uk.screen.at.app.kmk004.workplace.command.Kmk004WkpSaveCommandHandler;
import nts.uk.screen.at.app.kmk004.workplace.find.Kmk004WkpDto;
import nts.uk.screen.at.app.kmk004.workplace.find.Kmk004WkpDtoFinder;

/**
 * The Class Kmk004WkpWebService.
 */
@Path("screen/at/kmk004/workplace")
@Produces("application/json")
public class Kmk004WkpWebService {
	
	/** The find. */
	@Inject
	private Kmk004WkpDtoFinder find;

	/** The add. */
	@Inject
	private Kmk004WkpSaveCommandHandler saveHandler;

	/** The delete. */
	@Inject
	private Kmk004WkpDeleteCommandHandler delete;

	/**
	 * Finder.
	 *
	 * @param cm
	 *            the cm
	 * @return the kmk 004 dto
	 */
	@POST
	@Path("getDetails/{year}/{wkpId}")
	public Kmk004WkpDto getDetails(@PathParam("year") Integer year, @PathParam("wkpId") String wkpId) {
		return this.find.findKmk004WkpDto(year, wkpId);
	}

	/**
	 * Adds the.
	 *
	 * @param cm
	 *            the cm
	 */
	@POST
	@Path("save")
	public void add(Kmk004WkpSaveCommand cm) {
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
	public Kmk004WkpDeleteCommand delete(Kmk004WkpDeleteCommand cm) {
		this.delete.handle(cm);
		return cm;
    }
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<WkpRegularWorkHourDto> findAll() {
		return this.find.findAllWkpRegWorkHourDto();
	}
}
