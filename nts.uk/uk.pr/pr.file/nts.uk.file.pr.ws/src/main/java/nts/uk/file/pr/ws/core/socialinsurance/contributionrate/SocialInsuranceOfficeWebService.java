package nts.uk.file.pr.ws.core.socialinsurance.contributionrate;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate.ContributionRateExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate.ContributionRateExportService;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance/healthinsurance")
@Produces("application/json")
public class SocialInsuranceOfficeWebService {

    @Inject
    private ContributionRateExportService contributionRateExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(ContributionRateExportQuery query) {
        return this.contributionRateExportService.start(query);
    }
}
