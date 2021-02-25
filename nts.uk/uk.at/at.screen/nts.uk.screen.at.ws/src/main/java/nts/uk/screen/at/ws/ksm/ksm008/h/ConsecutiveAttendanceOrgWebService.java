package nts.uk.screen.at.ws.ksm.ksm008.h;

import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.ConsecutiveAttendanceOrgDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.StartupInfoOrgScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/ksm008/h")
@Produces("application/json")
public class ConsecutiveAttendanceOrgWebService {
    @Inject
    private StartupInfoOrgScreenQuery startupInfoOrgScreenQuery;

    @POST
    @Path("getStartupInfoOrg")
    public ConsecutiveAttendanceOrgDto getStartupInfoOrg(){
        return startupInfoOrgScreenQuery.getStartupInfoOrg();
    }
}
