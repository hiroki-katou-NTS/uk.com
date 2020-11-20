package nts.uk.screen.at.ws.ksm.ksm008.i;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i.Ksm008ICreateCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i.Ksm008ICreateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i.Ksm008IDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i.Ksm008IDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i.Ksm008IUpdateCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i.Ksm008IUpdateCommandHandler;
import nts.uk.screen.at.app.ksm008.query.i.Ksm008IStartInfoDto;
import nts.uk.screen.at.app.ksm008.query.i.Ksm008IStartupInfoScreenQuery;
import nts.uk.screen.at.app.ksm008.query.i.MaxDaysOfContinuousWorkTimeDto;
import nts.uk.screen.at.app.ksm008.query.i.MaxDaysOfContinuousWorkTimeListDto;
import nts.uk.screen.at.app.ksm008.query.i.WorkingHourListScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.I: 会社の就業時間帯の連続上限設定.メニュー別OCD
 *
 * @author rafiqul.islam
 **/

@Path("screen/at/ksm008/i")
@Produces("application/json")
public class ContinuousWorkHourSettingKsm008I extends WebService {
    @Inject
    private WorkingHourListScreenQuery workingHourListScreenQuery;

    @Inject
    private Ksm008IDeleteCommandHandler Ksm008IDeleteCommandHandler;

    @Inject
    private Ksm008IStartupInfoScreenQuery ksm008IStartupInfoScreenQuery;

    @Inject
    private Ksm008ICreateCommandHandler ksm008ICreateCommandHandler;

    @Inject
    private Ksm008IUpdateCommandHandler ksm008IUpdateCommandHandler;

    @POST
    @Path("getStartupInfo/{code}")
    public Ksm008IStartInfoDto getStartupInfo(@PathParam("code") String code) {
        List<MaxDaysOfContinuousWorkTimeDto> workTimeList = workingHourListScreenQuery.getWortimeList();
        Collections.sort(workTimeList);
        return ksm008IStartupInfoScreenQuery.getStartupInfoCom(code, workTimeList);
    }

    @POST
    @Path("getWorkingHourList/{code}")
    public MaxDaysOfContinuousWorkTimeListDto getWorkingHourList(@PathParam("code") String code) {
        return workingHourListScreenQuery.get(code);
    }

    @POST
    @Path("deleteWorkHourSetting")
    public void deleteWorkHourSetting(Ksm008IDeleteCommand command) {
        Ksm008IDeleteCommandHandler.handle(command);
    }

    @POST
    @Path("createWorkHourSetting")
    public void createWorkHourSetting(Ksm008ICreateCommand command) {
        ksm008ICreateCommandHandler.handle(command);
    }

    @POST
    @Path("updateWorkHourSetting")
    public void updateWorkHourSetting(Ksm008IUpdateCommand command) {
        ksm008IUpdateCommandHandler.handle(command);
    }
}
