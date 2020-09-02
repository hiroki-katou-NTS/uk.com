package nts.uk.screen.at.ws.ksm.ksm005.a;

import nts.uk.screen.at.app.ksm005.find.MonthlyPatternRequestPrams;
import nts.uk.screen.at.app.ksm005.find.MonthlyPatternScreenProcessor;
import nts.uk.screen.at.app.ksm005.find.MonthlySettingPatternDto;

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
}
