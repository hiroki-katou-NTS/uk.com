package nts.uk.file.pr.ws.core.socialinsurance;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance")
@Produces("application/json")
public class SocialInsuranceWebService {

    @Inject
    private SocialInsuranceExportService socialInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(SocialInsuranceExportQuery query) {
        return this.socialInsuranceExportService.start(query);
    }
}
