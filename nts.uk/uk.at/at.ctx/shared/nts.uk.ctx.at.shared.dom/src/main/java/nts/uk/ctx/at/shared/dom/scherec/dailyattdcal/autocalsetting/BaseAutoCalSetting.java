/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

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

	/** The leave early. */
	// 遅刻早退
	protected AutoCalcOfLeaveEarlySetting leaveEarly;

	/** The raising salary. */
	// 加給
	protected AutoCalRaisingSalarySetting raisingSalary;

	/** The set of divergence time. */
	// 乖離時間
	protected AutoCalcSetOfDivergenceTime divergenceTime;

	@Deprecated
	//TODO: su dung constructor 6 tham so ben duoi do update domain
	public BaseAutoCalSetting(AutoCalOvertimeSetting normalOTTime, AutoCalFlexOvertimeSetting flexOTTime,
			AutoCalRestTimeSetting restTime) {
		super();
		this.normalOTTime = normalOTTime;
		this.flexOTTime = flexOTTime;
		this.restTime = restTime;
	}

	/**
	 * Instantiates a new base auto cal setting.
	 *
	 * @param normalOTTime the normal OT time
	 * @param flexOTTime the flex OT time
	 * @param restTime the rest time
	 * @param leaveEarly the leave early
	 * @param raisingSalary the raising salary
	 * @param divergenceTime the divergence time
	 */
	public BaseAutoCalSetting(AutoCalOvertimeSetting normalOTTime, AutoCalFlexOvertimeSetting flexOTTime,
			AutoCalRestTimeSetting restTime, AutoCalcOfLeaveEarlySetting leaveEarly,
			AutoCalRaisingSalarySetting raisingSalary, AutoCalcSetOfDivergenceTime divergenceTime) {
		super();
		this.normalOTTime = normalOTTime;
		this.flexOTTime = flexOTTime;
		this.restTime = restTime;
		this.leaveEarly = leaveEarly;
		this.raisingSalary = raisingSalary;
		this.divergenceTime = divergenceTime;
	}

}
