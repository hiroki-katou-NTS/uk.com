package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

/**
 * 実働のみで計算するしない区分
 * @author keisuke_hoshina
 *
 */
public enum CalculationByActualTimeAtr {
	CalculationByActualTime,
	CalculationOtherThanActualTime;
	
	/**
	 * 実働時間のみで計算するか判定する
	 * @return　実働時間のみで判定する
	 */
	public boolean isCalclationByActualTime() {
		return CalculationByActualTime.equals(this);
	}
}
