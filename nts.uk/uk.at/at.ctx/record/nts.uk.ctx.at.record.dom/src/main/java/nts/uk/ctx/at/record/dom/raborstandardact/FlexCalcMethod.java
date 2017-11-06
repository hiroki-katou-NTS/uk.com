package nts.uk.ctx.at.record.dom.raborstandardact;

/**
 * フレックス計算方法
 * @author keisuke_hoshina
 *
 */
public enum FlexCalcMethod {
	Half,
	OneDay;
	
	/**
	 * 半日かどうか判定する
	 * @return　半日である
	 */
	public boolean isHalf() {
		return Half.equals(this);
	}
}
