package nts.uk.screen.at.ws.ksm.ksm008.g;

import nts.uk.screen.at.app.ksm008.ConsecutiveWorkCmp.StartupInfoCmpScreenQuery;
import nts.uk.screen.at.app.ksm008.ConsecutiveWorkCmp.ConsecutiveWorkCmpDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/ksm008/g")
@Produces("application/json")
public class ConsecutiveWorkCmpWebservice {
    @Inject
    private StartupInfoCmpScreenQuery startupInfoCmpScreenQuery;

    @POST
    @Path("getStartupInfoCmp")
    public ConsecutiveWorkCmpDto getStartupInfoCmp() {
        return this.startupInfoCmpScreenQuery.get();
    }
}
