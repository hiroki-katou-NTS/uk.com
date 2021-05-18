package nts.uk.ctx.at.schedule.ws.shift.table.company;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.table.RegisterCompanyShiftTableRuleCommand;
import nts.uk.ctx.at.schedule.app.command.shift.table.RegisterCompanyShiftTableRuleCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.table.ShiftTableRuleDto;
import nts.uk.ctx.at.schedule.app.find.shift.table.ShiftTableRuleForCompanyFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * KSM011Ba api
 *
 * @author viet.tx
 */
@Path("at/schedule/shift/table/company")
@Produces("application/json")
public class CompanyShiftTableRuleWebService extends WebService {

    @Inject
    private RegisterCompanyShiftTableRuleCommandHandler commandHandler;

    @Inject
    private ShiftTableRuleForCompanyFinder finder;

    @POST
    @Path("init")
    public ShiftTableRuleDto init() {
        return this.finder.get();
    }

    @POST
    @Path("register")
    public void register(RegisterCompanyShiftTableRuleCommand command) {
        this.commandHandler.handle(command);
    }
}
