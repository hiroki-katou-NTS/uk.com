package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;
/**
 * システム固定の月別実績エラーアラーム
 * @author tutk
 *
 */
public enum SysFixedMonPerEral {
	
	MON_UNCONFIRMED(1,"Enum_SysFixedMonPerEral_MON_UNCONFIRMED"),
	
	WITH_MON_CORRECTION(2,"Enum_SysFixedMonPerEral_WITH_MON_CORRECTION"),
	
	NO_DAILY_ONE_MONTH(3,"Enum_SysFixedMonPerEral_NO_DAILY_ONE_MONTH"),
	
	PRIORITY_DIGESTION_VACATION(4,"Enum_SysFixedMonPerEral_PRIORITY_DIGESTION_VACATION"),
	
	_36_CHECK_AGREEMENT(5,"Enum_SysFixedMonPerEral_36_CHECK_AGREEMENT"),
	
	CHECK_DEADLINE_HOLIDAY(6,"Enum_SysFixedMonPerEral_CHECK_DEADLINE_HOLIDAY");
	
	public int value;
	
	public String nameId;

	private SysFixedMonPerEral(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	
}
