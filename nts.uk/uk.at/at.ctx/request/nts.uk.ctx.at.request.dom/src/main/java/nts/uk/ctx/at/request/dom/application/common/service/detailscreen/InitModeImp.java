package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

@Stateless
public class InitModeImp implements InitMode {
	/** 14-3.詳細画面の初期モード */
//	@Inject
//	ApplicationSettingRepository applicationSettingRepository;

	@Override
	public OutputMode getDetailScreenInitMode(User user, Integer reflectPerState) {
		// 利用者をチェックする(Check user)
		if(user==User.APPROVER || user==User.OTHER) {
			// output:表示モード(output: displayMode)
			return OutputMode.DISPLAYMODE;
		}
		// ステータスをチェックする(Check status)
		if (reflectPerState==ReflectPlanPerState.NOTREFLECTED.value || reflectPerState==ReflectPlanPerState.REMAND.value) {
			// output:編集モード(output: editMode)
			return OutputMode.EDITMODE;
		}
		// output:表示モード(output: displayMode)
		return OutputMode.DISPLAYMODE;
	}

}
