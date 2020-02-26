package nts.uk.yearend.ws.yearendadjustment.insurancecompany.earthquakeinsurance;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance.EarthQuakeInsuranceExportQuery;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance.EarthQuakeInsuranceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/yearend/yearendadjustment/earthquakeinsurance")
@Produces("application/json")
public class EarthQuakeInsuranceWebService {

    @Inject
    private EarthQuakeInsuranceExportService earthQuakeInsuranceExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(EarthQuakeInsuranceExportQuery query) {
        return this.earthQuakeInsuranceExportService.start(query);
    }
}
