package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
public class HolidayWorkRegisterServiceImpl implements HolidayWorkRegisterService {
	
	@Inject
	private ApplicationApprovalService applicationApprovalService;
	
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject 
	private NewAfterRegister newAfterRegister;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	private ApproveAppProcedure approveAppProcedure;
	
	@Override
	public ProcessResult register(String companyId, AppHolidayWork appHolidayWork, AppTypeSetting appTypeSetting, 
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput) {
		Application application = appHolidayWork.getApplication();
		
		//	2-2.????????????????????????????????????????????????
		applicationApprovalService.insertApp(application, 
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().orElse(Collections.emptyList()));
		appHolidayWorkRepository.add(appHolidayWork);
		// ?????????????????????????????????
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		List<String> autoFailServer = new ArrayList<>();
		ApproveAppProcedureOutput approveAppProcedureOutput = approveAppProcedure.approveAppProcedure(
        		AppContexts.user().companyId(), 
        		Arrays.asList(application), 
        		Collections.emptyList(), 
        		AppContexts.user().employeeId(), 
        		Optional.empty(), 
        		appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
        		false,
        		true);
		autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
		autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
		autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));
		
		//	???????????????????????? (pending)
		interimRemainDataMngRegisterDateChange.registerDateChange(
				companyId, 
				application.getEmployeeID(), 
				Arrays.asList(application.getAppDate().getApplicationDate()));
		
		//	2-3.??????????????????????????????
		ProcessResult processResult = newAfterRegister.processAfterRegister(
				Arrays.asList(application.getAppID()), 
				appTypeSetting,
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet(),
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
	public ProcessResult registerMulti(String companyId, List<String> empList, AppTypeSetting appTypeSetting,
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput, AppHolidayWork appHolidayWork,
			Map<String, ApprovalRootContentImport_New> approvalRootContentMap) {
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		List<String> autoFailServer = new ArrayList<>();
		List<String> applicationIdList = new ArrayList<String>();
		List<String> reflectAppIdLst = new ArrayList<>();
		//	INPUT???????????????????????????????????????
		empList.forEach(empId -> {
			//	?????????????????????????????????????????????INPUT?????????????????????
			AppHolidayWork empAppHolidayWork = appHolidayWork;
			empAppHolidayWork.setEmployeeID(empId);
			String appId = IdentifierUtil.randomUniqueId();
			empAppHolidayWork.setAppID(appId);
			List<ApprovalPhaseStateImport_New> listApprovalPhaseState = approvalRootContentMap.get(empId).getApprovalRootState().getListApprovalPhaseState();
			//	List?????????ID??????Add(?????????GUID)
			applicationIdList.add(appId);
			
			//	2-2.????????????????????????????????????????????????
			applicationApprovalService.insertApp(empAppHolidayWork.getApplication(), listApprovalPhaseState);
			appHolidayWorkRepository.add(empAppHolidayWork);
			// ?????????????????????????????????
			ApproveAppProcedureOutput approveAppProcedureOutput = approveAppProcedure.approveAppProcedure(
	        		AppContexts.user().companyId(), 
	        		Arrays.asList(empAppHolidayWork.getApplication()), 
	        		Collections.emptyList(), 
	        		AppContexts.user().employeeId(), 
	        		Optional.empty(), 
	        		appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
	        		false,
	        		true);
			autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
			autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
			autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));
			
			//	???????????????????????? (pending)
			interimRemainDataMngRegisterDateChange.registerDateChange(
					companyId, 
					empAppHolidayWork.getEmployeeID(), 
					Arrays.asList(empAppHolidayWork.getAppDate().getApplicationDate()));
		});
		
		//	List?????????ID?????????????????????
		//2-3.??????????????????????????????
		ProcessResult processResult = newAfterRegister.processAfterRegister(
				applicationIdList, 
				appTypeSetting, 
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet(),
				true);
		processResult.getAutoSuccessMail().addAll(autoSuccessMail);
		processResult.getAutoFailMail().addAll(autoFailMail);
		processResult.getAutoFailServer().addAll(autoFailServer);
		processResult.setAutoSuccessMail(processResult.getAutoSuccessMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailMail(processResult.getAutoFailMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailServer(processResult.getAutoFailServer().stream().distinct().collect(Collectors.toList()));
		processResult.setReflectAppIdLst(reflectAppIdLst);
		return processResult;
	}
	
	@Override
	public ProcessResult update(String companyId, AppHolidayWork appHolidayWork, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		Application application = (Application) appHolidayWork;
		//	????????????????????????????????????????????????
		applicationRepository.update(application);
		//	????????????????????????????????????????????????????????????
		appHolidayWorkRepository.update(appHolidayWork);
		
		//	????????????????????????
		interimRemainDataMngRegisterDateChange.registerDateChange(
				companyId, 
				application.getEmployeeID(), 
				Arrays.asList(application.getAppDate().getApplicationDate()));
		
		//	4-2.??????????????????????????????
		return detailAfterUpdate.processAfterDetailScreenRegistration(
				companyId,
				application.getAppID(),
				appDispInfoStartupOutput);
	}

	@Override
	public ProcessResult registerMobile(Boolean mode, String companyId, AppHdWorkDispInfoOutput appHdWorkDispInfo,
			AppHolidayWork appHolidayWork, AppTypeSetting appTypeSetting) {
		if(mode) {
			return this.register(companyId, appHolidayWork, appTypeSetting, appHdWorkDispInfo);
		} else {
			return this.update(companyId, appHolidayWork, appHdWorkDispInfo.getAppDispInfoStartupOutput());
		}
	}
}
