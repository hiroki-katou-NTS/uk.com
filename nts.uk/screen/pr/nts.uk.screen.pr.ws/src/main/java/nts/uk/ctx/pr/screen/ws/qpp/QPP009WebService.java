/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qpp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.salarytable.SalaryTableReportService;
import nts.uk.file.pr.app.export.salarytable.query.SalaryTableReportQuery;

/**
 * The Class QPP009WebService.
 */
@Path("/screen/pr/qpp009")
@Produces("application/json")
public class QPP009WebService extends WebService {
	
	/** The export service. */
	@Inject
	private SalaryTableReportService exportService;
	
	/**
	 * Generate.
	 *
	 * @param query the query
	 * @return the export service result
	 */
	@POST
	@Path("generate")
	public ExportServiceResult generate(SalaryTableReportQuery query) {
	// TODO:
		return this.exportService.start(query);
	}

}
