package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;
/**
 * システム固定の月別実績エラーアラーム
 * @author tutk
 *
 */
public enum SysFixedMonPerEral {
	/**1 本人未確認	 */
	MYSELF_UNCONFIRMED(1," 本人未確認"),
	/**2 管理者未確認	 */
	ADMIN_UNCONFIRMED(2,"管理者未確認"),
	/**3 1ヶ月分の日次データがない	 */
	NO_DAILY_ONE_MONTH(3,"1ヶ月分の日次データがない"),
	/**4 休暇の消化優先	 */
	PRIORITY_DIGESTION_VACATION(4,"休暇の消化優先"),
	/**5 月次訂正あり	 */
	MONTHLY_CORRECTION(5,"月次訂正あり"),
	/**6 代休の消化期限チェック	 */
	CHECK_DEADLINE_HOLIDAY(6,"月次訂正あり"),
	/**手入力	 */
	MANUAL_INPUT(7, "手入力");
	
	public int value;
	
	public String nameId;

	private SysFixedMonPerEral(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	
}
