package nts.uk.file.pr.ws.core.laborinsurance.laborincsuranceoffice;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportQuery;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/laborInsurance/laborInsuranceoffice")
@Produces("application/json")
public class LaborInsuranceWebService {

    @Inject
    private LaborInsuranceExportService laborInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(LaborInsuranceExportQuery query) {
        return this.laborInsuranceExportService.start(query);
    }
}
