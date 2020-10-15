package nts.uk.screen.at.ws.ksm.ksm005.b;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksm005.find.RequestPrams;
import nts.uk.screen.at.app.ksm005.find.WeeklyWorkDto;
import nts.uk.screen.at.app.ksm005.find.WeeklyWorkScreenProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/record/weekly-work")
@Produces(MediaType.APPLICATION_JSON)
public class WeeklyWorkWebService extends WebService {

    @Inject
    private WeeklyWorkScreenProcessor weeklyWorkScreenProcessor;

    @POST
    @Path("get")
    public WeeklyWorkDto getWeeklyWork(RequestPrams requestPrams) {
        return this.weeklyWorkScreenProcessor.findDataBentoMenu(requestPrams);
    }
}
