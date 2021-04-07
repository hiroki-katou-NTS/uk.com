package nts.uk.screen.at.ws.ksm.ksm011.d;

import nts.uk.screen.at.app.ksm011.d.command.RegisterSettingScheduleModifyCommandHandler;
import nts.uk.screen.at.app.ksm011.d.command.RegisterSettingScheduleModifyCommand;
import nts.uk.screen.at.app.ksm011.d.query.SettingScheCorrectionByWorkDto;
import nts.uk.screen.at.app.ksm011.d.query.SettingScheCorrectionByWorkProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/ksm/ksm011/d")
@Produces("application/json")
public class SettingScheduleModifyWS {
    @Inject
    private SettingScheCorrectionByWorkProcessor processor;

    @Inject
    private RegisterSettingScheduleModifyCommandHandler command;

    @POST
    @Path("settingschedule")
    public SettingScheCorrectionByWorkDto getSettingScheCorrectionByWork() {
        return this.processor.getSettingSche();
    }

    @POST
    @Path("registersettingschedule")
    public void registerSettingScheModifyByWork(RegisterSettingScheduleModifyCommand command) {
        this.command.handle(command);
    }
}
