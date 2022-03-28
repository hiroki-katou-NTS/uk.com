package nts.uk.ctx.at.record.ws.dailyattendance.timesheet.ouen.support;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.dailyattdcal.dailyattendance.timesheet.ouen.support.RegisterTravelTimeAccountingScreenCommand;
import nts.uk.ctx.at.shared.app.command.dailyattdcal.dailyattendance.timesheet.ouen.support.RegisterTravelTimeAccountingScreenCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/ctx/record/dailyattendance")
@Produces("application/json")
public class RegisterTravelTimeAccountingWebService extends WebService {

    @Inject
    private RegisterTravelTimeAccountingScreenCommandHandler registerTravelTimeAccountingScreenCommandHandler;

    @POST
    @Path("register-travel-time")
    public void registerTravelTime(RegisterTravelTimeAccountingScreenCommand command){
    	registerTravelTimeAccountingScreenCommandHandler.handle(command);
    }
}
