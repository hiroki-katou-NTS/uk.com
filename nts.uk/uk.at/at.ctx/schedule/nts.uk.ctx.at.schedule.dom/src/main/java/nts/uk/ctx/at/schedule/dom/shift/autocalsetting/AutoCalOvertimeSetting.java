/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AutoCalOvertimeSetting.
 */
/// 残業時間の自動計算設定
@Getter
public class AutoCalOvertimeSetting extends DomainObject {

	/** The Early OT time. */
	// 早出残業時間
	private AutoCalSetting earlyOtTime;

	/** The Early mid OT time. */
	// 早出深夜残業時間
	private AutoCalSetting earlyMidOtTime;

	/** The Normal OT time. */
	// 普通残業時間
	private AutoCalSetting normalOtTime;

	/** The Normal mid OT time. */
	// 普通深夜残業時間
	private AutoCalSetting normalMidOtTime;

	/** The Legal OT time. */
	// 法定内残業時間
	private AutoCalSetting legalOtTime;

	/** The Legal mid OT time. */
	// 法定内深夜残業時間
	private AutoCalSetting legalMidOtTime;

	/**
	 * Instantiates a new auto cal overtime setting.
	 *
	 * @param earlyOTTime
	 *            the early OT time
	 * @param earlyMidOTTime
	 *            the early mid OT time
	 * @param normalOTTime
	 *            the normal OT time
	 * @param normalMidOTTime
	 *            the normal mid OT time
	 * @param legalOTTime
	 *            the legal OT time
	 * @param legalMidOTTime
	 *            the legal mid OT time
	 */
	public AutoCalOvertimeSetting(AutoCalSetting earlyOTTime, AutoCalSetting earlyMidOTTime,
			AutoCalSetting normalOTTime, AutoCalSetting normalMidOTTime, AutoCalSetting legalOTTime,
			AutoCalSetting legalMidOTTime) {
		super();
		this.earlyOtTime = earlyOTTime;
		this.earlyMidOtTime = earlyMidOTTime;
		this.normalOtTime = normalOTTime;
		this.normalMidOtTime = normalMidOTTime;
		this.legalOtTime = legalOTTime;
		this.legalMidOtTime = legalMidOTTime;
	}

}
