package nts.uk.file.pr.ws.core.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance/welfarepensioninsurance")
@Produces("application/json")
public class WelfarepensionInsuranceWebService {

    @Inject
    private WelfarepensionInsuranceExportService welfarepensionInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(WelfarepensionInsuranceExportQuery query) {
        return this.welfarepensionInsuranceExportService.start(query);
    }
}
