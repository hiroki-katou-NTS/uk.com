package nts.uk.ctx.at.template.request.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.template.request.TemplateExportQuery;
import nts.uk.ctx.at.request.template.request.TemplateExportService;

@Path("at/template/request")
@Produces("application/json")
public class TemplateExportWebService extends WebService {
    
    @Inject
    private TemplateExportService exportService;

    @POST
    @Path("export")
    public ExportServiceResult export(TemplateExportQuery query) {
        return exportService.start(query);
    }
}
