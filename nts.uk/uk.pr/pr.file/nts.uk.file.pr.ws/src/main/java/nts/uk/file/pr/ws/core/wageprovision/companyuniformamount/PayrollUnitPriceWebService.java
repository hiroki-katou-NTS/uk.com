package nts.uk.file.pr.ws.core.wageprovision.companyuniformamount;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.wageprovision.companyuniformamount.PayrollUnitPriceExportQuery;
import nts.uk.ctx.pr.file.app.core.wageprovision.companyuniformamount.PayrollUnitPriceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceWebService {

    @Inject
    private PayrollUnitPriceExportService service;

    @POST
    @Path("export")
    public ExportServiceResult generate(PayrollUnitPriceExportQuery query) {
        return this.service.start(query);
    }
}
