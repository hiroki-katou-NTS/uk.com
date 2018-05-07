package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

public enum SysFixedMonPerEral {
	
	MON_UNCONFIRMED(1,"月次未確認"),
	
	WITH_MON_CORRECTION(2,"月次訂正あり"),
	
	NO_DAILY_ONE_MONTH(3,"1ヵ月分の日次データがない"),
	
	PRIORITY_DIGESTION_VACATION(4,"休暇の消化優先"),
	
	_36_CHECK_AGREEMENT(5,"36協定のチェック"),
	
	CHECK_DEADLINE_HOLIDAY(6,"★代休の消化期限チェック");
	
	public int value;
	
	public String nameId;

	private SysFixedMonPerEral(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	
}
