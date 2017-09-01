package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.NewScreenStartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.PrelaunchAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.ProcessAfterRegisterService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.ProcessBeforeRegisterService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;

public class ApplicationStampNewDefault implements ApplicationStampNewDomainService {
	
	@Inject
	private PrelaunchAppCommonSetService prelaunchAppCommonSetService;
	
	@Inject
	private NewScreenStartCheckErrorService newScreenStartCheckErrorService;
	
	@Inject
	private ProcessBeforeRegisterService processBeforeRegisterService; 
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private ApplicationStampRepository applicationStampRepository;
	
	@Inject
	private ProcessAfterRegisterService processAfterRegisterService;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;

	@Override
	public void appStampActivation(String companyID, String employeeID, int rootAtr, int appAtr, GeneralDate date) {
		prelaunchAppCommonSetService.prelaunchAppCommonSetService(companyID, employeeID, rootAtr, appAtr, date);
		newScreenStartCheckErrorService.checkError(appAtr);
	}

	@Override
	public void appStampPreProcess(String companyID, String employeeID, GeneralDate date, StampRequestMode stampRequestMode) {
		prelaunchAppCommonSetService.prelaunchAppCommonSetService(
				companyID, 
				employeeID, 
				1, // EmploymentRootAtr.APPLICATION
				stampRequestMode.value, 
				date);
		// 1-4.新規画面起動時の承認ルート取得パターン
		newScreenStartCheckErrorService.checkError(stampRequestMode.value);
		appStampSet(employeeID);
	}

