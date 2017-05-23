package nts.uk.ctx.pr.screen.ws.qpp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusReportService;
import nts.uk.file.pr.app.export.comparingsalarybonus.query.ComparingSalaryBonusQuery;

@Path("/screen/pr/QPP008")
@Produces("application/json")
public class QPP008WebService extends WebService {
	/** The export service. */
	@Inject
	private ComparingSalaryBonusReportService reportService;

	/**
	 * Generate.
	 *
	 * @param query
	 *            the query
	 * @return the export service result
	 */
   @POST
   @Path("saveAsPdf")
   public ExportServiceResult exportDataToPdf(ComparingSalaryBonusQuery query) {
        return reportService.start(query);
   }
}
