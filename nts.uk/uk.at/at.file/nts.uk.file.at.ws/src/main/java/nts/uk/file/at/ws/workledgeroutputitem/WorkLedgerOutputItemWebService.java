package nts.uk.file.at.ws.workledgeroutputitem;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.workledgeroutputitem.WorkLedgerOutputItemFileQuery;
import nts.uk.file.at.app.export.workledgeroutputitem.WorkLedgerOutputItemService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kwr005/report")
@Produces("application/json")
public class WorkLedgerOutputItemWebService extends WebService {
    @Inject
    private WorkLedgerOutputItemService statusService;

    @POST
    @Path("export")
    public ExportServiceResult generate(WorkLedgerOutputItemFileQuery query) {
        return statusService.start(query);
    }
}
