package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting.ApprovalSetDto;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.AppReflectAfterConfirm;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class ApplicationSettingFinder {
	@Inject
	private ApplicationSettingRepository appSetRep;
	
	/**
	 * find application setting
	 */
	public ApplicationSettingDto finder(){
		String companyId = AppContexts.user().companyId();
		Optional<ApplicationSetting> appSet = this.appSetRep.getApplicationSettingByComID(companyId);
		if(appSet.isPresent()){
			ApplicationSetting data = appSet.get();
			return new ApplicationSettingDto(companyId, data.getAppActLockFlg().value, 
					data.getAppEndWorkFlg().value, data.getAppActConfirmFlg().value, 
					data.getAppOvertimeNightFlg().value, data.getAppActMonthConfirmFlg().value, 
					data.getRequireAppReasonFlg().value, data.getDisplayPrePostFlg().value, 
					data.getDisplaySearchTimeFlg().value, data.getManualSendMailAtr().value, 
					data.getBaseDateFlg().value, data.getAdvanceExcessMessDispAtr().value, 
					data.getHwAdvanceDispAtr().value, data.getHwActualDispAtr().value, 
					data.getActualExcessMessDispAtr().value, data.getOtAdvanceDispAtr().value, 
					data.getOtActualDispAtr().value, data.getWarningDateDispAtr().v(),
					data.getAppReasonDispAtr().value, data.getAppContentChangeFlg().value, 
					data.getScheReflectFlg().value, data.getPriorityTimeReflectFlg().value, 
					data.getAttendentTimeReflectFlg().value, data.getClassScheAchi().value,
					data.getReflecTimeofSche().value);
		}
		return null;
	}
	public ApprovalSetDto getAppRef(){
		ApprovalSetDto appReflect = new ApprovalSetDto();
		Optional<AppReflectAfterConfirm> opt = this.appSetRep.getAppRef();
		if(opt.isPresent()){
			AppReflectAfterConfirm optget = opt.get();
			appReflect.setScheduleCon(optget.getScheduleConfirmedAtr().value);
			appReflect.setAchiveCon(optget.getAchievementConfirmedAtr().value);
		}
		return appReflect;
	}
}
