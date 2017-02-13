/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qet;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.screen.ws.qet.dto.WageLedgerReportDto;

/**
 * The Class QET001Webservice.
 */
@Path("/screen/pr/qet001")
@Produces("application/json")
public class QET001Webservice extends WebService{
	
	
	@POST
	@Path("print")
	public FileGeneratorContext printReport(WageLedgerReportDto printDto) {
		// TODO: validate print dto.
		return null;
	}
}
