package nts.uk.file.at.ws.supportworklist;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.supportworklist.SupportWorkListExportService;
import nts.uk.file.at.app.export.supportworklist.SupportWorkListQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kha/002/report/")
@Produces("application/json")
public class SupportWorkListWebService extends WebService {
    @Inject
    private SupportWorkListExportService exportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(SupportWorkListQuery query) {
        return exportService.start(query);
    }
}
