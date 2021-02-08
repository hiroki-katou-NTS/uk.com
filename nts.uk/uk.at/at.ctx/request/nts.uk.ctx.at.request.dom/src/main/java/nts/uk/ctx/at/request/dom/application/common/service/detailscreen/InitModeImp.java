package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;

@Stateless
public class InitModeImp implements InitMode {
	/** 14-3.詳細画面の初期モード */
	@Inject
	ApplicationSettingRepository applicationSettingRepository;

	@Override
	public OutputMode getDetailScreenInitMode(User user, Integer reflectPerState) {
		
		OutputMode outputMode;

		if (user.equals(User.APPROVER)) {
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
