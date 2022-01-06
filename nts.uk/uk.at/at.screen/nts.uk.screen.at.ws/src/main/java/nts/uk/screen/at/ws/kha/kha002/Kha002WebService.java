package nts.uk.screen.at.ws.kha.kha002;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemDto;
import nts.uk.screen.at.app.kha002.Kha002InitDto;
import nts.uk.screen.at.app.kha002.Kha002ScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/screen/kha002/")
@Produces("application/json")
public class Kha002WebService extends WebService {
    @Inject
    private Kha002ScreenQuery screenQuery;

    @POST
    @Path("a/init")
    public Kha002InitDto initScreenA() {
        return screenQuery.initScreenA();
    }

    @POST
    @Path("b/attendanceItems")
    public List<AttendanceItemDto> getAvailableAttendanceItems() {
        return screenQuery.getAttendanceItems();
    }
}