	@Override
	public void appStampGoOutPermitActivation(String companyID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, employeeID, date, StampRequestMode.STAMP_GO_OUT_PERMIT);
	}

	@Override
	public void appStampGoOutPermitRegister(Application application, ApplicationStamp applicationStamp) {
		appReasonCheck();
		
		/*
		申請承認設定->申請設定->申請制限設定.申請理由が必須＝trueのとき、申請理由が未入力 (#Msg_115#)
		 ※詳細はアルゴリズム参照
		 */
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(applicationStamp.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if(applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)&&
				Strings.isEmpty(application.getApplicationReason().v())){
					throw new BusinessException("Msg_115");
		}
		
		List<ApplicationStampGoOutPermit> applicationStampGoOutPermits = applicationStamp.getApplicationStampGoOutPermits();
		for(ApplicationStampGoOutPermit applicationStampGoOutPermit : applicationStampGoOutPermits){
			
			// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
			if(applicationStampGoOutPermit.getStartTime()>=applicationStampGoOutPermit.getEndTime()){
				throw new BusinessException("Msg_307");
			}
			
			/*
			打刻申請詳細.打刻分類＝外出のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
			- 開始時刻
			- 開始場所
			- 終了時刻
			*/
			if(applicationStampGoOutPermit.getStampAtr().equals(StampAtr.GO_OUT)&&
					(applicationStampGoOutPermit.getStartTime() == null ||
					Strings.isEmpty(applicationStampGoOutPermit.getStartLocation()) || 
					applicationStampGoOutPermit.getEndTime() == null )){
						throw new BusinessException("Msg_308");
			}

			/*
			打刻申請詳細.打刻分類＝育児 または 介護のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
			- 開始時刻
			- 終了時刻
			 */
			if((applicationStampGoOutPermit.getStampAtr().equals(StampAtr.CARE)||
					applicationStampGoOutPermit.getStampAtr().equals(StampAtr.CHILDCARE))&&
					(applicationStampGoOutPermit.getStartTime() == null ||
					applicationStampGoOutPermit.getEndTime() == null )){
						throw new BusinessException("Msg_308");
			}
			
		}
		appStampRegistration();
	}

	@Override
	public void appStampWorkActivation(String companyID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, employeeID, date, StampRequestMode.STAMP_ADDITIONAL);
	}

	@Override
	public void appStampWorkRegister(Application application, ApplicationStamp applicationStamp) {
		/*
		申請承認設定->申請設定->申請制限設定.申請理由が必須＝trueのとき、申請理由が未入力 (#Msg_115#)
		 ※詳細はアルゴリズム参照
		 */
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(applicationStamp.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if(applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)&&
				Strings.isEmpty(application.getApplicationReason().v())){
					throw new BusinessException("Msg_115");
		}
		
		List<ApplicationStampWork> applicationStampWorks = applicationStamp.getApplicationStampWorks();
		for(ApplicationStampWork applicationStampWork : applicationStampWorks) {
			// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
			if(applicationStampWork.getStartTime()>=applicationStampWork.getEndTime()){
				throw new BusinessException("Msg_307");
			}
			
			/*
			打刻申請詳細.打刻分類＝出勤／退勤 または 臨時のとき、すべての出退勤申請が以下のいずれも設定されていない (#Msg_308#)
			- 開始時刻
			- 開始場所
			- 終了時刻
			*/
			if(applicationStampWork.getStampAtr().equals(StampAtr.ATTENDANCE)&&
					(applicationStampWork.getStartTime() == null ||
					Strings.isEmpty(applicationStampWork.getStartLocation()) || 
					applicationStampWork.getEndTime() == null )){
						throw new BusinessException("Msg_308");
			}
			
		}
	}

	@Override
	public void appStampCancelActivation(String companyID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, employeeID, date, StampRequestMode.STAMP_CANCEL);
	}

	@Override
	public void appStampCancelRegister(Application application, ApplicationStamp applicationStamp) {
		/*
		申請承認設定->申請設定->申請制限設定.申請理由が必須＝trueのとき、申請理由が未入力 (#Msg_115#)
		 ※詳細はアルゴリズム参照
		 */
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(applicationStamp.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if(applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)&&
				Strings.isEmpty(application.getApplicationReason().v())){
					throw new BusinessException("Msg_115");
		}
		
		// すべての打刻取消申請が実績取消＝するしない区分.しない (#Msg_321#)
		List<ApplicationStampCancel> applicationStampCancels = applicationStamp.getApplicationStampCancels();
		for(ApplicationStampCancel applicationStampCancel : applicationStampCancels) {
			if(applicationStampCancel.getCancelAtr()==0){
				throw new BusinessException("Msg_321");
			}
		}
	}

	@Override
	public void appStampOnlineRecordActivation(String companyID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, employeeID, date, StampRequestMode.STAMP_ONLINE_RECORD);	
	}

	@Override
	public void appStampOnlineRecordRegister(Application application, ApplicationStamp applicationStamp) {
		/*
		申請承認設定->申請設定->申請制限設定.申請理由が必須＝trueのとき、申請理由が未入力 (#Msg_115#)
		 ※詳細はアルゴリズム参照
		 */
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(applicationStamp.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if(applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)&&
				Strings.isEmpty(application.getApplicationReason().v())){
					throw new BusinessException("Msg_115");
		}
	}

	@Override
	public void appStampOtherActivation(String companyID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, employeeID, date, StampRequestMode.OTHER);
	}

	@Override
	public void appStampOtherRegister(Application application, ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampSet(String employeeID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampRegistration() {
		/*StampRequestMode StampRequestMode = applicationStamp.getStampRequestMode();
		processBeforeRegisterService.processBeforeRegister(
				applicationStamp.getCompanyID(), 
				employeeID, 
				startDate, 
				endDate, 
				postAtr, 
				routeAtr, 
				targetApp);
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(empID, application);
		switch(StampRequestMode){
			case STAMP_GO_OUT_PERMIT: applicationStampRepository.addStampGoOutPermit(applicationStamp);break;
			case STAMP_ADDITIONAL: applicationStampRepository.addStampWork(applicationStamp);break;
			case STAMP_CANCEL: applicationStampRepository.addStampCancel(applicationStamp);break;
			case STAMP_ONLINE_RECORD: applicationStampRepository.addStampOnlineRecord(applicationStamp);break;
			default: break;
		}
		processAfterRegisterService.processAfterRegister(companyID, appID);*/
	}

	@Override
	public void appReasonCheck() {
		// TODO Auto-generated method stub
		
	}

}
