package nts.uk.file.at.ws.outputitemsofworkstatustable;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kwr003/report")
@Produces("application/json")
public class DisplayContentWorkStatusWebService extends WebService {

    @Inject
    private DisplayContentWorkStatusService statusService;
    @POST
    @Path("export")
    public ExportServiceResult generate(DisplayContentWorkStatusRequest request) {
        return statusService.start(request);
    }
}
