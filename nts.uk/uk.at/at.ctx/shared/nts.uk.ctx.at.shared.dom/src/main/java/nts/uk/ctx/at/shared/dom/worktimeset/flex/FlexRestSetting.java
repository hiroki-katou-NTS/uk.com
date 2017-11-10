/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//流動勤務の休憩設定
@Getter
public class FlexRestSetting extends DomainObject{

	// 共通の休憩設定
	private CommonRestSetting commonRestSetting;

	// 流動休憩設定
	private FlowWorkRestSettingDetail flowRestSetting;
}
