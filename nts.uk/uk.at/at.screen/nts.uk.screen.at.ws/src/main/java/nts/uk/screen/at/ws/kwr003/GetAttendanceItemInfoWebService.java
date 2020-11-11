package nts.uk.screen.at.ws.kwr003;


import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr003.GetAttendanceItemInfoScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/screen/kwr/003/b")
@Produces("application/json")
public class GetAttendanceItemInfoWebService extends WebService {
    @Inject
    private GetAttendanceItemInfoScreenQuery query;

    @POST
    @Path("getinfor")
    public AttendanceItemInfoDto getAttendanceItemInfo(int classification, int formNumberDisplay) {
        return query.getInfo(formNumberDisplay);
    }
}
