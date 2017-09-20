package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStamp;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampNewDomainService;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;

@Stateless
public class LateOrLeaveEarlyServiceDefault implements LateOrLeaveEarlyService {

	@Inject
	LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;

	@Inject
	ApplicationRepository applicationRepository;

	@Inject
	ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	ApplicationSettingRepository applicationSetting;

	@Override
	public boolean isExist(String companyID, String appID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {

		/** 申請理由が必須 */
	
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(lateOrLeaveEarly.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		Optional<Application> application = applicationRepository.getAppById(lateOrLeaveEarly.getCompanyID(), lateOrLeaveEarly.getAppID());
		if(applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)&&
				Strings.isEmpty(application.get().getApplicationReason().v())){
					throw new BusinessException("Msg_115");
		}
		
		//遅刻時刻早退時刻がともに設定されているとき、遅刻時刻≧早退時刻 (#Msg_381#)
				int lateTime1  = lateOrLeaveEarly.getLateTime1().valueAsMinutes();
				int earlyTime1 = lateOrLeaveEarly.getEarlyTime1().valueAsMinutes();
				int lateTime2  = lateOrLeaveEarly.getLateTime2().valueAsMinutes();
				int earlyTime2 = lateOrLeaveEarly.getEarlyTime2().valueAsMinutes();
				
				if ( lateTime1   >=  earlyTime1 && lateTime2 >= earlyTime2 ){
					throw new BusinessException("Msg381");
				}
		//遅刻、早退、遅刻2、早退2のいずれか１つはチェック必須(#Msg_382#)
				int late1 = lateOrLeaveEarly.getLate1().value;
				int late2 = lateOrLeaveEarly.getLate2().value;
				int early1 = lateOrLeaveEarly.getEarly1().value;
				int early2 = lateOrLeaveEarly.getEarly2().value;
				
				int checkSelect = late1 + late2 + early1 + early2;
				if ( checkSelect == 0){
					throw new BusinessException("Msg382");
				}		
				
		//	[画面Bのみ]遅刻、早退、遅刻2、早退2のチェックがある遅刻時刻、早退時刻は入力必須(#Msg_470#)	
				int checkInputTime =lateTime1 + lateTime2 + earlyTime1 + earlyTime2;
				if(checkInputTime <= 0  ){
					throw new BusinessException("Msg470");
				}
	}

	@Override
	public void updateLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLateOrLeaveEarly(String companyID, String appID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerLateOrLeaveEarly(String companyID) {

		// TODO Auto-generated method stub

	}

	@Override
	public void changeApplication(String companyID, String appID, GeneralDate applicationDate, int actualCancelAtr,
			int early1, int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2,
			String reasonTemp, String appReason) {
		// TODO Auto-generated method stub

	}

	
}