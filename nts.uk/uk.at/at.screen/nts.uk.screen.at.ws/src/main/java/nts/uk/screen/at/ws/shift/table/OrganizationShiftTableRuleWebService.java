package nts.uk.screen.at.ws.shift.table;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.shift.table.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/shift/table")
@Produces("application/json")
public class OrganizationShiftTableRuleWebService extends WebService {
    @Inject
    private GetSetTissueListScreenQuery getSetTissueListScreenQuery;

    @Inject
    private ShiftTableRuleForOrganizationScreenQuery shiftTableRuleForOrgScreenQuery;

    @POST
    @Path("setTissueList")
    public TargetOrgDto init() {
        return this.getSetTissueListScreenQuery.getTargetOrg();
    }

    @POST
    @Path("retrievingOrgShiftTableRule")
    public ShiftTableRuleForCompanyDto getOrgShiftTableRule(OrganizationSelectionListParam param) {
        return this.shiftTableRuleForOrgScreenQuery.get(param);
    }
}
