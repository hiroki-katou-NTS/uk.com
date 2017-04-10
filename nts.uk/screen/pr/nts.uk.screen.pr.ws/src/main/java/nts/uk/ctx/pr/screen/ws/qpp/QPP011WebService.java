package nts.uk.ctx.pr.screen.ws.qpp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.residentialtax.ResidentialTaxQuery;
import nts.uk.file.pr.app.export.residentialtax.ResidentialTaxReportService;

@Path("/screen/pr/QPP011")
@Produces("application/json")
public class QPP011WebService extends WebService {

	@Inject
	ResidentialTaxReportService reportService;

	@POST
	@Path("saveAsPdf")
	public ExportServiceResult exportDataToPdf(ResidentialTaxQuery query) {
		return reportService.start(query);
	}

}
