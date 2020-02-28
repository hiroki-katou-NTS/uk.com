package nts.uk.file.pr.ws.core.socialinsurnoticreset;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInfoExportPDFService;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExportQuery;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/changeAdd")
@Produces("application/json")
public class EmpAddChangeInfoWebService {

    @Inject
    private EmpAddChangeInfoExportPDFService service;

    @POST
    @Path("/exportData")
    public ExportServiceResult exportData(NotificationOfLossInsExportQuery query){
        return service.start(query);
    }
}