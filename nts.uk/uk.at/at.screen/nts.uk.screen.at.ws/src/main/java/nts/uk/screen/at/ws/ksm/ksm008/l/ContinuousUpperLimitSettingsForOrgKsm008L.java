package nts.uk.screen.at.ws.ksm.ksm008.l;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l.Ksm008LCreateWorkTimeOrgCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l.Ksm008LCreateWorkTimeOrgCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l.Ksm008LDeleteWorkTimeOrgCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l.Ksm008LDeleteWorkTimeOrgCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l.Ksm008LUpdateWorkTimeOrgCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l.Ksm008LUpdateWorkTimeOrgCommandHandler;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008GetWkDetaislRequestParam;
import nts.uk.screen.at.app.ksm008.query.j.Ksm008GetWkListRequestParam;
import nts.uk.screen.at.app.ksm008.query.l.Ksm008LStartOrgInfoDto;
import nts.uk.screen.at.app.ksm008.query.l.Ksm008LStartupOrgInfoScreenQuery;
import nts.uk.screen.at.app.ksm008.query.l.Ksm008LWorkingHourListOrgScreenQuery;
import nts.uk.screen.at.app.ksm008.query.l.MaxDaysOfWorkTimeDto;
import nts.uk.screen.at.app.ksm008.query.l.MaxDaysOfWorkTimeListOrgDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.L: 組織の就業時間帯の上限設定.メニュー別OCD
 *
 * @author rafiqul.islam
 **/

@Path("screen/at/ksm008/l")
@Produces("application/json")
public class ContinuousUpperLimitSettingsForOrgKsm008L extends WebService {

    @Inject
    Ksm008LStartupOrgInfoScreenQuery ksm008LStartupOrgInfoScreenQuery;

    @Inject
    Ksm008LWorkingHourListOrgScreenQuery ksm008LWorkingHourListOrgScreenQuery;

    @Inject
    Ksm008LCreateWorkTimeOrgCommandHandler ksm008LCreateWorkTimeOrgCommandHandler;

    @Inject
    Ksm008LUpdateWorkTimeOrgCommandHandler ksm008LUpdateWorkTimeOrgCommandHandler;

    @Inject
    Ksm008LDeleteWorkTimeOrgCommandHandler ksm008LDeleteWorkTimeOrgCommandHandler;

    @POST
    @Path("getStartupInfo")
    public Ksm008LStartOrgInfoDto getStartupInfo() {
        return ksm008LStartupOrgInfoScreenQuery.getOrgInfo();
    }

    @POST
    @Path("getCheckCon/{code}")
    public AlarmCheckConditionsQueryDto getAlarmCheckConSche(@PathParam("code") String code) {
        return ksm008LStartupOrgInfoScreenQuery.getAlarmCheckConSche(code);
    }

    @POST
    @Path("getWorkHoursList")
    public List<MaxDaysOfWorkTimeDto> getWorkHoursList(Ksm008GetWkListRequestParam requestParam) {
        return ksm008LStartupOrgInfoScreenQuery.getWorkTimeList(requestParam);
    }

    @POST
    @Path("getWorkTimeDetails")
    public MaxDaysOfWorkTimeListOrgDto getWorkTimeDetails(Ksm008GetWkDetaislRequestParam requestParam) {
        return ksm008LWorkingHourListOrgScreenQuery.get(requestParam);
    }

    @POST
    @Path("createWorkHourSetting")
    public void createWorkHourSetting(Ksm008LCreateWorkTimeOrgCommand command) {
        ksm008LCreateWorkTimeOrgCommandHandler.handle(command);
    }

    @POST
    @Path("updateWorkHourSetting")
    public void updateWorkHourSetting(Ksm008LUpdateWorkTimeOrgCommand command) {
        ksm008LUpdateWorkTimeOrgCommandHandler.handle(command);
    }

    @POST
    @Path("deleteWorkHourSetting")
    public void deleteWorkHourSetting(Ksm008LDeleteWorkTimeOrgCommand command) {
        ksm008LDeleteWorkTimeOrgCommandHandler.handle(command);
    }
}
