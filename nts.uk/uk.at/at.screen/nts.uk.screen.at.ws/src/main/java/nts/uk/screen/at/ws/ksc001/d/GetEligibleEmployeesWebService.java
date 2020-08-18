package nts.uk.screen.at.ws.ksc001.d;


import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksc001.d.ConditionDto;
import nts.uk.screen.at.app.ksc001.d.ListEligibleEmployeesDto;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("get/employee")
@Produces("application/json")
public class GetEligibleEmployeesWebService extends WebService {

    @POST
    @Path("listemployid")
    public ListEligibleEmployeesDto getListEmployeeId(ConditionDto conditionDto){
        return null;
    }
}
