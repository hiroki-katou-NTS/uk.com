package nts.uk.screen.at.ws.kwr007;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr003.AttendanceItemInfoPrams;
import nts.uk.screen.at.app.kwr007.GetArbitraryScheduleScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/screen/kwr/007/b")
@Produces("application/json")
public class Kwr007WebService extends WebService {
    @Inject
    private GetArbitraryScheduleScreenQuery query;

    @POST
    @Path("getinfor")
    public AttendanceItemInfoDto getAttendanceItemInfo(AttendanceItemInfoPrams prams) {
        return query.geInfo(prams.getFormNumberDisplay());
    }

}
