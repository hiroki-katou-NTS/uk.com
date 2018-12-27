package nts.uk.ctx.at.record.dom.daily.calcset;

/**
 * 外出と流動休憩の関係設定
 * @author keisuke_hoshina
 *
 */

public enum RelationSetOfGoOutAndFluBreakTime {
	Calculation,    //開始時刻が同じ場合は休憩と外出の両方を計算する
	norCalculation; //開始時刻が同じ場合は外出を計算しない
	
	/**
	 * 開始時刻が同じ場合は休憩と外出の両方を計算する　であるか判定する
	 * @return　開始時刻が同じ場合は休憩と外出の両方を計算する　である
	 */
	public boolean isCalculation() {
		return this.equals(Calculation);
	}
	
	
}
