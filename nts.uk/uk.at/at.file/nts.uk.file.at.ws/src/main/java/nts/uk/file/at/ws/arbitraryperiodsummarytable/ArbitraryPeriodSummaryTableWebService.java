package nts.uk.file.at.ws.arbitraryperiodsummarytable;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.arbitraryperiodsummarytable.ArbitraryPeriodSummaryTableFileQuery;
import nts.uk.file.at.app.export.arbitraryperiodsummarytable.ArbitraryPeriodSummaryTableService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kwr/007/report")
@Produces("application/json")
public class ArbitraryPeriodSummaryTableWebService extends WebService {
    @Inject
    private ArbitraryPeriodSummaryTableService service;

    @POST
    @Path("export")
    public ExportServiceResult generate(ArbitraryPeriodSummaryTableFileQuery fileQuery) {
        return service.start(fileQuery);
    }
}
