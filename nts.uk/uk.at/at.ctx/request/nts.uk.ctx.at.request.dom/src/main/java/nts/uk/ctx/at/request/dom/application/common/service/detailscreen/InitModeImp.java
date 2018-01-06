package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InitModeImp implements InitMode {
	/** 14-3.詳細画面の初期モード */
	@Inject
	ApplicationSettingRepository applicationSettingRepository;

	private ReflectPlanPerState status;

	@Override
	public DetailScreenInitModeOutput getDetailScreenInitMode(User user, int reflectPerState) {
		String companyID = AppContexts.user().companyId();
		
		OutputMode outputMode;

		Optional<ApplicationSetting> applicationSetting = applicationSettingRepository
				.getApplicationSettingByComID(companyID);

		if (user == User.APPROVER) {
			/**
			 * Domain model "approval setting". You can change the contents of
			 * the application at the time of approval but false
			 */
			/** 0 = Display Mode */
			if (applicationSetting.get().getAppContentChangeFlg().value == 0) {

				outputMode = OutputMode.DISPLAYMODE;
			} else {
				/*
				 * ステータスが
				 * 99:過去申請(passApp)、4:反映済(reflected),3:取消待ち(waiCancellation),2:
				 * 取消済( canceled) ,
				 */
				if (reflectPerState == 99 || reflectPerState == 4 || reflectPerState == 3 || reflectPerState == 2) {
					outputMode = OutputMode.DISPLAYMODE;
				} else {
					outputMode = OutputMode.EDITMODE;
				}
			}

		} else {
			/*
			 * ステータスが差し戻し、未反映 Status 0:未反映 (notReflected) 1:差し戻し(remand)
			 */

			if (reflectPerState == 0 || reflectPerState == 1) {
				outputMode = OutputMode.EDITMODE;
			} else {
				outputMode = OutputMode.DISPLAYMODE;
			}
		}
		return new DetailScreenInitModeOutput(outputMode);
	}

}
