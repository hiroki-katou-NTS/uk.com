package nts.uk.screen.at.ws.ksm.ksm008.j;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j.Ksm008JCreateWorkTimeOrgCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j.Ksm008JCreateWorkTimeOrgCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j.Ksm008JDeleteWorkTimeOrgCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j.Ksm008JDeleteWorkTimeOrgCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j.Ksm008JUpdateWorkTimeOrgCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j.Ksm008JUpdateWorkTimeOrgCommandHandler;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.screen.at.app.ksm008.query.i.MaxDaysOfContinuousWorkTimeDto;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008GetWkDetaislRequestParam;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008GetWkListRequestParam;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008JStartOrgInfoDto;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008JStartupOrgInfoScreenQuery;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008JWorkingHourListOrgScreenQuery;
import nts.uk.screen.at.app.ksm008.query.j.MaxDaysOfContinuousWorkTimeListOrgDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.J: 組織の就業時間帯の連続上限設定.メニュー別OCD
 *
 * @author rafiqul.islam
 **/

@Path("screen/at/ksm008/j")
@Produces("application/json")
public class ContinuousUpperLimitSettingsKsmOrg008J extends WebService {

    @Inject
    Ksm008JStartupOrgInfoScreenQuery ksm008JStartupOrgInfoScreenQuery;

    @Inject
    Ksm008JWorkingHourListOrgScreenQuery ksm008JWorkingHourListOrgScreenQuery;

    @Inject
    Ksm008JCreateWorkTimeOrgCommandHandler ksm008JCreateWorkTimeOrgCommandHandler;

    @Inject
    Ksm008JDeleteWorkTimeOrgCommandHandler ksm008JDeleteWorkTimeOrgCommandHandler;

    @Inject
    Ksm008JUpdateWorkTimeOrgCommandHandler ksm008JUpdateWorkTimeOrgCommandHandler;

    @POST
    @Path("getCheckCon/{code}")
    public AlarmCheckConditionsQueryDto getAlarmCheckConSche(@PathParam("code") String code) {
        return ksm008JStartupOrgInfoScreenQuery.getAlarmCheckConSche(code);
    }

    @POST
    @Path("getStartupInfo")
    public Ksm008JStartOrgInfoDto getStartupInfo() {
        return ksm008JStartupOrgInfoScreenQuery.getOrgInfo();
    }

    @POST
    @Path("getWorkHoursList")
    public List<MaxDaysOfContinuousWorkTimeDto> getWorkHoursList(Ksm008GetWkListRequestParam requestParam) {
        return ksm008JStartupOrgInfoScreenQuery.getWorkTimeList(requestParam);
    }

    @POST
    @Path("getWorkHoursDetails")
    public MaxDaysOfContinuousWorkTimeListOrgDto getWorkHoursDetails(Ksm008GetWkDetaislRequestParam requestParam) {
        return ksm008JWorkingHourListOrgScreenQuery.get(requestParam);
    }

    @POST
    @Path("createWorkHourSetting")
    public void createWorkHourSetting(Ksm008JCreateWorkTimeOrgCommand command) {
        ksm008JCreateWorkTimeOrgCommandHandler.handle(command);
    }

    @POST
    @Path("updateWorkHourSetting")
    public void updateWorkHourSetting(Ksm008JUpdateWorkTimeOrgCommand command) {
        ksm008JUpdateWorkTimeOrgCommandHandler.handle(command);
    }

    @POST
    @Path("deleteWorkHourSetting")
    public void deleteWorkHourSetting(Ksm008JDeleteWorkTimeOrgCommand command) {
        ksm008JDeleteWorkTimeOrgCommandHandler.handle(command);
    }
}
