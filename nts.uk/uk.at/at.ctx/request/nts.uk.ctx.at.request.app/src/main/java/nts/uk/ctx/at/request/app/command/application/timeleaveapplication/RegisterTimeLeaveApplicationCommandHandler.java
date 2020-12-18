package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
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

    @Override
    protected ProcessResult handle(CommandHandlerContext<RegisterTimeLeaveApplicationCommand> context) {

        RegisterTimeLeaveApplicationCommand command = context.getCommand();
        BusinessTripInfoOutput businessTripInfoOutput = command.getBusinessTripInfoOutput().toDomain();
        String loginSid = AppContexts.user().employeeId();
        ApplicationDto applicationDto = command.getApplication();

        Application application = Application.createFromNew(
            EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
            applicationDto.getEmployeeID() == null ? loginSid : applicationDto.getEmployeeID(),
            EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
            new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
            loginSid,
            Optional.empty(),
            Optional.empty(),
            Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
            Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
            applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
            applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
            ));

        //ドメインモデル「時間休暇申請」を登録する
        timeLeaveApplicationRepository.add(TimeLeaveApplicationCommand.toDomain(command.getTimeLeaveApplicationCommand(), application));

        //2-2.新規画面登録時承認反映情報の整理
        this.registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);

        //暫定データの登録
        this.interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(), application.getEmployeeID(), Arrays.asList(application.getAppDate().getApplicationDate()));

        Optional<AppTypeSetting> appTypeSet = businessTripInfoOutput
            .getAppDispInfoStartup()
            .getAppDispInfoNoDateOutput()
            .getApplicationSetting()
            .getAppTypeSettings().stream().filter(i -> i.getAppType() == ApplicationType.BUSINESS_TRIP_APPLICATION)
            .findFirst();

        //2-3.新規画面登録後の処理
        return this.newAfterRegister.processAfterRegister(
            application.getAppID(),
            appTypeSet.get(),
            businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet()
        );
    }
}
