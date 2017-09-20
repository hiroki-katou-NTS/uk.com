package nts.uk.ctx.at.record.dom.dailyprocess.calc;
/**
 * 法定内区分
 * @author keisuke_hoshina
 *
 */
public enum WithinStatutoryAtr {
	WithinStatutory,
	ExcessOfStatutory;
	
	/**
	 * 法定内であるか判定する
	 * @return　法定内である
	 */
	public boolean isWithinStatutory() {
		return WithinStatutory.equals(this);
	}
	
	/**
	 * 法定外であるか判定する
	 * @return 法定外である
	 */
	public boolean isExcessOfStatutory() {
		return ExcessOfStatutory.equals(this);
	}
}
