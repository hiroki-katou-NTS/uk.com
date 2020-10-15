package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;

@Stateless
@Transactional
public class LateOrLeaveEarlyServiceDefault implements LateOrLeaveEarlyService {

	@Inject
	LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;

	@Inject
	ApplicationSettingRepository applicationSettingRepository;

	@Inject
	ApplicationSettingRepository applicationSetting;
	
	@Inject 
	EmployeeRequestAdapter employeeAdapter;
	
//	@Inject
//	ApplicationDeadlineRepository deadlineRepository;	
	
	@Inject
	ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository_New;
	
	@Inject
	private ApplicationApprovalService appRepository;
	
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeSetRepo;
	@Override
	public boolean isExist(String companyID, String appID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {

		/** 申請理由が必須  */
		
		int prePost = lateOrLeaveEarly.getApplication().getPrePostAtr().value;
		Integer lateTime1 = lateOrLeaveEarly.getLateTime1AsMinutes();
		Integer earlyTime1 = lateOrLeaveEarly.getEarlyTime1AsMinutes();
		Integer lateTime2 = lateOrLeaveEarly.getLateTime2AsMinutes();
		Integer earlyTime2 = lateOrLeaveEarly.getEarlyTime2AsMinutes();
		int late1 = lateOrLeaveEarly.getLate1().value;
		int late2 = lateOrLeaveEarly.getLate2().value;
		int early1 = lateOrLeaveEarly.getEarly1().value;
		int early2 = lateOrLeaveEarly.getEarly2().value;
		
		// validateReason(lateOrLeaveEarly.getApplication().getCompanyID(),lateOrLeaveEarly.getApplication().getAppReason().v());

		// [画面Bのみ]遅刻時刻早退時刻がともに設定されているとき、遅刻時刻≧早退時刻 (#Msg_381#)
		if(lateTime1!=null && earlyTime1!=null && lateTime2!=null && earlyTime2!=null){
			if (((late1 == 1 && early1 == 1 && lateTime1 >= earlyTime1) || (late2 == 1 && early2 == 1 && lateTime2 >= earlyTime2)) 
				&& prePost == 0) {
				throw new BusinessException("Msg_381");
			}
		}
		// 遅刻、早退、遅刻2、早退2のいずれか１つはチェック必須(#Msg_382#))
		int checkSelect = late1 + late2 + early1 + early2;
		if (checkSelect == 0) {
			throw new BusinessException("Msg_382");
		}

		// [画面Bのみ]遅刻、早退、遅刻2、早退2のチェックがある遅刻時刻、早退時刻は入力必須(#Msg_470#)
		if (prePost == 0 && 
			( 	(late1 == 1 && lateTime1 == null) || 
				(late2 == 1 && lateTime2 == null) ||
				(early1 == 1 && earlyTime1 == null)||
				(early2 == 1 && earlyTime2 == null) 
			)
		) {
			throw new BusinessException("Msg_470");
		}
		//Register phase
		/*approvalRootStateAdapter.insertByAppType(
				lateOrLeaveEarly.getApplication().getCompanyID(), 
				lateOrLeaveEarly.getApplication().getEmployeeID(),
				lateOrLeaveEarly.getApplication().getAppType().value, 
				lateOrLeaveEarly.getApplication().getAppDate(), 
				lateOrLeaveEarly.getApplication().getAppID());*/
		// Add LateOrLeaveEarly
		// error EA refactor 4
		/*appRepository.insert(lateOrLeaveEarly.getApplication());*/
		lateOrLeaveEarlyRepository.add(lateOrLeaveEarly);
		//applicationRepository_New.insert(lateOrLeaveEarly.getApplication());
	}
//	private boolean isReasonTextFieldDisplay(AppTypeDiscreteSetting appTypeSet) {
//
//		return appTypeSet.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY);
//
//	}
//
//	private boolean isComboBoxReasonDisplay(AppTypeDiscreteSetting appTypeSet) {
//		return appTypeSet.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY);
//
//	}

	@Override
	public void updateLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {

		int late1 = lateOrLeaveEarly.getLate1().value;
		int late2 = lateOrLeaveEarly.getLate2().value;
		int early1 = lateOrLeaveEarly.getEarly1().value;
		int early2 = lateOrLeaveEarly.getEarly2().value;
		
		// 遅刻、早退、遅刻2、早退2のいずれか１つはチェック必須(#Msg_382#)
		int checkSelect = late1 + late2 + early1 + early2;
		if (checkSelect == 0) {
			throw new BusinessException("Msg_382");
		}
		//申請承認設定->申請設定->申請制限設定.申請理由が必須＝trueのとき、申請理由が未入力 (#Msg_115#)
//		validateReason(lateOrLeaveEarly.getApplication().getCompanyID(),lateOrLeaveEarly.getApplication().getAppReason().v());
//		
//		lateOrLeaveEarlyRepository.update(lateOrLeaveEarly);
//		applicationRepository_New.updateWithVersion(lateOrLeaveEarly.getApplication());
	}
	
	

	private void validateReason(String companyID,String reason) {
		ApplicationType appType= ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION;
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
				.getApplicationSettingByComID(companyID);
//		ApplicationSetting applicationSetting = applicationSettingOp.get();
//		AppTypeDiscreteSetting appTypeSet = appTypeSetRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value)
//				.get();
//		boolean isAllReasonControlDisplay = isComboBoxReasonDisplay(appTypeSet) && isReasonTextFieldDisplay(appTypeSet);
//		
//		boolean isReasonBlankWhenRequired = applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
//				&& Strings.isBlank(reason);
//		if (isReasonBlankWhenRequired && isAllReasonControlDisplay) {
//			throw new BusinessException("Msg_115");
//		}
	}

	@Override
	public void deleteLateOrLeaveEarly(String companyID, String appID) {
		// 5-2.詳細画面削除後の処理
		//TODO

	}

	@Override
	public void registerLateOrLeaveEarly(String companyID) {
	
	}

	@Override
	public void changeApplication(String companyID, String appID, GeneralDate applicationDate, int actualCancelAtr,
			int early1, int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2,
			String reasonTemp, String appReason) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApplicantName(String employeeID) {
		return employeeAdapter.getEmployeeName(employeeID);
	}

	@Override
	public LateOrLeaveEarly findByID(String companyID, String appID) {
		/*
		LateOrLeaveEarly lateOrLeaveEarly = lateOrLeaveEarlyRepository.findByCode(companyID, appID).get();
		Application_New application = applicationRepository_New.findByID(companyID, appID).get();
		lateOrLeaveEarly.setApplication(application);
		return lateOrLeaveEarly;
		*/
		return null;
	}

}