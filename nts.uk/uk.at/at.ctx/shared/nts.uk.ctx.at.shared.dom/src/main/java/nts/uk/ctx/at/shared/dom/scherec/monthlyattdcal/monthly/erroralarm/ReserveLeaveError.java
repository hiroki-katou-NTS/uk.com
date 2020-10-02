package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm;

import lombok.AllArgsConstructor;

/**
 * 積立年休エラー
 * @author shuichu_ishida
 */
@AllArgsConstructor
public enum ReserveLeaveError {
	/** 積立年休不足エラー（付与前） */
	SHORTAGE_RSVLEA_BEFORE_GRANT(0),
	/** 積立年休不足エラー（付与後） */
	SHORTAGE_RSVLEA_AFTER_GRANT(1);

	public final int value;
}
