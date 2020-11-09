package nts.uk.file.at.ws.annualworkledger;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.annualworkledger.AnnualWorkLedgerExportService;
import nts.uk.file.at.app.export.annualworkledger.AnnualWorkLedgerFileQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kwr004/report")
@Produces("application/json")
public class Kwr004WebService extends WebService {

    @Inject
    private AnnualWorkLedgerExportService service;
    @POST
    @Path("export")
    public ExportServiceResult generate(AnnualWorkLedgerFileQuery query)
    {
        return service.start(query);
    }
}