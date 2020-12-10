package nts.uk.screen.at.ws.cmm024.screen_b;


import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.cmm024.approver36agrbyworkplace.PerformInitialDisplaysByWorkPlaceScreenDto;
import nts.uk.screen.at.app.query.cmm024.approver36agrbyworkplace.PerformInitialDisplaysByWorkPlaceScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("approve/byworkplace/initial")
@Produces("application/json")
public class WorkPlaceWebService extends WebService {
    @Inject
    private PerformInitialDisplaysByWorkPlaceScreenQuery workPlaceScreenQuery;

    @Path("{worlkplaceid}")
    @POST
    public PerformInitialDisplaysByWorkPlaceScreenDto getInitial(@PathParam("worlkplaceid") String worlkplaceid) {
        return workPlaceScreenQuery.getApprove36AerByWorkplace(worlkplaceid);
    }


}