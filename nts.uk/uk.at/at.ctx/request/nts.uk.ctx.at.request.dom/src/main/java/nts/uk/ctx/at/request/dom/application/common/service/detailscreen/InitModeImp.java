package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InitModeImp implements InitMode {
	/** 14-3.詳細画面の初期モード */
	@Inject
	ApplicationSettingRepository applicationSettingRepository;

	@Override
	public OutputMode getDetailScreenInitMode(User user, Integer reflectPerState) {
		String companyID = AppContexts.user().companyId();
		
		OutputMode outputMode;

		Optional<ApplicationSetting> applicationSetting = applicationSettingRepository
				.getApplicationSettingByComID(companyID);

		if (user.equals(User.APPROVER)) {
			/**
			 * Domain model "approval setting". You can change the contents of
			 * the application at the time of approval but false
			 */
			/** 0 = Display Mode */
			if (applicationSetting.get().getAppContentChangeFlg().equals(AppCanAtr.NOTCAN)) {

				outputMode = OutputMode.DISPLAYMODE;
			} else {
				/*
				 * ステータスが
				 * 99:過去申請(passApp)、4:反映済(reflected),3:取消待ち(waiCancellation),2:
				 * 取消済( canceled) ,
				 */
				if (reflectPerState.equals(ReflectPlanPerState.PASTAPP.value) || 
						reflectPerState.equals(ReflectPlanPerState.REFLECTED.value) || 
						reflectPerState.equals(ReflectPlanPerState.CANCELED.value)) {
					outputMode = OutputMode.DISPLAYMODE;
				} else {
					outputMode = OutputMode.EDITMODE;
				}
			}

		} else {
			/*
			 * ステータスが差し戻し、未反映 Status 0:未反映 (notReflected) 1:差し戻し(remand)
			 */

			if (reflectPerState.equals(ReflectPlanPerState.NOTREFLECTED.value) || 
					reflectPerState.equals(ReflectPlanPerState.REMAND.value)) {
				outputMode = OutputMode.EDITMODE;
			} else {
				outputMode = OutputMode.DISPLAYMODE;
			}
		}
		return outputMode;
	}

}
