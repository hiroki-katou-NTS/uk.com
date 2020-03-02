package nts.uk.file.pr.ws.core.socialinsurance.salaryhealth;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance/salaryhealth")
@Produces("application/json")
public class SocialInsuranceOfficeWebService {

    @Inject
    private SalaryHealthExportService healthInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(SalaryHealthExportQuery query) {
        return this.healthInsuranceExportService.start(query);
    }
}
