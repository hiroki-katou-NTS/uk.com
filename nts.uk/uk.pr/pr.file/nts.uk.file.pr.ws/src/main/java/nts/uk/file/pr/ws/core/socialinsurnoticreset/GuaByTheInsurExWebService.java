package nts.uk.file.pr.ws.core.socialinsurnoticreset;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/socialinsurnoticreset")
@Produces("application/json")
public class GuaByTheInsurExWebService {

    @Inject
    private GuaByTheInsurExportService service;

    @POST
    @Path("export")
    public ExportServiceResult generate(GuaByTheInsurExportQuery query) {
        return service.start(query);
    }

}
