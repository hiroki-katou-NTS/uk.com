package nts.uk.file.at.ws.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.outputworkstatustable.OutputFileWorkStatusFileQuery;
import nts.uk.file.at.app.export.outputworkstatustable.OutputFileWorkStatusService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kwr003/report")
@Produces("application/json")
public class DisplayContentWorkStatusWebService extends WebService {

    @Inject
    private OutputFileWorkStatusService statusService;
    @POST
    @Path("export")
    public ExportServiceResult generate()
    {
        val rs = new OutputFileWorkStatusFileQuery();
        return statusService.start(rs);
    }
}
