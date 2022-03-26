package nts.uk.file.at.ws.scheduledailytable;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.scheduledailytable.ScheduleDailyTableExportQuery;
import nts.uk.file.at.app.export.scheduledailytable.ScheduleDailyTableExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/file/scheduledailytable")
@Produces("application/json")
public class ScheduleDailyTableExportWebService extends WebService {
    @Inject
    private ScheduleDailyTableExportService exportService;

    @POST
    @Path("export")
    public ExportServiceResult export(ScheduleDailyTableExportQuery query) {
        return this.exportService.start(query);
    }
}
