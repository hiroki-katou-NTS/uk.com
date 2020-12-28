package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfo;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

/**
 * 時間休暇申請を登録する
 */
@Stateless
@Transactional
public class RegisterTimeLeaveApplicationCommandHandler extends CommandHandlerWithResult<RegisterTimeLeaveApplicationCommand, ProcessResult> {

    @Inject
    private TimeLeaveApplicationRepository timeLeaveApplicationRepository;

    @Inject
    private RegisterAtApproveReflectionInfoService registerService;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    NewAfterRegister newAfterRegister;

    @Inject
    ApplicationApprovalService appRepository;

    @Override
    protected ProcessResult handle(CommandHandlerContext<RegisterTimeLeaveApplicationCommand> context) {

        RegisterTimeLeaveApplicationCommand command = context.getCommand();
        Application application = command.getApplication().toDomain();
        TimeLeaveApplicationOutput timeLeaveApplicationOutput = TimeLeaveAppDisplayInfo.mappingData(command.getTimeLeaveAppDisplayInfo());

        // ドメインモデル「申請」の新規登録をする
        this.appRepository.insertApp(
            application,
            timeLeaveApplicationOutput
                .getAppDispInfoStartup()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().isPresent() ? timeLeaveApplicationOutput.getAppDispInfoStartup()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState()
                .get() : null);

        //ドメインモデル「時間休暇申請」を登録する
        timeLeaveApplicationRepository.add(TimeLeaveApplicationCommand.toDomain(command.getTimeLeaveApplicationCommand(), application));

        //2-2.新規画面登録時承認反映情報の整理
        this.registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);

        //暫定データの登録
        this.interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(), application.getEmployeeID(), Arrays.asList(application.getAppDate().getApplicationDate()));

        Optional<AppTypeSetting> appTypeSet = timeLeaveApplicationOutput
            .getAppDispInfoStartup()
            .getAppDispInfoNoDateOutput()
            .getApplicationSetting()
            .getAppTypeSettings().stream().filter(i -> i.getAppType() == ApplicationType.ANNUAL_HOLIDAY_APPLICATION)
            .findFirst();

        //2-3.新規画面登録後の処理
        return this.newAfterRegister.processAfterRegister(
            application.getAppID(),
            appTypeSet.get(),
            timeLeaveApplicationOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet()
        );
    }
}
