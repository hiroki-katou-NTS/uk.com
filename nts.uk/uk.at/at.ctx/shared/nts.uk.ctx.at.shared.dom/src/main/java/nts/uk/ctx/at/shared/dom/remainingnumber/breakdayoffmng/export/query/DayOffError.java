package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import lombok.AllArgsConstructor;

/**
 * 代休エラー
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DayOffError {
	/**
	 * 日単位代休残数不足エラー
	 */
	DAYERROR(0),
	/**
	 * 時間単位代休残数不足エラー
	 */
	TIMEERROR(1);
	public final Integer value;
}
