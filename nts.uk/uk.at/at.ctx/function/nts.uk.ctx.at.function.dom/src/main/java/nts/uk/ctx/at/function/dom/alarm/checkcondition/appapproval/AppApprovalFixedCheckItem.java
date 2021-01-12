package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

/**
 * 申請承認の固定のチェック項目
 */
public enum AppApprovalFixedCheckItem {
	/**1 未承認１	 */
	NOT_APPROVED_1(1, "未承認１"),
	/**2 未承認2	 */
	NOT_APPROVED_2(2, "未承認２"),
	/**3 未承認3	 */
	NOT_APPROVED_3(3, "未承認３"),
	/**4 未承認4	 */
	NOT_APPROVED_4(4, "未承認４"),
	/**5 未承認5	 */
	NOT_APPROVED_5(5, "未承認５"),
	/**6 未承認（反映条件未達） */
	NOT_APPROVED_COND_NOT_SATISFY(6, "未承認（反映条件未達）"),
	/**7 否認	 */
	DISAPPROVE(7, "否認"),
	/**8 未反映（反映条件達成）	 */
	NOT_REFLECT(8, "未反映（反映条件達成）"),
	/**9 代理承認	 */
	REPRESENT_APPROVE(9, "代理承認"),
	/**10 要承認	 */
	APPROVE(10, "要承認"),
	/**11 承認者未指定	 */
	APPROVER_NOT_SPECIFIED(11, "承認者未指定"),
	/**12 残業申請の事後申請の提出漏れ*/
	MISS_OT_APP(12, "残業申請の事後申請の提出漏れ"),
	/**13 休日出勤申請の事後申請の提出漏れ	 */
	MISS_WORK_IN_HOLIDAY_APP(13, "休日出勤申請の事後申請の提出漏れ");

	public int value;
	
	public String name;
	
	private AppApprovalFixedCheckItem(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static AppApprovalFixedCheckItem valueOf(int value) {
		for (AppApprovalFixedCheckItem item : AppApprovalFixedCheckItem.values()) {
			if (item.value == value) return item;
		}
		
		return null;
	}
	
	public static AppApprovalFixedCheckItem fromName(String name) {
		for (AppApprovalFixedCheckItem item : AppApprovalFixedCheckItem.values()) {
			if (item.name.equals(name)) return item;
		}
		
		return null;
	}
}
