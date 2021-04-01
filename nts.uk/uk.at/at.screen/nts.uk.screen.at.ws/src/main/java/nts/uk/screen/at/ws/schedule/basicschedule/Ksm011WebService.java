package nts.uk.screen.at.ws.schedule.basicschedule;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol.RegisterScheFuncCtrlCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol.RegisterScheFuncCtrlCommandHandler;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleBasicSettingDto;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleBasicSettingScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author viet.tx
 */
@Path("screen/at/schedule/basicsetting")
@Produces("application/json")
public class Ksm011WebService extends WebService {
    @Inject
    private ScheduleBasicSettingScreenQuery scheduleBasicSettingScreenQuery;

    @Inject
    private RegisterScheFuncCtrlCommandHandler registerScheFuncCtrlCommand;

    // KSM011C
    @POST
    @Path("init")
    public ScheduleBasicSettingDto init() {
        return this.scheduleBasicSettingScreenQuery.getDataInit();
    }

    // KSM011C
    @POST
    @Path("register")
    public void registerScheduleFuncControl(RegisterScheFuncCtrlCommand command) {
        this.registerScheFuncCtrlCommand.handle(command);
    }
}
