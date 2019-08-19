package nts.uk.file.pr.ws.core.socialinsurnoticreset;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/file/printdata/notice")
@Produces("application/json")
public class NoticeOfLossWebService {

    @Inject
    private NotificationOfLossInsExportService service;

    @POST
    @Path("exportFile")
    public ExportServiceResult generate(NotificationOfLossInsExportQuery query) {
        return service.start(query);
    }

}
