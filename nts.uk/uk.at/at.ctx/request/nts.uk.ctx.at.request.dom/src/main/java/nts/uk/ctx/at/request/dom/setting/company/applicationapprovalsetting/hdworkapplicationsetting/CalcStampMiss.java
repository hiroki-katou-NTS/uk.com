package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum CalcStampMiss {
	/**
	 * 登録不可
	 */
	CAN_NOT_REGIS(0),
	/**
	 * システム時刻仮計算
	 */
	CALC_STAMP_MISS(1);
	public final int value;
}
