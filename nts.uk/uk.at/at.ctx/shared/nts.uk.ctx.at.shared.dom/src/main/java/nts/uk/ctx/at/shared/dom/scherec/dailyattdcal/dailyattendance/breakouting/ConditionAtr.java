package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * 控除種別区分
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

	/**
	 * 変換する
	 * @return 外出理由
	 */
	public Optional<GoingOutReason> toGoingOutReason() {
		switch(this) {
		//私用
		case PrivateGoOut:
			return Optional.of(GoingOutReason.PRIVATE);
		//公用
		case PublicGoOut:
			return Optional.of(GoingOutReason.PUBLIC);
		//有償
		case CompesationGoOut:
			return Optional.of(GoingOutReason.COMPENSATION);
		//組合
		case UnionGoOut:
			return Optional.of(GoingOutReason.UNION);
		default:
			return Optional.empty();
		}
	}
}
