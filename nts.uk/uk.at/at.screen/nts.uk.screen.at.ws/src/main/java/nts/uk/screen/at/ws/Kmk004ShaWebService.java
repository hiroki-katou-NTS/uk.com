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

import nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew.ShainRegularWorkHourDto;
import nts.uk.screen.at.app.kmk004.employee.command.Kmk004ShaDeleteCommand;
import nts.uk.screen.at.app.kmk004.employee.command.Kmk004ShaDeleteCommandHandler;
import nts.uk.screen.at.app.kmk004.employee.command.Kmk004ShaSaveCommand;
import nts.uk.screen.at.app.kmk004.employee.command.Kmk004ShaSaveCommandHandler;
import nts.uk.screen.at.app.kmk004.employee.find.Kmk004ShaDto;
import nts.uk.screen.at.app.kmk004.employee.find.Kmk004ShaDtoFinder;

/**
 * The Class Kmk004ShaWebService.
 */
@Path("screen/at/kmk004/employee")
@Produces("application/json")
public class Kmk004ShaWebService {
	
	/** The find. */
	@Inject
	private Kmk004ShaDtoFinder find;

	/** The add. */
	@Inject
	private Kmk004ShaSaveCommandHandler saveHandler;

	/** The delete. */
	@Inject
	private Kmk004ShaDeleteCommandHandler delete;

	/**
	 * Finder.
	 *
	 * @param cm
	 *            the cm
	 * @return the kmk 004 dto
	 */
	@POST
	@Path("getDetails/{year}/{sid}")
	public Kmk004ShaDto getDetails(@PathParam("year") Integer year, @PathParam("sid") String sid) {
		return this.find.findKmk004ShaDto(year, sid);
	}

	/**
	 * Adds the.
	 *
	 * @param cm
	 *            the cm
	 */
	@POST
	@Path("save")
	public void add(Kmk004ShaSaveCommand cm) {
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
	public Kmk004ShaDeleteCommand delete(Kmk004ShaDeleteCommand cm) {
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
	public List<ShainRegularWorkHourDto> findAll() {
		return this.find.findAllShainRegLaborTime();
	}
}
