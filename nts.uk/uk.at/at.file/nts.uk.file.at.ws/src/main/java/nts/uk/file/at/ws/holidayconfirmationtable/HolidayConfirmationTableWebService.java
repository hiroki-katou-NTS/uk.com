package nts.uk.file.at.ws.holidayconfirmationtable;


import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.holidayconfirmationtable.CreateTraceConfirmationTableFileQuery;
import nts.uk.file.at.app.export.holidayconfirmationtable.OutputTraceConfirmationTableService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/holidayconfirmationtable")
@Produces("application/json")
public class HolidayConfirmationTableWebService extends WebService {
    @Inject
    private OutputTraceConfirmationTableService service;
    @POST
    @Path("export")
    public ExportServiceResult generate(CreateTraceConfirmationTableFileQuery query)
    {
        return service.start(query);
    }
}
