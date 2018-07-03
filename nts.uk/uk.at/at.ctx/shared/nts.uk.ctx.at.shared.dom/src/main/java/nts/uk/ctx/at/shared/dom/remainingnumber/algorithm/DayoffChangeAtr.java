package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;

/**
 * 代休発生元区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DayoffChangeAtr{
	/**
	 * 残業から代休発生
	 */
	OVERTIME(0),
	/**
	 * 休出から代休発生
	 */
	BREAKTIME(1);
	
	public final int value;
}
