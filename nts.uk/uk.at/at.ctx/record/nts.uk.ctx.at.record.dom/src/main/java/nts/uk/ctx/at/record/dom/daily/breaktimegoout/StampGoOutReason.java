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

}
