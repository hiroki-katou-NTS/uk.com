package nts.uk.file.pr.ws.core.insurenamechangenoti;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiQuery;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/insurenamechangenoti")
@Produces("application/json")
public class InsuredNameChangedNotiWebService {

    @Inject
    private InsuredNameChangedNotiService service;


    @POST
    @Path("exportPDF")
    public ExportServiceResult generate(InsuredNameChangedNotiQuery query) {
        return service.start(query);
    }

}
