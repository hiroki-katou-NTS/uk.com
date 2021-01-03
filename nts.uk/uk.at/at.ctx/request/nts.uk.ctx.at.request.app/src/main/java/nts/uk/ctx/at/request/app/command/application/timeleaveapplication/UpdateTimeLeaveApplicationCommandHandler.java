package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.service.TimeLeaveApplicationService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * 時間休暇申請を更新する
 */
@Stateless
@Transactional
public class UpdateTimeLeaveApplicationCommandHandler extends CommandHandlerWithResult<UpdateTimeLeaveApplicationCommand, ProcessResult> {

    @Inject
    private TimeLeaveApplicationRepository timeLeaveApplicationRepository;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    private TimeLeaveApplicationService timeLeaveApplicationService;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;

    @Inject
    private ApplicationRepository applicationRepository;

    @Override
    protected ProcessResult handle(CommandHandlerContext<UpdateTimeLeaveApplicationCommand> context) {

        UpdateTimeLeaveApplicationCommand command = context.getCommand();
        Application application = command.getApplication().toDomain(command
            .getTimeLeaveAppDisplayInfo().getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication());

        TimeLeaveApplication timeLeaveApplication = TimeLeaveApplicationCommand.toDomain(command.getTimeLeaveApplicationCommand(), application);
        TimeLeaveApplicationOutput timeLeaveApplicationOutput = TimeLeaveAppDisplayInfoDto.mappingData(command.getTimeLeaveAppDisplayInfo());

        timeLeaveApplicationService.checkBeforeUpdate(command.getTimeLeaveApplicationCommand().getTimeDigestAppType(),
            timeLeaveApplication, timeLeaveApplicationOutput);

        //ドメインモデル「申請」の更新をする
        applicationRepository.update(application);

        //時間休暇申請を更新する
        timeLeaveApplicationRepository.update(timeLeaveApplication);

        //暫定データの登録
        this.interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(),
            application.getEmployeeID(), Arrays.asList(application.getAppDate().getApplicationDate()));

        //4-2.詳細画面登録後の処理
        return detailAfterUpdate.processAfterDetailScreenRegistration(AppContexts.user().companyId(), application.getAppID(),
            timeLeaveApplicationOutput.getAppDispInfoStartup());
    }
}
