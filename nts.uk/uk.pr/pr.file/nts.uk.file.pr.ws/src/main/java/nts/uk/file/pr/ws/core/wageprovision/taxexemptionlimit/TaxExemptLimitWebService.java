package nts.uk.file.pr.ws.core.wageprovision.taxexemptionlimit;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit.TaxExemptLimitExportQuery;
import nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit.TaxExemptLimitExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("file/core/wageprovision/taxexemptionlimit")
@Produces("application/json")
public class TaxExemptLimitWebService {
    @Inject
    private TaxExemptLimitExportService mTaxExemptLimitExportService;

    @POST
    @Path("exportExcel")
    public ExportServiceResult exportExcel(TaxExemptLimitExportQuery mTaxExemptLimitExportQuery) {
        return this.mTaxExemptLimitExportService.start(mTaxExemptLimitExportQuery);
    }
}
