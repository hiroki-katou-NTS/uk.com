package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum OverrideSet {
	/**
	 * システム時刻優先
	 */
	SYSTEM_TIME_PRIORITY(0),
	/**
	 * 退勤時刻優先
	 */
	TIME_OUT_PRIORITY(1);
	public final int value;
}
