package nts.uk.file.pr.ws.core.comlegalrecord;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.comlegalrecord.CompanyStatutoryWriteExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printdata/comlegalrecord")
@Produces("application/json")
public class CompanyStatutoryWriteWebService {

    @Inject
    private CompanyStatutoryWriteExportService service;

    @POST
    @Path("export")
    public ExportServiceResult generate() {
        return service.start(null);
    }
}
