package nts.uk.file.at.ws.form9;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.form9.Form9ExcelByFormatExportService;
import nts.uk.file.at.app.export.form9.Form9ExcelByFormatQuery;
import nts.uk.file.at.app.export.form9.SystemTemplateFinder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("at/file/form9/report")
@Produces("application/json")
public class Form9WebService extends WebService {
    @Inject
    private Form9ExcelByFormatExportService exportService;

    @Inject
    private SystemTemplateFinder finder;

    @POST
    @Path("export-excel")
    public ExportServiceResult generate(Form9ExcelByFormatQuery form9Query) {
        return this.exportService.start(form9Query);
    }

    @GET
    @Path("download-system-template/{code}")
    public Response downloadTemplate2(@PathParam("code") String code) {
        return finder.getSystemTemplateFile(code);
    }
}
