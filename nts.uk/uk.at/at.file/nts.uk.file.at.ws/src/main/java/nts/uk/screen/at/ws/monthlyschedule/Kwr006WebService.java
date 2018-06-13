/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws.monthlyschedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleQuery;

/**
 * The Class Kwr006WebService.
 */
@Path("screen/at/monthlyschedule")
@Produces("application/json")
public class Kwr006WebService extends WebService {

	/** The service. */
	@Inject
	MonthlyPerformanceExportService service;

	/**
	 * Export data.
	 *
	 * @param query the query
	 * @return the export service result
	 */
	@POST
	@Path("export")
	public ExportServiceResult exportData(MonthlyWorkScheduleQuery query) {
		return service.start(query);
	}
}
