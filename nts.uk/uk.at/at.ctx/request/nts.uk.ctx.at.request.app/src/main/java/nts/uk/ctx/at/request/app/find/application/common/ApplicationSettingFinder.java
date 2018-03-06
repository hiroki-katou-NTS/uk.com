package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
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
			return new ApplicationSettingDto(companyId, appSet.get().getAppActLockFlg().value, 
					appSet.get().getAppEndWorkFlg().value, appSet.get().getAppActConfirmFlg().value, 
					appSet.get().getAppOvertimeNightFlg().value, appSet.get().getAppActMonthConfirmFlg().value, 
					appSet.get().getRequireAppReasonFlg().value, appSet.get().getDisplayPrePostFlg().value, 
					appSet.get().getDisplaySearchTimeFlg().value, appSet.get().getManualSendMailAtr().value, 
					appSet.get().getBaseDateFlg().value, appSet.get().getAdvanceExcessMessDispAtr().value, 
					appSet.get().getHwAdvanceDispAtr().value, appSet.get().getHwActualDispAtr().value, 
					appSet.get().getActualExcessMessDispAtr().value, appSet.get().getOtAdvanceDispAtr().value, 
					appSet.get().getOtActualDispAtr().value, appSet.get().getWarningDateDispAtr().v(),
					appSet.get().getAppReasonDispAtr().value, appSet.get().getAppContentChangeFlg().value, 
					appSet.get().getScheReflectFlg().value, appSet.get().getPriorityTimeReflectFlg().value, 
					appSet.get().getAttendentTimeReflectFlg().value);
		}
		return null;
	}
}
