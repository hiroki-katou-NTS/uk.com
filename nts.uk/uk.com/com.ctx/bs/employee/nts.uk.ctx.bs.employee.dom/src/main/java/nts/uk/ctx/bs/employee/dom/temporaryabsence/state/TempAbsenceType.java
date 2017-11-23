package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

public enum TempAbsenceType {
	// 休職
	LEAVE_OF_ABSENCE(1),
	// 産前休業
	MIDWEEK_CLOSURE(2),
	// 産後休業
	AFTER_CHILDBIRTH(3),
	// 育児介護
	CHILD_CARE_NURSING(4),
	// 介護休業
	NURSING_CARE_LEAVE(5);

	public int value;

	TempAbsenceType(int value) {
		this.value = value;
	}

}
