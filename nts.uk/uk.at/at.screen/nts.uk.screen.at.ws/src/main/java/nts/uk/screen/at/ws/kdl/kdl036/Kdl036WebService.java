package nts.uk.screen.at.ws.kdl.kdl036;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.screen.at.app.kdl036.HolidayWorkSubHolidayAssociationFinder;
import nts.uk.screen.at.app.kdl036.Kdl036InputData;
import nts.uk.screen.at.app.kdl036.Kdl036OutputData;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kdl036")
@Produces("application/json")
public class Kdl036WebService extends WebService {
    @Inject
    private HolidayWorkSubHolidayAssociationFinder finder;

    @POST
    @Path("init")
    public Kdl036OutputData init(Kdl036InputData data) {
        return finder.init(data);
    }

    @POST
    @Path("associate")
    public List<LeaveComDayOffManaDto> associate(Kdl036OutputData data) {
        return finder.determineAssociationTarget(data);
    }
}
