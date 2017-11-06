package nts.uk.ctx.at.record.dom.breakorgoout.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 外出理由
 *
 */
@AllArgsConstructor
public enum GoingOutReason {
	
	/* 私用 */
	PRIVATE(0),
	/* 公用 */
	PUBLIC(1),
	/* 有償 */
	COMPENSATION(2),
	/* 組合 */
	UNION(3);
	
	public final int value;

	/**
	 * 私用か組合であるか判定する
	 * @return　私用か組合である
	 */
	public boolean isPrivateOrUnion() {
		return this.equals(PRIVATE)||this.equals(UNION);
	}
	
	/**
	 * 公用か有償であるか判定する
	 * @return　公用か有償である
	 */
	public boolean isPublicOrCmpensation() {
		return !isPrivateOrUnion();
	}

	
	/**
	 * 私用であるか判定する
	 * @return 私用である
	 */
	public boolean isPrivate(){
		return PRIVATE.equals(this);
	}
	
	/**
	 * 公用であるか判定する
	 * @return 公用である
	 */
	public boolean isPublic() {
		return PUBLIC.equals(this);
	}
	
	/**
	 * 有償であるか判定する
	 * @return　有償である
	 */
	public boolean isCompensation() {
		return COMPENSATION.equals(this);
	}
	
	/**
	 * 組合であるか判定する
	 * @return　組合である
	 */
	public boolean isUnion() {
		return UNION.equals(this);
	}
}
