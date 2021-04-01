package nts.uk.ctx.at.schedule.ws.shift.table.organization;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.table.RegisterOrganizationShiftTableRuleCommand;
import nts.uk.ctx.at.schedule.app.command.shift.table.RegisterOrganizationShiftTableRuleCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.table.ShiftTableRuleForOrganizationFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * KSM011 Bb api
 * @author viet.tx
 */
@Path("at/schedule/shift/table/organization")
@Produces("application/json")
public class OrganizationShiftTableRuleWebService extends WebService {
    @Inject
    private ShiftTableRuleForOrganizationFinder finder;

    @Inject
    private RegisterOrganizationShiftTableRuleCommandHandler commandHandler;

//    @POST
//    @Path("init")
//    public ShiftTableRuleDto init() {
//        return this.finder.getData();
//    }

    @POST
    @Path("register")
    public void register(RegisterOrganizationShiftTableRuleCommand command) {
        this.commandHandler.handle(command);
    }
}
