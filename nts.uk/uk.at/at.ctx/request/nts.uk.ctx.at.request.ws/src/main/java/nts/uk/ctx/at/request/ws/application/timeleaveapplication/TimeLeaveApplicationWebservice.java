package nts.uk.ctx.at.request.ws.application.timeleaveapplication;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.timeleaveapplication.RegisterTimeLeaveApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.timeleaveapplication.RegisterTimeLeaveApplicationCommandHandler;
import nts.uk.ctx.at.request.app.command.application.timeleaveapplication.UpdateTimeLeaveApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.timeleaveapplication.UpdateTimeLeaveApplicationCommandHandler;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.ChangeAppDateParams;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.RequestParam;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.StartProcessTimeLeaveParam;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.TimeLeaveApplicationFinder;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.StartProcessTimeLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/request/application/timeLeave")
@Produces("application/json")
public class TimeLeaveApplicationWebservice extends WebService {

    @Inject
    private TimeLeaveApplicationFinder finder;

    @Inject
    private RegisterTimeLeaveApplicationCommandHandler register;

    @Inject
    private UpdateTimeLeaveApplicationCommandHandler update;


    @POST
    @Path("initNewApp")
    public TimeLeaveAppDisplayInfoDto initNewApplication(AppDispInfoStartupDto appDispInfoStartupOutput) {
        return this.finder.initNewTimeLeaveApplication(appDispInfoStartupOutput);
    }

    @POST
    @Path("changeAppDate")
    public TimeLeaveAppDisplayInfoDto changeAppDate(ChangeAppDateParams params) {
        return this.finder.changeApplyDate(params);
    }

    /**
     * 登録前チェック
     */
    @POST
    @Path("checkBeforeRegister")
    public List<ConfirmMsgOutput> checkBeforeRegister(RequestParam param) {
        return this.finder.checkBeforeRegister(param);
    }

    /**
     * 時間休暇申請を登録する
     */
    @POST
    @Path("register")
    public ProcessResult register(RegisterTimeLeaveApplicationCommand command) {
        return this.register.handle(command);
    }

    /**
     * 時間休暇申請を更新する
     */
    @POST
    @Path("update")
    public ProcessResult update(UpdateTimeLeaveApplicationCommand command) {
        return this.update.handle(command);
    }

    /**
     * 起動する
     */
    @POST
    @Path("startProcess")
    public StartProcessTimeLeaveAppDto startProcessTimeLeaveApp(StartProcessTimeLeaveParam param) {
        return this.finder.initUpdateTimeLeaveApp(param);
    }

}
