package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 条件
 * @author keisuke_hoshina
 *
 */
public enum ConditionAtr {
	BREAK,
	PrivateGoOut,
	PublicGoOut,
	CompesationGoOut,
	UnionGoOut,
	Child,
	Care;
	
	/**
	 * 休憩であるか判定する
	 * @return　休憩である
	 */
	public boolean isBreak() {
		return BREAK.equals(this);
	}
}
