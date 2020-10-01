package nts.uk.ctx.at.record.ws.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision.RegisterAppSpecialProvisionMonthCommand;
import nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision.RegisterAppSpecialProvisionMonthCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision.RegisterAppSpecialProvisionYearCommand;
import nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision.RegisterAppSpecialProvisionYearCommandHandler;
import nts.uk.ctx.at.record.app.command.reservation.bento.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author Le Huu Dat
 */
@Path("at/record/monthly/agreement/monthly-result/special-provision")
@Produces("application/json")
public class AppSpecialProvisionMonthWebService extends WebService {
    @Inject
    private RegisterAppSpecialProvisionMonthCommandHandler registerAppSpecialProvisionMonthCommandHandler;
    @Inject
    private RegisterAppSpecialProvisionYearCommandHandler registerAppSpecialProvisionYearCommandHandler;

    @Path("register-month")
    @POST
    public void add(List<RegisterAppSpecialProvisionMonthCommand> commands) {
        this.registerAppSpecialProvisionMonthCommandHandler.handle(commands);
    }

    @Path("register-year")
    @POST
    public void update(List<RegisterAppSpecialProvisionYearCommand> commands) {
        this.registerAppSpecialProvisionYearCommandHandler.handle(commands);
    }
}
