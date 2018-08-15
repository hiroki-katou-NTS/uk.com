package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import lombok.AllArgsConstructor;
/**
 * 予定実績区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ManagermentAtr {
	/** 0: 日数 */
	DAYS(0),
	/** 1: 時間数 */
	TIMES(1);

	public final int value;
}
