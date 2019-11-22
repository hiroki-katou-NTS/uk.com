package nts.uk.file.pr.ws.report.printconfig.empinsreportsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportQuery;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsLossInfoPDFService;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportSettingExportQuery;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportTxtSettingCsvExportService;

@Path("ctx/pr/report/printconfig/empinsreportsetting")
@Produces("application/json")
public class EmpInsReportSettingCsvExportWebService extends WebService {
	
	@Inject
	private EmpInsReportTxtSettingCsvExportService exportService;

    @Inject
    private EmpInsLossInfoPDFService qui004PdfService;

	@POST
    @Path("exportcsv")
    public ExportServiceResult generate(EmpInsReportSettingExportQuery query) {
        return exportService.start(query);
    }

    @POST
    @Path("export-pdf-qui004")
    public ExportServiceResult generatePdfQui004(EmpInsReportSettingExportQuery query){
        return qui004PdfService.start(query);
    }
}
