package nts.uk.ctx.at.request.ws.application.timeleaveapplication;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.timeleaveapplication.RegisterTimeLeaveApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.timeleaveapplication.RegisterTimeLeaveApplicationCommandHandler;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.CheckBeforeRegisterDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.RequestParam;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.RequestParam;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.TimeLeaveApplicationFinder;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfo;
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
    private RegisterTimeLeaveApplicationCommandHandler commandHandler;


    @POST
    @Path("initNewApp")
    public TimeLeaveAppDisplayInfo checkBeforeRegister(AppDispInfoStartupDto appDispInfoStartupOutput) {
        return this.finder.initNewTimeLeaveApplication(appDispInfoStartupOutput);
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
    public ProcessResult register(RegisterTimeLeaveApplicationCommand param) {
        return this.commandHandler.handle(param);
    }

}
