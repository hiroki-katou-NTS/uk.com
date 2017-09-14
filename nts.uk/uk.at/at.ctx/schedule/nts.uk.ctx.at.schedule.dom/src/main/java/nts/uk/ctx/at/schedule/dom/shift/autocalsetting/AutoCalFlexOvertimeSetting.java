/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AutoCalFlexOvertimeSetting.
 */
// フレックス超過時間の自動計算設定
@Getter
public class AutoCalFlexOvertimeSetting extends DomainObject {

	/** The Flex OT time. */
	// フレックス超過時間
	private AutoCalSetting flexOtTime;

	/** The Flex OT night time. */
	// フレックス超過深夜時間
	private AutoCalSetting flexOtNightTime;

	/**
	 * Instantiates a new auto cal flex overtime setting.
	 *
	 * @param flexOtTimeSet the flex ot time set
	 * @param flexOtNightTimeSet the flex ot night time set
	 */
	public AutoCalFlexOvertimeSetting(AutoCalSetting flexOtTimeSet,
			AutoCalSetting flexOtNightTimeSet) {
		super();
		this.flexOtTime = flexOtTimeSet;
		this.flexOtNightTime = flexOtNightTimeSet;
	}

}
