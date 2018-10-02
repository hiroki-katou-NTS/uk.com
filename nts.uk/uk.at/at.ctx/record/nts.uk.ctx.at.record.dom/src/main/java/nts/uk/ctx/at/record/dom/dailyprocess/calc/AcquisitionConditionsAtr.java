package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 取得条件区分
 * @author keisuke_hoshina
 *
 */
public enum AcquisitionConditionsAtr {
	All,         //全て
	ForDeduction;//控除のみ
	
	/**
	 * 控除用か判定する
	 * @return　控除用である
	 */
	public boolean isForDeduction() {
		return ForDeduction.equals(this);
	}
}
