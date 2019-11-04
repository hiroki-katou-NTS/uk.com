package nts.uk.file.pr.ws.core.socialinsurnoticreset;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExportCSVService;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExportPDFService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/file/printconfig/notice")
@Produces("application/json")
public class NoticeOfLossWebService {

    @Inject
    private NotificationOfLossInsExportPDFService servicePDF;

    @Inject
    private NotificationOfLossInsExportCSVService serviceCSV;

    @POST
    @Path("exportFilePDF")
    public ExportServiceResult generatePDF(NotificationOfLossInsExportQuery query) {
        return servicePDF.start(query);
    }

    @POST
    @Path("exportFileCSV")
    public ExportServiceResult generateCSV(NotificationOfLossInsExportQuery query) {
        return serviceCSV.start(query);
    }
}
