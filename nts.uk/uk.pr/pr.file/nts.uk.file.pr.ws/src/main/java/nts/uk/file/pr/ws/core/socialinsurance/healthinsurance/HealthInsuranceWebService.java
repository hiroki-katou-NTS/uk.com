package nts.uk.file.pr.ws.core.socialinsurance.healthinsurance;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance/healthinsurance")
@Produces("application/json")
public class HealthInsuranceWebService {

    @Inject
    private HealthInsuranceExportService healthInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(HealthInsuranceExportQuery query) {
        return this.healthInsuranceExportService.start(query);
    }
}
