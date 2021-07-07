package nts.uk.ctx.at.schedule.ws.shift.table.organization;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.table.DeleteOrgShiftTableRuleCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.table.RegisterOrganizationShiftTableRuleCommand;
import nts.uk.ctx.at.schedule.app.command.shift.table.RegisterOrganizationShiftTableRuleCommandHandler;

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
    private RegisterOrganizationShiftTableRuleCommandHandler commandHandler;

    @Inject
    private DeleteOrgShiftTableRuleCommandHandler deleteCommandHandler;

    @POST
    @Path("register")
    public void register(RegisterOrganizationShiftTableRuleCommand command) {
        this.commandHandler.handle(command);
    }

    @POST
    @Path("delete")
    public void delete(RegisterOrganizationShiftTableRuleCommand command) {
        this.deleteCommandHandler.handle(command);
    }
}
