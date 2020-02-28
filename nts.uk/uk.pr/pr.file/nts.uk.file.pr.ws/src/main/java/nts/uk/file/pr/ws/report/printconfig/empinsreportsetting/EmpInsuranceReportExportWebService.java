package nts.uk.file.pr.ws.report.printconfig.empinsreportsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExportQuery;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerPDFService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportCsvService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportPdfService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportQuery;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsLossInfoPDFService;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsLossInfoExportQuery;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportTxtSettingCsvExportService;

@Path("ctx/pr/report/printconfig/empinsreportsetting")
@Produces("application/json")
public class EmpInsuranceReportExportWebService extends WebService {
	@Inject
	private NotifiOfChangInNameInsPerPDFService service;

	@Inject
	private EmpInsGetQualifReportPdfService qui001PdfService;

	@Inject
	private EmpInsGetQualifReportCsvService qui001CsvService;

	@Inject
	private EmpInsReportTxtSettingCsvExportService qui004CsvService;

	@Inject
	private EmpInsLossInfoPDFService qui004PdfService;

	@POST
	@Path("exportPDF")
	public ExportServiceResult generate(NotifiOfChangInNameInsPerExportQuery query) {
		return service.start(query);
	}

	@POST
	@Path("export-pdf-qui001")
	public ExportServiceResult generatePdfQui001(EmpInsGetQualifReportQuery query) {
		return qui001PdfService.start(query);
	}

	@POST
	@Path("export-csv-qui001")
	public ExportServiceResult generateCsvQui001(EmpInsGetQualifReportQuery query) {
		return qui001CsvService.start(query);
	}

	@POST
	@Path("export-csv-qui004")
	public ExportServiceResult generate(EmpInsLossInfoExportQuery query) {
		return qui004CsvService.start(query);
	}

	@POST
	@Path("export-pdf-qui004")
	public ExportServiceResult generatePdfQui004(EmpInsLossInfoExportQuery query) {
		return qui004PdfService.start(query);
	}
}
