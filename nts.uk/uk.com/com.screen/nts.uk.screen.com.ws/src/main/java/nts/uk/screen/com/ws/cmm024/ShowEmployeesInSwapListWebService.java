package nts.uk.screen.com.ws.cmm024;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.EmployeeInfoData;
import nts.uk.screen.com.app.query.ShowEmployeesInSwapListScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("show/inswap")
@Produces("application/json")
public class ShowEmployeesInSwapListWebService extends WebService {
    @Inject
    private ShowEmployeesInSwapListScreenQuery query;

    @Path("employees")
    @POST
    public List<EmployeeInfoData> getEmployees(ShowEmployeesDto dto) {
        return query.getEmployees(dto.getWorkplaceId(),dto.getBaseDate());
    }
}
