package nts.uk.screen.at.ws.kwr005;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr005.GetTimeItemInformationScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/screen/kwr/005/b")
@Produces("application/json")
public class GetTimeItemInformationWebService extends WebService {
    @Inject
    private GetTimeItemInformationScreenQuery query;

    @POST
    @Path("getinfor")
    public AttendanceItemInfoDto getAttendanceItemInfo(int formNumberDisplay) {
        return query.geInfo(formNumberDisplay);
    }
}
