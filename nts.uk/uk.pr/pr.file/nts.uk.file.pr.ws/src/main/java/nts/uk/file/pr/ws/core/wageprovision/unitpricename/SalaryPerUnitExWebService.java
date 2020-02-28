package nts.uk.file.pr.ws.core.wageprovision.unitpricename;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPerUnitExportService;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPreUnitExportQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/wageprovision/salaryperunit")
@Produces("application/json")
public class SalaryPerUnitExWebService {

    @Inject
    private SalaryPerUnitExportService mSalaryPerUnitExportService;

    @POST
    @Path("exportExcel")
    public ExportServiceResult exportExcel(SalaryPreUnitExportQuery mSalaryPreUnitExportQuery) {
        return this.mSalaryPerUnitExportService.start(mSalaryPreUnitExportQuery);
    }

}
