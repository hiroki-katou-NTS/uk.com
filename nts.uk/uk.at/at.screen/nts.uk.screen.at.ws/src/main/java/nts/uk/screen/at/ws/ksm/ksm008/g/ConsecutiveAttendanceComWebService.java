package nts.uk.screen.at.ws.ksm.ksm008.g;

import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom.StartupInfoComScreenQuery;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom.ConsecutiveAttendanceComDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/ksm008/g")
@Produces("application/json")
public class ConsecutiveAttendanceComWebService {
    @Inject
    private StartupInfoComScreenQuery startupInfoComScreenQuery;

    @POST
    @Path("getStartupInfoCom")
    public ConsecutiveAttendanceComDto getStartupInfoCom(GetStartupInfoParam param) {
        return startupInfoComScreenQuery.getStartupInfoCom(param.getCode());
    }
}
