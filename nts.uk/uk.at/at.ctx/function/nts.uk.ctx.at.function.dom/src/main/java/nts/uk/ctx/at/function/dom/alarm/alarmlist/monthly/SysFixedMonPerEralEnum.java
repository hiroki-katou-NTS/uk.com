package nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly;

/**
 * システム固定の月別実績エラーアラーム
 * @author thuongtv
 *
 */
public enum SysFixedMonPerEralEnum {
	/**本人未確認	 */
	MON_UNCONFIRMED(1,"Enum_SysFixedMonPerEral_MON_UNCONFIRMED"),
	/** 管理者未確認	 */
	WITH_MON_CORRECTION(2,"Enum_SysFixedMonPerEral_WITH_MON_CORRECTION"),
	/**1ヶ月分の日次データがない	 */
	NO_DAILY_ONE_MONTH(3,"Enum_SysFixedMonPerEral_NO_DAILY_ONE_MONTH"),
	/**休暇の消化優先	 */
	PRIORITY_DIGESTION_VACATION(4,"Enum_SysFixedMonPerEral_PRIORITY_DIGESTION_VACATION"),
	/**	36協定のチェック        月次訂正あり*/
	_36_CHECK_AGREEMENT(5,"Enum_SysFixedMonPerEral_36_CHECK_AGREEMENT"),
	/**代休の消化期限チェック	 */
	CHECK_DEADLINE_HOLIDAY(6,"Enum_SysFixedMonPerEral_CHECK_DEADLINE_HOLIDAY");
	
	public int value;
	
	public String nameId;

	private SysFixedMonPerEralEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	
}
