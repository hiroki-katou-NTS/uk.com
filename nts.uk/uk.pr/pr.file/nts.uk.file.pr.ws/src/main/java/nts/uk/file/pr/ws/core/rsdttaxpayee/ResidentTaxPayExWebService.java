package nts.uk.file.pr.ws.core.rsdttaxpayee;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportQuery;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportService;
import nts.uk.ctx.pr.file.app.core.rsdttaxpayee.ResidentTexPayeeExportQuery;
import nts.uk.ctx.pr.file.app.core.rsdttaxpayee.ResidentTexPayeeExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/laborInsurance/residentTaxPayeeExport")
@Produces("application/json")
public class ResidentTaxPayExWebService {

    @Inject
    private ResidentTexPayeeExportService mResidentTexPayeeExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(ResidentTexPayeeExportQuery query) {
        return this.mResidentTexPayeeExportService.start(query);
    }
}
