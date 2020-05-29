package nts.uk.ctx.at.record.dom.raborstandardact;

import lombok.AllArgsConstructor;

/**
 * フレックス計算方法
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
public enum FlexCalcMethod {
	Half(0),
	OneDay(1);
	
	public final int value;
	
	/**
	 * 半日かどうか判定する
	 * @return　半日である
	 */
	public boolean isHalf() {
		return Half.equals(this);
	}
}
