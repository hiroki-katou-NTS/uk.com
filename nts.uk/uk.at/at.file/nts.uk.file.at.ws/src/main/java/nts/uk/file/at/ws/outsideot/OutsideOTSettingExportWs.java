/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.ws.outsideot;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.outsideot.OutsideOTSettingExportService;
import nts.uk.file.at.app.outsideot.OutsideOTSettingQuery;

/**
 * The Class OutsideOTSettingExportWs.
 */
@Path("at/shared/outsideot/export")
@Produces(MediaType.APPLICATION_JSON)
public class OutsideOTSettingExportWs extends WebService{

	/** The service. */
	@Inject
	private OutsideOTSettingExportService service;
	
	/**
	 * Export excel.
	 *
	 * @param query the query
	 * @return the export service result
	 */
    @POST
    @Path("excel")
    public ExportServiceResult exportExcel(OutsideOTSettingQuery query) {
        return this.service.start(query);
    }
}
