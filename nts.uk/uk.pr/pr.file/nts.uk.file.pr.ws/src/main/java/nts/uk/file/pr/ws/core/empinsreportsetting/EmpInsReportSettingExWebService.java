package nts.uk.file.pr.ws.core.empinsreportsetting;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo.EmpInsGetQualifReportPdfService;
import nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo.EmpInsGetQualifReportQuery;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExportQuery;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingPDFService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/empinsreportsetting")
@Produces("application/json")
public class EmpInsReportSettingExWebService {
    @Inject
    private EmpInsReportSettingPDFService service;

    @Inject
    private EmpInsGetQualifReportPdfService qui001PdfService;

    @POST
    @Path("exportPDF")
    public ExportServiceResult generate(EmpInsReportSettingExportQuery query) {
        return service.start(query);
    }

    @POST
    @Path("export-pdf-qui001")
    public ExportServiceResult generatePdfQui001(EmpInsGetQualifReportQuery query) {
        return qui001PdfService.start(query);
    }
}
