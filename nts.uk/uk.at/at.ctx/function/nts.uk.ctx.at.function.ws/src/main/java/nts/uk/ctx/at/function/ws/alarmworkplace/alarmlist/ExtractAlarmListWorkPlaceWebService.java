package nts.uk.ctx.at.function.ws.alarmworkplace.alarmlist;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceCommandHandler;
import nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceCommand;
import nts.uk.ctx.at.function.app.command.alarmworkplace.extractprocessstatus.CreateAlarmListExtractProcessStatusWorkplaceCommand;
import nts.uk.ctx.at.function.app.command.alarmworkplace.extractprocessstatus.CreateAlarmListExtractProcessStatusWorkplaceCommandHandler;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.CheckConditionDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceFinder;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.InitActiveAlarmListDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KAL011-アラームリスト(職場別)
 */
@Path("at/function/alarm/alarm-list")
@Produces("application/json")
public class ExtractAlarmListWorkPlaceWebService extends WebService {
    @Inject
    private ExtractAlarmListWorkPlaceFinder extractAlarmListWorkPlaceFinder;
    @Inject
    private CreateAlarmListExtractProcessStatusWorkplaceCommandHandler createAlarmListExtractProcessStatusWorkplaceCommandHandler;
    @Inject
    private ExtractAlarmListWorkPlaceCommandHandler extractAlarmListWorkPlaceCommandHandler;

    @POST
    @Path("init")
    public InitActiveAlarmListDto initActiveAlarmList() {
        return extractAlarmListWorkPlaceFinder.initActiveAlarmList();
    }

    @POST
    @Path("get-check-conditions/{code}/{ym}")
    public List<CheckConditionDto> getCheckConditions(@PathParam("code") String code, @PathParam("ym") Integer ym) {
        return extractAlarmListWorkPlaceFinder.getCheckConditions(code, ym);
    }

    @POST
    @Path("extract/start")
    public JavaTypeResult<String> extractStarting() {
        return new JavaTypeResult<>(createAlarmListExtractProcessStatusWorkplaceCommandHandler.handle(new CreateAlarmListExtractProcessStatusWorkplaceCommand()));
    }

    @POST
    @Path("extract/execute")
    public AsyncTaskInfo extractAlarm(ExtractAlarmListWorkPlaceCommand command) {
        return extractAlarmListWorkPlaceCommandHandler.handle(command);
    }
}
