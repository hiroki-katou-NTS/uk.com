package nts.uk.screen.at.ws.ksm.ksm005.a;

import nts.uk.screen.at.app.ksm005.find.MonthlyPatternRequestPrams;
import nts.uk.screen.at.app.ksm005.find.RegisterMonthlyPatternDto;
import nts.uk.screen.at.app.ksm005.find.RegisterMonthlyPatternProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/schedule/register-monthly-pattern")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterMonthlyPatternWebservice {

    @Inject
    private RegisterMonthlyPatternProcessor registerMonthlyPatternProcessor;

    @POST
    @Path("get")
    public RegisterMonthlyPatternDto getWeeklyWork(MonthlyPatternRequestPrams requestPrams) {
        return this.registerMonthlyPatternProcessor.registerMonthlyPattern(requestPrams);
    }
}
