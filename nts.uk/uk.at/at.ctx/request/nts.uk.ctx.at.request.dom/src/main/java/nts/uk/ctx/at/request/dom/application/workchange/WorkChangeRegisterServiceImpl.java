package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class WorkChangeRegisterServiceImpl implements IWorkChangeRegisterService {

	@Inject
	ApplicationApprovalService appRepository;

	@Inject
	NewAfterRegister newAfterRegister;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	private IWorkChangeUpdateService workChangeUpdateService;
	
	@Inject
	private AppWorkChangeRepository workChangeRepository;
	
	@Inject
	private ApproveAppProcedure approveAppProcedure;

	@Override
	public ProcessResult registerData(String companyId, Application application, AppWorkChange workChange,
			List<GeneralDate> lstDateHd, Boolean isMail, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		Optional<List<ApprovalPhaseStateImport_New>> opListApproval = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpListApprovalPhaseState();
		List<ApprovalPhaseStateImport_New> lstApproval = null;
		if (opListApproval.isPresent()) {
			lstApproval = opListApproval.get();
		}
		// ドメインモデル「勤務変更申請設定」の新規登録をする
		// appRepository.insert(application);
		appRepository.insertApp(application, lstApproval);
		
		// ドメインモデル「勤務変更申請」の新規登録をする
		// KAFS07
		workChangeRepository.add(workChange);
		
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
        		appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
        		false,
        		true);
		autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
		autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
		autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));

		// 年月日Listを作成する
		GeneralDate startDateParam = application.getOpAppStartDate().isPresent()
				? application.getOpAppStartDate().get().getApplicationDate()
				: application.getAppDate().getApplicationDate();
		GeneralDate endDateParam = application.getOpAppEndDate().isPresent()
				? application.getOpAppEndDate().get().getApplicationDate()
				: application.getAppDate().getApplicationDate();
		List<GeneralDate> listDate = new DatePeriod(startDateParam, endDateParam).datesBetween();

//		for (GeneralDate loopDate = startDateParam; loopDate
//				.beforeOrEquals(endDateParam); loopDate = loopDate.addDays(1)) {
//			if (lstDateHd != null ) {
//				if (!lstDateHd.contains(loopDate)) {
//					listDate.add(loopDate);
//				}
//			} else {
//				listDate.add(loopDate);
//			}
//			
//		}

		// 暫定データの登録
		interimRemainDataMngRegisterDateChange.registerDateChange(companyId, application.getEmployeeID(), listDate);

		// 共通アルゴリズム「2-3.新規画面登録後の処理」を実行する
		AppTypeSetting appTypeSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().get();
		ProcessResult processResult = newAfterRegister.processAfterRegister(
				 Arrays.asList(application.getAppID()), 
				 appTypeSetting,
				 appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet(),
				 false);
		processResult.getAutoSuccessMail().addAll(autoSuccessMail);
		processResult.getAutoFailMail().addAll(autoFailMail);
		processResult.getAutoFailServer().addAll(autoFailServer);
		processResult.setAutoSuccessMail(processResult.getAutoSuccessMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailMail(processResult.getAutoFailMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailServer(processResult.getAutoFailServer().stream().distinct().collect(Collectors.toList()));
		return processResult;
	}


	@Override
	public boolean isTimeRequired(String workTypeCD) {
		SetupType setupType = basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCD);
		if (setupType == SetupType.REQUIRED) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ProcessResult registerProcess(Boolean mode, String companyId, Application application, AppWorkChange appWorkchange,
			List<GeneralDate> lstDates, Boolean isMail, AppDispInfoStartupOutput appDispInfoStartupOutput ) {
		if (mode) {
			return this.registerData(companyId, application, appWorkchange, lstDates, isMail, appDispInfoStartupOutput);
		}else {
			return workChangeUpdateService.updateWorkChange(companyId, application, appWorkchange, appDispInfoStartupOutput);
		}
		
	}
}
