package nts.uk.file.at.ws.schedule.personalschedule.byworkplace;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.PersonalScheduleByWkpQuery;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.PersonalScheduleByWorkplaceExportService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/at/schedule/personal/by-workplace")
@Produces("application/json")
public class PersonalScheduleByWorkplaceExportWebService extends WebService {
    @Inject
    private PersonalScheduleByWorkplaceExportService exportService;

    @POST
    @Path("export")
    public ExportServiceResult generate(PersonalScheduleByWkpQuery query) {
        return this.exportService.start(query);
    }
}
