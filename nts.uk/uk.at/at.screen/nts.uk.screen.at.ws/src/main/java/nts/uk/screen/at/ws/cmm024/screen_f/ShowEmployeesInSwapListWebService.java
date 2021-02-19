package nts.uk.screen.at.ws.cmm024.screen_f;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.EmployeeInfoData;
import nts.uk.screen.at.app.query.cmm024.showemployee.ShowEmployeesInSwapListScreenQuery;

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
