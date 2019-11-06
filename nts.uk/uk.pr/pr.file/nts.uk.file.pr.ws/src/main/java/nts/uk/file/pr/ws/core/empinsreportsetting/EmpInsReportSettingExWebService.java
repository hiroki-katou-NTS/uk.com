package nts.uk.file.pr.ws.core.empinsreportsetting;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExportQuery;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingPDFService;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiQuery;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/empinsreportsetting")
@Produces("application/json")
public class EmpInsReportSettingExWebService {
    @Inject
    private EmpInsReportSettingPDFService service;


    @POST
    @Path("exportPDF")
    public ExportServiceResult generate(EmpInsReportSettingExportQuery query) {
        return service.start(query);
    }
}
