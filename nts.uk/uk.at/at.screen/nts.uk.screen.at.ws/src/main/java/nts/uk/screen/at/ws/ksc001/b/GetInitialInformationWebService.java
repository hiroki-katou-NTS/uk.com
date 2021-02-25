package nts.uk.screen.at.ws.ksc001.b;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksc001.b.GetInitialInformationScreenQuery;
import nts.uk.screen.at.app.ksc001.b.InitialInformationDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("get/initial")
@Produces("application/json")
public class GetInitialInformationWebService extends WebService {
    @Inject
    private GetInitialInformationScreenQuery screenQuery;

    @POST
    @Path("information")
    public InitialInformationDto GetInitialInformationDto(){
        return screenQuery.GetInitialInformation();
    }
}