package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//流動勤務の休憩設定
@Getter
public class FlowWorkRestSetting extends DomainObject{

	// 共通の休憩設定
	private CommonRestSetting commonRestSetting;

	// 流動休憩設定詳細
	private FlowWorkRestSettingDetail flowRestSetting;
}
