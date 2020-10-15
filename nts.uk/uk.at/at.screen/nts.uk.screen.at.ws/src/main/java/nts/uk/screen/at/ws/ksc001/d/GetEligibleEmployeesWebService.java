package nts.uk.screen.at.ws.ksc001.d;


import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksc001.d.ConditionDto;
import nts.uk.screen.at.app.ksc001.d.GetEligibleEmployeesScreenQuery;
import nts.uk.screen.at.app.ksc001.d.ListEligibleEmployeesDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("get/employee")
@Produces("application/json")
public class GetEligibleEmployeesWebService extends WebService {
    @Inject
    private GetEligibleEmployeesScreenQuery query;
    @POST
    @Path("listemployid")
    public ListEligibleEmployeesDto getListEmployeeId(ConditionDto conditionDto){
        val rs = new ListEligibleEmployeesDto();
        val listItem =query.getListEmployeeId(conditionDto);
        if(listItem!=null){
            rs.listEmployeeId = listItem;
        }
        return rs ;
    }
}
