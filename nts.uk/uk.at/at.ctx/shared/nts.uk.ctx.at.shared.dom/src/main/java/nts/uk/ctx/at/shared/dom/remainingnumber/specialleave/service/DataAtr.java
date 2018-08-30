package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;

/**
 * 特別休暇データ区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DataAtr {
	/**付与済	 */
	GRANTED(0),
	/**付与予定	 */
	GRANTSCHE(1);
	public final int value;

}
