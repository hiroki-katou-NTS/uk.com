package nts.uk.ctx.at.function.ws.alarmworkplace;

import nts.uk.ctx.at.function.app.command.alarmworkplace.*;
import nts.uk.ctx.at.function.app.find.alarmworkplace.AlarmPatternSettingWorkPlaceFinder;
import nts.uk.ctx.at.function.app.find.alarmworkplace.RequestParam;
import nts.uk.ctx.at.function.app.find.alarmworkplace.WkpAlarmPatternSettingDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.WkpCheckConditionDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("alarmworkplace/patternsetting")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmPatternSettingWorkPlaceWS {

    @Inject
    private AlarmPatternSettingWorkPlaceFinder finder;

    @Inject
    private RegisterAlarmPatternSettingWorkPlaceCommandHandler register;

    @Inject
    private AddAlarmPatternSettingWorkPlaceCommandHandler add;

    @Inject
    private DeleteAlarmPatternSettingWorkPlaceCommandHandler delete;

    @POST
    @Path("getAll")
    public List<WkpAlarmPatternSettingDto> getListPatternSettings() {
        return finder.getListPatternSettings();
    }

    @POST
    @Path("getPatternSetting")
    public WkpAlarmPatternSettingDto selectPattern(RequestParam requestParam) {
        return finder.selectPatternSetting(requestParam);
    }

    @POST
    @Path("register")
    public void registerAlarmPatternSettingWorkPlace(RegisterAlarmPatternSettingWorkPlaceCommand command) {
        register.handle(command);
    }

    @POST
    @Path("add")
    public void addAlarmPatternSettingWorkPlace(RegisterAlarmPatternSettingWorkPlaceCommand command) {
        add.handle(command);
    }

    @POST
    @Path("delete")
    public void deleteAlarmPatternSettingWorkPlace(DeleteAlarmPatternSettingWorkPlaceCommand command) {
        delete.handle(command);
    }
}
