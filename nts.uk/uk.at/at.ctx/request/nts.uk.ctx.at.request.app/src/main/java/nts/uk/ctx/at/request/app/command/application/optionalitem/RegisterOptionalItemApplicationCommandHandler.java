package nts.uk.ctx.at.request.app.command.application.optionalitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.optitem.OptionalItemApplicationQuery;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class RegisterOptionalItemApplicationCommandHandler extends CommandHandlerWithResult<RegisterOptionalItemApplicationCommand, ProcessResult> {

    @Inject
    private NewBeforeRegister registerBefore;

    @Inject
    private OptionalItemApplicationRepository repository;

    @Inject
    private ApplicationApprovalService appRepository;

    @Inject
    private NewAfterRegister newAfterRegister;

    @Inject
    private OptionalItemApplicationQuery optionalItemApplicationQuery;
    
    @Inject
	private ApproveAppProcedure approveAppProcedure;

    @Override
    protected ProcessResult handle(CommandHandlerContext<RegisterOptionalItemApplicationCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
        RegisterOptionalItemApplicationCommand command = commandHandlerContext.getCommand();
        /**
         *登録前のエラーチェック処理
         */
        this.optionalItemApplicationQuery.checkBeforeUpdate(command.getOptItemAppCommand());
        OptionalItemApplication domain = command.getOptItemAppCommand().toDomain();
        ApplicationDto applicationDto = command.getApplication();
        AppDispInfoStartupOutput appDispInfoStartup = command.getAppDispInfoStartup().toDomain();
        Application application = Application.createFromNew(EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                applicationDto.getEmployeeID(),
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
        /**
         * 登録時チェック処理（全申請共通）
         */
        registerBefore.processBeforeRegister_New(cid,
                EmploymentRootAtr.APPLICATION,
                false,
                application,
                null,
                appDispInfoStartup.getAppDispInfoWithDateOutput().getOpMsgErrorLst().orElse(Collections.emptyList()),
                Collections.emptyList(),
                appDispInfoStartup, 
                new ArrayList<String>(), 
                Optional.empty(), 
                Optional.empty(), 
                false);
        /**
         * ドメインモデル「申請」の新規登録をする
         * */
        appRepository.insertApp(application, command
                .getAppDispInfoStartup().toDomain()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().isPresent() ? command.getAppDispInfoStartup().toDomain()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().get() : null);

        domain.setAppID(application.getAppID());
        List<AnyItemValue> acceptAnyItemValue = domain.getOptionalItems().stream().filter(optionalItem -> optionalItem.getAmount().isPresent() || optionalItem.getTimes().isPresent() || optionalItem.getTime().isPresent()).collect(Collectors.toList());
        domain.setOptionalItems(acceptAnyItemValue);
        repository.save(domain);
        
        // 申請承認する時の手続き
 		List<String> autoSuccessMail = new ArrayList<>();
 		List<String> autoFailMail = new ArrayList<>();
 		List<String> autoFailServer = new ArrayList<>();
 		ApproveAppProcedureOutput approveAppProcedureOutput = approveAppProcedure.approveAppProcedure(
         		AppContexts.user().companyId(), 
         		Arrays.asList(application), 
         		Collections.emptyList(), 
         		AppContexts.user().employeeId(), 
         		Optional.empty(), 
         		appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
         		false,
         		true);
 		autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
 		autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
 		autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));
        
        /**
         * 2-3.新規画面登録後の処理
         * */
        ProcessResult processResult = newAfterRegister.processAfterRegister(
        		Arrays.asList(application.getAppID()),
                appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings().stream().findFirst().get(),
                appDispInfoStartup.getAppDispInfoNoDateOutput().isMailServerSet(),
                false);
        processResult.getAutoSuccessMail().addAll(autoSuccessMail);
        processResult.getAutoFailMail().addAll(autoFailMail);
        processResult.getAutoFailServer().addAll(autoFailServer);
        processResult.setAutoSuccessMail(processResult.getAutoSuccessMail().stream().distinct().collect(Collectors.toList()));
        processResult.setAutoFailMail(processResult.getAutoFailMail().stream().distinct().collect(Collectors.toList()));
        processResult.setAutoFailServer(processResult.getAutoFailServer().stream().distinct().collect(Collectors.toList()));
        return processResult;
    }

}
