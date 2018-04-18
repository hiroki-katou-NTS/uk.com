/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class JobAutoCalSetting.
 */
// 別自動計算設定
@Getter
@NoArgsConstructor
public class BaseAutoCalSetting extends AggregateRoot {

	/** The normal OT time. */
	// 残業時間
	protected AutoCalOvertimeSetting normalOTTime;

	/** The flex OT time. */
	// フレックス超過時間
	protected AutoCalFlexOvertimeSetting flexOTTime;

	/** The rest time. */
	// 休出時間
	protected AutoCalRestTimeSetting restTime;

	public BaseAutoCalSetting(AutoCalOvertimeSetting normalOTTime, AutoCalFlexOvertimeSetting flexOTTime,
			AutoCalRestTimeSetting restTime) {
		super();
		this.normalOTTime = normalOTTime;
		this.flexOTTime = flexOTTime;
		this.restTime = restTime;
	}

}
