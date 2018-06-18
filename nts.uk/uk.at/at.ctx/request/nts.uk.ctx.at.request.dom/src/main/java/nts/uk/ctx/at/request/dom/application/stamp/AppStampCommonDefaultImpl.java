package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampSetOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampCommonDefaultImpl implements AppStampCommonDomainService {

	@Inject
	private StampRequestSettingRepository stampRequestSettingRepository;
	
	@Inject
	private ApplicationReasonRepository applicationReasonRepository;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Override
	public void appReasonCheck(String applicationReason, AppStamp appStamp) {
		appStamp.getApplication_New().setAppReason(new AppReason(applicationReason));
	}

	@Override
	public AppStampSetOutput appStampSet(String companyID) {
		StampRequestSetting stampRequestSetting = this.stampRequestSettingRepository.findByCompanyID(companyID).get();
		List<ApplicationReason> applicationReasons = this.applicationReasonRepository.getReasonByAppType(companyID, ApplicationType.STAMP_APPLICATION.value);
		return new AppStampSetOutput(stampRequestSetting, applicationReasons);
	}

	@Override
	public void validateReason(AppStamp appStamp) {
		/*申請承認設定->申請設定->申請制限設定.申請理由が必須＝trueのとき、申請理由が未入力 (#Msg_115#)
		 ※詳細はアルゴリズム参照*/
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(appStamp.getApplication_New().getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(
				appStamp.getApplication_New().getCompanyID(), 
				ApplicationType.STAMP_APPLICATION.value).get();
		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY)
				||appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)){
			if(applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)&&
					Strings.isEmpty(appStamp.getApplication_New().getAppReason().v())){
						throw new BusinessException("Msg_115");
			}
		}
		appStamp.customValidate();
	}

	@Override
	public String getEmployeeName(String employeeID) {
		return employeeAdapter.getEmployeeName(employeeID);
	}

	@Override
	public AppStamp findByID(String companyID, String appID) {
		AppStamp appStamp = appStampRepository.findByAppID(companyID, appID);
		appStamp.setApplication_New(applicationRepository.findByID(companyID, appID).get());
		return appStamp;
	}

}
