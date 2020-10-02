package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation;

import lombok.AllArgsConstructor;

/**
 * 締め処理状態
 * @author shuichu_ishida
 */
@AllArgsConstructor
public enum ClosureStatus {
	/** 未締め */
	UNTREATED(0),
	/** 締め済 */
	PROCESSED(1);

	public final int value;
}
