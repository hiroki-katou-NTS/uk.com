package nts.uk.screen.at.ws.ksm.ksm008.h;

//import nts.uk.ctx.at.schedule.app.query.schedule.alarm.consecutivework.consecutiveattendance.ConsecutiveAttendanceOrgQuery;
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

//    @Inject
//    private ConsecutiveAttendanceOrgQuery consecutiveAttendanceOrgQuery;

    @POST
    @Path("getStartupInfoOrg")
    public ConsecutiveAttendanceOrgDto getStartupInfoOrg(){
        return startupInfoOrgScreenQuery.getStartupInfoOrg();
    }

//    @POST
//    @Path("getMaxConsDays")
//    public Integer getMaxConsDays(int unit, String workplaceId, String workplaceGroupId){
//        if(consecutiveAttendanceOrgQuery.getMaxConsDays().isPresent()){
//           return consecutiveAttendanceOrgQuery.getMaxConsDays().get().getNumberOfDays().getNumberOfDays().v();
//        }
//
//        return null;
//    }
}
