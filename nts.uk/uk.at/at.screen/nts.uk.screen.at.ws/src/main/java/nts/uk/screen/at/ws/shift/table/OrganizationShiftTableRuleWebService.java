package nts.uk.screen.at.ws.shift.table;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.shift.table.GetRulesForOrganizationShiftTableScreenQuery;
import nts.uk.screen.at.app.shift.table.OrganizationSelectionListParam;
import nts.uk.screen.at.app.shift.table.ShiftTableRuleForCompanyDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/shift/table")
@Produces("application/json")
public class OrganizationShiftTableRuleWebService extends WebService {
    @Inject
    private GetRulesForOrganizationShiftTableScreenQuery query;

    @POST
    @Path("orgshiftTableRule")
    public ShiftTableRuleForCompanyDto init(OrganizationSelectionListParam param) {
        return this.query.get(param);
    }
}
