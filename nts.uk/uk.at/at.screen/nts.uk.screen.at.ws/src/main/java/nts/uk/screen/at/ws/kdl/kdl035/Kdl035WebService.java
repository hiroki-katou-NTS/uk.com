package nts.uk.screen.at.ws.kdl.kdl035;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.screen.at.app.kdl035.Kdl035InputData;
import nts.uk.screen.at.app.kdl035.Kdl035OutputData;
import nts.uk.screen.at.app.kdl035.SubHolidaySubWorkAssociationFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kdl035")
@Produces("application/json")
public class Kdl035WebService extends WebService {
    @Inject
    private SubHolidaySubWorkAssociationFinder finder;

    @POST
    @Path("init")
    public Kdl035OutputData init(Kdl035InputData data) {
        return finder.init(data);
    }

    @POST
    @Path("associate")
    public List<PayoutSubofHDManagementDto> associate(Kdl035OutputData data) {
        return finder.determineAssociationTarget(data);
    }
}
