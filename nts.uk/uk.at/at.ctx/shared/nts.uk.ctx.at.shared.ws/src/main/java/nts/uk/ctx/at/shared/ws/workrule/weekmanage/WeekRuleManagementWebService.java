package nts.uk.ctx.at.shared.ws.workrule.weekmanage;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.workrule.weekmanage.WeekRuleManagementRegisterCommand;
import nts.uk.ctx.at.shared.app.command.workrule.weekmanage.WeekRuleManagementRegisterCommandHandler;
import nts.uk.ctx.at.shared.app.find.workrule.weekmanage.WeekRuleManagementDto;
import nts.uk.ctx.at.shared.app.find.workrule.weekmanage.WeekRuleManagementFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/at/shared/workrule/weekmanage")
@Produces("application/json")
public class WeekRuleManagementWebService extends WebService {
    @Inject
    private WeekRuleManagementFinder finder;

    @Inject
    private WeekRuleManagementRegisterCommandHandler redHandler;

    @POST
    @Path("find")
    public WeekRuleManagementDto find() {
        return this.finder.find();
    }

    @POST
    @Path("register")
    public void register(WeekRuleManagementRegisterCommand command) {
        this.redHandler.handle(command);
    }
}
