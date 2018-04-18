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

import nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew.EmpRegularWorkHourDto;
import nts.uk.screen.at.app.kmk004.employment.command.Kmk004EmpDeleteCommand;
import nts.uk.screen.at.app.kmk004.employment.command.Kmk004EmpDeleteCommandHandler;
import nts.uk.screen.at.app.kmk004.employment.command.Kmk004EmpSaveCommand;
import nts.uk.screen.at.app.kmk004.employment.command.Kmk004EmpSaveCommandHandler;
import nts.uk.screen.at.app.kmk004.employment.find.Kmk004EmpDto;
import nts.uk.screen.at.app.kmk004.employment.find.Kmk004EmpDtoFinder;

/**
 * The Class Kmk004EmpWebService.
 */
@Path("screen/at/kmk004/employment")
@Produces("application/json")
public class Kmk004EmpWebService {
	
	/** The find. */
	@Inject
	private Kmk004EmpDtoFinder find;

	/** The add. */
	@Inject
	private Kmk004EmpSaveCommandHandler saveHandler;

	/** The delete. */
	@Inject
	private Kmk004EmpDeleteCommandHandler delete;

	/**
	 * Finder.
	 *
	 * @param cm
	 *            the cm
	 * @return the kmk 004 dto
	 */
	@POST
	@Path("getDetails/{year}/{empCode}")
	public Kmk004EmpDto getDetails(@PathParam("year") Integer year, @PathParam("empCode") String empCode) {
		return this.find.findKmk004EmpDto(year, empCode);
	}

	/**
	 * Adds the.
	 *
	 * @param cm
	 *            the cm
	 */
	@POST
	@Path("save")
	public void add(Kmk004EmpSaveCommand cm) {
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
	public Kmk004EmpDeleteCommand delete(Kmk004EmpDeleteCommand cm) {
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
	public List<EmpRegularWorkHourDto> findAll() {
		return this.find.findAllEmpRegWorkHour();
	}

}
