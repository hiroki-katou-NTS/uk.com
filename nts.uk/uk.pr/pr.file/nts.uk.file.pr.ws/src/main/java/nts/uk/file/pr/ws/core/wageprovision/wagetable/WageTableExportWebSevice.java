package nts.uk.file.pr.ws.core.wageprovision.wagetable;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableExportQuery;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("file/core/wageprovision/wagetable")
@Produces("application/json")
public class WageTableExportWebSevice {

    @Inject
    private WageTableExportService mWageTableExportService;

    @POST
    @Path("exportExcel")
    public ExportServiceResult exportExcel(WageTableExportQuery query) {
        return this.mWageTableExportService.start(query);
    }

}
