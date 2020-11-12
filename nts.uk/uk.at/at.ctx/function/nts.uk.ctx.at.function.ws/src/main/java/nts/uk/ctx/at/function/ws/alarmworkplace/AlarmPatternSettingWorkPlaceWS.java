package nts.uk.ctx.at.function.ws.alarmworkplace;

//import nts.uk.ctx.at.function.app.command.alarmworkplace.DeleteAlarmPatternSettingWorkPlaceCommandHandler;
//import nts.uk.ctx.at.function.app.command.alarmworkplace.RegisterAlarmPatternSettingWorkPlaceCommandHandler;
import nts.uk.ctx.at.function.app.find.alarmworkplace.AlarmPatternSettingWorkPlaceFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alarmworkplace/patternsetting")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmPatternSettingWorkPlaceWS {
    @Inject
    private AlarmPatternSettingWorkPlaceFinder alarmPatternSettingWorkPlaceFinder;

//    @Inject
//    private RegisterAlarmPatternSettingWorkPlaceCommandHandler registerAlarmPatternSettingWorkPlaceHandler;
//
//    @Inject
//    private DeleteAlarmPatternSettingWorkPlaceCommandHandler deleteAlarmPatternSettingWorkPlaceHandler;

    @POST
    @Path("getListPatternSettings")
    public void getListPatternSettings() {
//        return alarmPatternSettingWorkPlaceFinder.getListPatternSettings();
    }

//    @POST
//    @Path("selectPattern")
//    public AlarmPatternSettingWorkPlace selectPattern(InsertBanHolidayTogetherDto command) {
//        alarmPatternSettingWorkPlaceFinder.handle(command);
//    }
//
//    @POST
//    @Path("getPatternSettings")
//    public AlarmPatternSettingWorkPlace getPatternSettings(InsertBanHolidayTogetherDto command) {
//        alarmPatternSettingWorkPlaceFinder.handle(command);
//    }
//
//    @POST
//    @Path("register")
//    public void registerAlarmPatternSettingWorkPlace(InsertBanHolidayTogetherDto command) {
//        registerAlarmPatternSettingWorkPlaceHandler.handle(command);
//    }
//
//    @POST
//    @Path("delete")
//    public void deleteAlarmPatternSettingWorkPlace(InsertBanHolidayTogetherDto command) {
//        deleteAlarmPatternSettingWorkPlaceHandler.handle(command);
//    }
}
