/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkDedicateSetting.
 */
//流動勤務専用設定

/**
 * Gets the calculate setting.
 *
 * @return the calculate setting
 */
@Getter
public class FlowWorkDedicateSetting extends DomainObject{

	/** The overtime setting. */
	//残業設定
	private FlowOTSet overtimeSetting;
	
	/** The calculate setting. */
	//計算設定
	private FlowCalculateSet calculateSetting;
	
}
