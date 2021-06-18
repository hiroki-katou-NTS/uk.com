package nts.uk.screen.at.ws.ksm.ksm008.k;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k.Ksm008KCreateCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k.Ksm008KCreateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k.Ksm008KDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k.Ksm008KDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k.Ksm008KUpdateCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k.Ksm008KUpdateCommandHandler;
import nts.uk.screen.at.app.ksm008.query.k.Ksm008KStartInfoDto;
import nts.uk.screen.at.app.ksm008.query.k.Ksm008KStartupInfoScreenQuery;
import nts.uk.screen.at.app.ksm008.query.k.MaxDayOfWorkTimeCompanyDto;
import nts.uk.screen.at.app.ksm008.query.k.MaxDaysOfWorkTimeComListDto;
import nts.uk.screen.at.app.ksm008.query.k.WorkingHourListCompanyScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.K: 会社の就業時間帯の上限設定.メニュー別OCD
 *
 * @author rafiqul.islam
 **/

@Path("screen/at/ksm008/k")
@Produces("application/json")
public class ContinuousUpperLimitSettingsForCompanyKsm008K extends WebService {

    @Inject
    Ksm008KStartupInfoScreenQuery ksm008KStartupInfoScreenQuery;

    @Inject
    WorkingHourListCompanyScreenQuery workingHourListCompanyScreenQuery;

    @Inject
    Ksm008KCreateCommandHandler ksm008KCreateCommandHandler;

    @Inject
    Ksm008KUpdateCommandHandler ksm008KUpdateCommandHandler;

    @Inject
    Ksm008KDeleteCommandHandler ksm008KDeleteCommandHandler;

    @POST
    @Path("getStartupInfo/{code}")
    public Ksm008KStartInfoDto getStartupInfo(@PathParam("code") String code) {
        List<MaxDayOfWorkTimeCompanyDto> workTimeList = workingHourListCompanyScreenQuery.getWortimeList();
        Collections.sort(workTimeList);
        return ksm008KStartupInfoScreenQuery.getStartupInfoCom(code, workTimeList);
    }

    @POST
    @Path("getWorkHoursDetails/{code}")
    public MaxDaysOfWorkTimeComListDto getWorkHoursDetails(@PathParam("code") String code) {
        return workingHourListCompanyScreenQuery.get(code);
    }

    @POST
    @Path("createWorkHourSetting")
    public void createWorkHourSetting(Ksm008KCreateCommand command) {
        ksm008KCreateCommandHandler.handle(command);
    }

    @POST
    @Path("updateWorkHourSetting")
    public void updateWorkHourSetting(Ksm008KUpdateCommand command) {
        ksm008KUpdateCommandHandler.handle(command);
    }

    @POST
    @Path("deleteWorkHourSetting")
    public void deleteWorkHourSetting(Ksm008KDeleteCommand command) {
        ksm008KDeleteCommandHandler.handle(command);
    }
}
