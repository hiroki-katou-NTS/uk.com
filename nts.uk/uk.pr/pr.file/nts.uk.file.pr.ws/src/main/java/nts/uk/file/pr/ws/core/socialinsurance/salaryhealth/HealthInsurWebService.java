package nts.uk.file.pr.ws.core.socialinsurance.salaryhealth;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurExportService;
/*import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurExportService;*/

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/socialinsurance/salaryhealth")
@Produces("application/json")
public class HealthInsurWebService {
    @Inject
    private SalaryHealthInsurExportService mSalaryHealthInsurExportService;

    @POST
    @Path("exportHealthInsur")
    public ExportServiceResult generate(SalaryHealthInsurExportQuery query) {
        return this.mSalaryHealthInsurExportService.start(query);
    }
}
