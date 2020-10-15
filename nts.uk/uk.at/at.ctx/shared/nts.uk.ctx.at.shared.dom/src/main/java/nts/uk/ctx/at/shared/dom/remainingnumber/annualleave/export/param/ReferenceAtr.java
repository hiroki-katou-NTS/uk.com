package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
/**
 * 参照先区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ReferenceAtr {
	/**
	 * 実績	
	 */
	RECORD(0),
	/**
	 * 予定・申請を含む
	 */
	APP_AND_SCHE(1);
	public final int value;
}
