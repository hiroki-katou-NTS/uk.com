package nts.uk.ctx.at.request.dom.setting.request.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeSettingOutput {
//	勤務変更申請の反映
	private ReflectWorkChangeApp appWorkChangeReflect;
//	勤務変更申請設定
	private AppWorkChangeSet appWorkChangeSet;
}
