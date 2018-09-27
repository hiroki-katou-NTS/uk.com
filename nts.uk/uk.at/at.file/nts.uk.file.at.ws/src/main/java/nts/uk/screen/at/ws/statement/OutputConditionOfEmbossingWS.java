/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws.statement;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingExportService;
import nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingQuery;

/**
 * The Class OutputConditionOfEmbossingWS.
 */
@Path("screen/at/statement")
@Produces("application/json")
public class OutputConditionOfEmbossingWS extends WebService{
	
	/** The service. */
	@Inject
	OutputConditionOfEmbossingExportService service;
	
	/**
	 * Export data.
	 *
	 * @param dto the dto
	 * @return the export service result
	 */
	@POST
	@Path("export")
	public ExportServiceResult exportData(OutputConditionOfEmbossingQuery dto) {
		return service.start(dto);
	}
}
