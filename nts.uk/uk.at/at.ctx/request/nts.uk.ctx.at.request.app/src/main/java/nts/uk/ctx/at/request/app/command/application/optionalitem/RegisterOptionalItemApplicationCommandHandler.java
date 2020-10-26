package nts.uk.ctx.at.request.app.command.application.optionalitem;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;


@Stateless
@Transactional
public class RegisterOptionalItemApplicationCommandHandler extends CommandHandler<RegisterOptionalItemApplicationCommand> {

    @Inject
    private NewBeforeRegister registerBefore;

    @Inject
    private OptionalItemApplicationRepository repository;

    @Inject
    private ApplicationApprovalService appRepository;

    @Inject
    private RegisterAtApproveReflectionInfoService registerService;

    @Inject
    private NewAfterRegister newAfterRegister;


    @Override
    protected void handle(CommandHandlerContext<RegisterOptionalItemApplicationCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
        /*登録時チェック処理（全申請共通）*/
        RegisterOptionalItemApplicationCommand command = commandHandlerContext.getCommand();
        ApplicationDto applicationDto = command.getApplication();
        AppDispInfoStartupOutput appDispInfoStartup = command.getAppDispInfoStartup().toDomain();
        Application application = Application.createFromNew(EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                sid,
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                sid,
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));
        registerBefore.processBeforeRegister_New(cid,
                EmploymentRootAtr.APPLICATION,
                false,
                application,
                null,
                appDispInfoStartup.getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                Collections.emptyList(),
                appDispInfoStartup);

        appRepository.insertApp(application, command
                .getAppDispInfoStartup().toDomain()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().isPresent() ? command.getAppDispInfoStartup().toDomain()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().get() : null);

        OptionalItemApplication domain = command.getOptItemAppCommand().toDomain();
        domain.setAppID(application.getAppID());
        repository.save(domain);
        /* 2-2.新規画面登録時承認反映情報の整理(register: reflection info setting) */
        registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
        newAfterRegister.processAfterRegister(application.getAppID(),
                appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings().stream().findFirst().get(),
                appDispInfoStartup.getAppDispInfoNoDateOutput().isMailServerSet());
    }

}
