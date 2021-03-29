package nts.uk.screen.at.ws.kmk.kmk008.i;

import nts.uk.screen.at.app.kmk.kmk008.unitofapprove.UnitOfApproveDto;
import nts.uk.screen.at.app.kmk.kmk008.unitofapprove.UnitOfApproveProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/i")
@Produces("application/json")
public class Kmk008IWebservice {

    @Inject
    private UnitOfApproveProcessor unitOfApproveProcessor;

    @POST
    @Path("getInitDisplay")
    public UnitOfApproveDto getInitDisplay() {
        return this.unitOfApproveProcessor.find();
    }

}
