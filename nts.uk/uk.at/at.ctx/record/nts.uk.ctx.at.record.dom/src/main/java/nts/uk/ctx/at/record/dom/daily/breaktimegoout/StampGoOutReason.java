package nts.uk.ctx.at.record.dom.daily.breaktimegoout;
/**
 * 外出理由
 * @author keisuke_hoshina
 *
 */
public enum StampGoOutReason {
	Private,
	Public,
	Compensation,
	Union;
	
	/**
	 * 私用か組合であるか判定する
	 * @return　私用か組合である
	 */
	public boolean isPrivateOrUnion() {
		return this.equals(Private)||this.equals(Union);
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
		return Private.equals(this);
	}
	
	/**
	 * 公用であるか判定する
	 * @return 公用である
	 */
	public boolean isPublic() {
		return Public.equals(this);
	}
	
	/**
	 * 有償であるか判定する
	 * @return　有償である
	 */
	public boolean isCompensation() {
		return Compensation.equals(this);
	}
	
	/**
	 * 組合であるか判定する
	 * @return　組合である
	 */
	public boolean isUnion() {
		return Union.equals(this);
	}
}
