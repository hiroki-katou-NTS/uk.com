package nts.uk.file.pr.ws.core.bank;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.bank.BankExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/transfer/bank")
@Produces("application/json")
public class BankWebService {

    @Inject
    private BankExportService service;

    @POST
    @Path("export")
    public ExportServiceResult generate() {
        return service.start(null);
    }
}
