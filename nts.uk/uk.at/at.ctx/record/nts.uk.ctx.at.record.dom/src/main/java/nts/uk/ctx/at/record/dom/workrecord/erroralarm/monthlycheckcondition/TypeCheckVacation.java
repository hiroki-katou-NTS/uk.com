package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

public enum TypeCheckVacation {
	
	ANNUAL_PAID_LEAVE(0,"年次有給休暇"),
	
	SUB_HOLIDAY(1,"代休"),
	
	PAUSE(2,"振休"),
	
	YEARLY_RESERVED(3,"積立年休"),
	
	_64H_SUPER_HOLIDAY(4,"60H超休"),
	
	PUBLIC_HOLIDAY(5,"公休"),
	
	SPECIAL_HOLIDAY(6,"特休"),
	
	NURSING_CARE_LEAVE(7,"看護・介護休");
	
	public int value;
	
	public String nameId;

	private TypeCheckVacation(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
