package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InforStatus {
	/**付与あり	 */
	GRANTED(0),
	/**付与なし	 */
	NOTGRANT(1),
	/**特別休暇不使用	 */
	NOTUSE(2),
	/**利用不可	 */
	OUTOFSERVICE(3);
	public final int value;

}
