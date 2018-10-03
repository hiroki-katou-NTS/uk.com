package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;

/**
 * 条件
 * @author keisuke_hoshina
 *
 */
public enum ConditionAtr {
	//休憩
	BREAK,
	//私用
	PrivateGoOut,
	//公用
	PublicGoOut,
	//有償
	CompesationGoOut,
	//組合
	UnionGoOut,
	//育児
	Child,
	//介護
	Care;
	
	/**
	 * 休憩であるか判定する
	 * @return　休憩である
	 */
	public boolean isBreak() {
		return BREAK.equals(this);
	}
	
	/**
	 * 育児であるか判定する
	 * @return 育児である
	 */
	public boolean isChild() {
		return Child.equals(this);
	}
	
	/**
	 * 介護であるか判定する
	 * @return　介護である
	 */
	public boolean isCare() {
		return Care.equals(this);
	}
	
	public static ConditionAtr convertFromGoOutReason(GoingOutReason goingOutReason) {
		switch(goingOutReason) {
				//有償
			case COMPENSATION:
				return CompesationGoOut;
				// 組合
			case UNION:
				return UnionGoOut;
				// 私用
			case PRIVATE:
				return PrivateGoOut;
				//公用
			case PUBLIC:
				return PublicGoOut;
			default:
				throw new RuntimeException("unknown GoOutReason in ConditionAtr:"+goingOutReason);
		}
	}
}
