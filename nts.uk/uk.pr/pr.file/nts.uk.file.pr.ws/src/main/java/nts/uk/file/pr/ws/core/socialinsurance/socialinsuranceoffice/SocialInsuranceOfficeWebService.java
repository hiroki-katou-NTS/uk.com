package nts.uk.file.pr.ws.core.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance/socialinsuranceoffice")
@Produces("application/json")
public class SocialInsuranceOfficeWebService {

    @Inject
    private SocialInsuranceOfficeExportService socialInsuranceOfficeExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(SocialInsuranceOfficeExportQuery query) {
        return this.socialInsuranceOfficeExportService.start(query);
    }
}
