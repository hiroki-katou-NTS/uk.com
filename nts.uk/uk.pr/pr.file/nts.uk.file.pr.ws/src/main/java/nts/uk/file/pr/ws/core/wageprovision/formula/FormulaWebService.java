package nts.uk.file.pr.ws.core.wageprovision.formula;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaExportQuery;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/wageprovision/formula")
@Produces("application/json")
public class FormulaWebService {

    @Inject
    private FormulaExportService formulaExportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(FormulaExportQuery query) {
        return this.formulaExportService.start(query);
    }
}
