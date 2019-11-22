package nts.uk.file.pr.ws.core.empinsreportsetting;

import nts.arc.layer.app.file.export.ExportServiceResult;
/*import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportPdfService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportQuery;*/
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExportQuery;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerPDFService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportPdfService;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReportQuery;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsLossInfoPDFService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/empinsreportsetting")
@Produces("application/json")
public class EmpInsReportSettingExWebService {
    @Inject
    private NotifiOfChangInNameInsPerPDFService service;

    @Inject
    private EmpInsGetQualifReportPdfService qui001PdfService;

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


}
