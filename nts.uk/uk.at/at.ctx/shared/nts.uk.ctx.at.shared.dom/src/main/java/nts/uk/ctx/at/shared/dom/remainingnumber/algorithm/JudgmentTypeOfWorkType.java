package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JudgmentTypeOfWorkType {
	/**	残数使用対象 */
	REMAIN(0),
	/**	残数発生使用対象外 */
	REMAINOCCNOTCOVER(1),
	/**	残数発生対象 */
	REMAINOCC(2);
	public final Integer value;
}
