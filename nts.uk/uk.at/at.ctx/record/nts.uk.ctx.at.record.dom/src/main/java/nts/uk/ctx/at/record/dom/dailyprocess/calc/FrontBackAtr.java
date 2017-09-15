package nts.uk.ctx.at.record.dom.dailyprocess.calc;


/**
 * 控除項目の優先度
 * @author keisuke_hoshina
 *
 */
public enum FrontBackAtr {
	Forword,
	Back;
	
	/**
	 * 前優先かどうか判定する
	 * @return　前優先である
	 */
	public boolean isForword() {
		return Forword.equals(this);
	}
}
