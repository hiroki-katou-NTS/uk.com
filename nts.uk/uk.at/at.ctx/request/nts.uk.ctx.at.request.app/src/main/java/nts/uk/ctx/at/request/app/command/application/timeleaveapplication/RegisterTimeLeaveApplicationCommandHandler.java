package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

/**
 * 時間休暇申請を登録する
 */
@Stateless
@Transactional
public class RegisterTimeLeaveApplicationCommandHandler extends CommandHandlerWithResult<RegisterTimeLeaveApplicationCommand, ProcessResult> {

    @Inject
    private TimeLeaveApplicationRepository timeLeaveApplicationRepository;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    NewAfterRegister newAfterRegister;

    @Inject
    ApplicationApprovalService appRepository;
    
    @Inject
	private ApproveAppProcedure approveAppProcedure;

    @Override
    protected ProcessResult handle(CommandHandlerContext<RegisterTimeLeaveApplicationCommand> context) {

        RegisterTimeLeaveApplicationCommand command = context.getCommand();
        TimeLeaveApplicationOutput timeLeaveApplicationOutput = TimeLeaveAppDisplayInfoDto.mappingData(command.getTimeLeaveAppDisplayInfo());

        // ドメインモデル「申請」の新規登録をする
        Application application = command.getApplication().toDomain();
        List<ApprovalPhaseStateImport_New> listApprovalPhaseState = timeLeaveApplicationOutput
                .getAppDispInfoStartup()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState()
                .orElse(new ArrayList<>());
        this.appRepository.insertApp(application, listApprovalPhaseState);

        //ドメインモデル「時間休暇申請」を登録する
        TimeLeaveApplication timeLeaveApplication = new TimeLeaveApplication(
                application,
                command.getDetails().stream().map(TimeLeaveAppDetailCommand::toDomain).collect(Collectors.toList())
        );
        this.timeLeaveApplicationRepository.add(timeLeaveApplication);
      
        // 申請承認する時の手続き
 		List<String> autoSuccessMail = new ArrayList<>();
 		List<String> autoFailMail = new ArrayList<>();
 		List<String> autoFailServer = new ArrayList<>();
 		ApproveAppProcedureOutput approveAppProcedureOutput = approveAppProcedure.approveAppProcedure(
         		AppContexts.user().companyId(), 
         		Arrays.asList(timeLeaveApplication.getApplication()), 
         		Collections.emptyList(), 
         		AppContexts.user().employeeId(), 
         		Optional.empty(), 
         		timeLeaveApplicationOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
         		false,
         		true);
 		autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
 		autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
 		autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));

        //暫定データの登録

        // TODO: wait for update
        this.interimRemainDataMngRegisterDateChange.registerDateChange(
                AppContexts.user().companyId(),
                application.getEmployeeID(),
                Arrays.asList(application.getAppDate().getApplicationDate())
        );

        Optional<AppTypeSetting> appTypeSet = timeLeaveApplicationOutput
            .getAppDispInfoStartup()
            .getAppDispInfoNoDateOutput()
            .getApplicationSetting()
            .getAppTypeSettings().stream().filter(i -> i.getAppType() == ApplicationType.ANNUAL_HOLIDAY_APPLICATION)
            .findFirst();

        //2-3.新規画面登録後の処理
        ProcessResult processResult = this.newAfterRegister.processAfterRegister(
            Arrays.asList(application.getAppID()),
            appTypeSet.get(),
            timeLeaveApplicationOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet(),
            false
        );
        processResult.getAutoSuccessMail().addAll(autoSuccessMail);
        processResult.getAutoFailMail().addAll(autoFailMail);
        processResult.getAutoFailServer().addAll(autoFailServer);
        processResult.setAutoSuccessMail(processResult.getAutoSuccessMail().stream().distinct().collect(Collectors.toList()));
        processResult.setAutoFailMail(processResult.getAutoFailMail().stream().distinct().collect(Collectors.toList()));
        processResult.setAutoFailServer(processResult.getAutoFailServer().stream().distinct().collect(Collectors.toList()));
        return processResult;
    }
}
