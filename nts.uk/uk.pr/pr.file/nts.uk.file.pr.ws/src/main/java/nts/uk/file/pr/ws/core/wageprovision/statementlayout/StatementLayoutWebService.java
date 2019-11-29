package nts.uk.file.pr.ws.core.wageprovision.statementlayout;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutExportQuery;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/wageprovision/statementlayout")
@Produces("application/json")
public class StatementLayoutWebService {

    @Inject
    private StatementLayoutExportService statementLayoutExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(StatementLayoutExportQuery query) {
        return this.statementLayoutExportService.start(query);
    }
}
