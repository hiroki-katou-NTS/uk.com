package nts.uk.ctx.pr.screen.ws.qet;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.screen.app.report.qet002.AccPaymentReportService;
import nts.uk.ctx.pr.screen.app.report.qet002.query.AccPaymentReportQuery;
import nts.uk.ctx.pr.screen.app.report.qet002.AccPaymentReportGenerator;


@Path("/screen/pr/qet002")
@Produces("application/json")
public class QET002WebService extends WebService {

//	@POST
//	@Path("printReport")
//	public FileGeneratorContext printReport(AccumulatedPaymentReportDto printDto) {
//		// TODO: validate print dto.
//		return null;
//	}

	@Inject
	private AccPaymentReportGenerator accumulatedPrint;
	
	@Inject
	private AccPaymentReportService exportService;
	
	@POST
	@Path("generate")
	public ExportServiceResult generate(AccPaymentReportQuery query) {
	// TODO: validate print dto.\
		System.out.println("1111111111111");
		
		
		return this.exportService.start(query);
	}
}
