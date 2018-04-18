package nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.flex;

import lombok.AllArgsConstructor;

/**
 * フレックス勤務の所定時間参照
 * @author shuichu_ishida
 */
@AllArgsConstructor
public enum ReferencePredTimeOfFlex {
	/** マスタから参照 */
	FROM_MASTER(0),
	/** 実績から参照 */
	FROM_RECORD(1);
	
	public int value;
}
