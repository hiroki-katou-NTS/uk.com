package nts.uk.screen.at.ws.ksm.ksm005.a;

import nts.uk.screen.at.app.ksm005.find.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/schedule/monthly-pattern")
@Produces(MediaType.APPLICATION_JSON)
public class MonthlyPatternWebService {

    @Inject
    private MonthlyPatternScreenProcessor monthlyPatternScreenProcessor;

    @POST
    @Path("get")
    public MonthlySettingPatternDto getWeeklyWork(MonthlyPatternRequestPrams requestPrams) {
        return this.monthlyPatternScreenProcessor.findDataMonthlyPattern(requestPrams);
    }

    @POST
    @Path("getworkstyle")
    public WorkStyleDto getWorkStyle(WorkTypeRequestPrams requestPrams) {
        return this.monthlyPatternScreenProcessor.findDataWorkStype(requestPrams);
    }
}
