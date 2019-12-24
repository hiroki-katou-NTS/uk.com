package nts.uk.yearend.ws.yearendadjustment.insurancecompany.lifeInsurance;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceExportQuery;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/yearend/yearendadjustment/lifeInsurance")
@Produces("application/json")
public class LifeInsuranceWebService {

    @Inject
    private LifeInsuranceExportService lifeInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(LifeInsuranceExportQuery query) {
        return this.lifeInsuranceExportService.start(query);
    }
}
