package nts.uk.ctx.at.request.app.command.application.businesstrip;

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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class AddBusinessTripCommandHandler extends CommandHandlerWithResult<AddBusinessTripCommand, ProcessResult> {


    @Inject
    ApplicationApprovalService appRepository;

    @Inject
    BusinessTripRepository businessTripRepository;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    NewAfterRegister newAfterRegister;
    
    @Inject
	private ApproveAppProcedure approveAppProcedure;

    @Override
    protected ProcessResult handle(CommandHandlerContext<AddBusinessTripCommand> context) {
        AddBusinessTripCommand command = context.getCommand();
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
        BusinessTrip businessTrip = command.getBusinessTrip().toDomain(application);
        businessTrip.setAppID(application.getAppID());
        BusinessTripInfoOutput businessTripInfoOutput = command.getBusinessTripInfoOutput().toDomain();

        GeneralDate appStartDate = application.getOpAppStartDate().isPresent() ? application.getOpAppStartDate().get().getApplicationDate() : null;
        GeneralDate appEndDate = application.getOpAppStartDate().isPresent() ? application.getOpAppEndDate().get().getApplicationDate() : null;
        List<GeneralDate> dates = new ArrayList<>();
        if (appStartDate != null && appEndDate != null) {
            DatePeriod period = new DatePeriod(appStartDate, appEndDate);
            dates = period.datesBetween();
        } else {
            dates.add(application.getAppDate().getApplicationDate());
        }

        // ドメインモデル「申請」の新規登録をする
        this.appRepository.insertApp(
                application,
                businessTripInfoOutput
                        .getAppDispInfoStartup()
                        .getAppDispInfoWithDateOutput()
                        .getOpListApprovalPhaseState().isPresent() ? businessTripInfoOutput.getAppDispInfoStartup()
                        .getAppDispInfoWithDateOutput()
                        .getOpListApprovalPhaseState()
                        .get() : null);
        
        // ドメインモデル「出張申請」を追加する
        this.businessTripRepository.add(businessTrip);

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
         		businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
         		false,
         		true);
		autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
		autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
		autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));

        


        // アルゴリズム「出張申請暫定残数を更新する」を実行する
        // アルゴリズム「暫定データの登録」を実行する
        this.interimRemainDataMngRegisterDateChange.registerDateChange(
                AppContexts.user().companyId(),
                application.getEmployeeID(),
                dates
        );

        Optional<AppTypeSetting> appTypeSet = businessTripInfoOutput
                .getAppDispInfoStartup()
                .getAppDispInfoNoDateOutput()
                .getApplicationSetting()
                .getAppTypeSettings().stream().filter(i -> i.getAppType()== ApplicationType.BUSINESS_TRIP_APPLICATION)
                .findFirst();

	    // 2-3.新規画面登録後の処理
        ProcessResult processResult = this.newAfterRegister.processAfterRegister(
	            Arrays.asList(application.getAppID()),
	            appTypeSet.get(),
	            businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet(),
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
