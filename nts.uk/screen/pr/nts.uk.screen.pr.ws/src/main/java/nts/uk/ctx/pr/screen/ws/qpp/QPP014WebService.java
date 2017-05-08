package nts.uk.ctx.pr.screen.ws.qpp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.banktransfer.BankTransferReportService;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferReportQuery;

@Path("/screen/pr/QPP014")
@Produces("application/json")
public class QPP014WebService extends WebService {
	
	@Inject
	private BankTransferReportService reportService;
	
	@POST
	@Path("saveAsPdf")
	public ExportServiceResult exportDataToPdf(BankTransferReportQuery query) {
		return reportService.start(query);
	}
}
