package nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.flex;

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
