package nts.uk.ctx.at.shared.dom.attendance.util.item;

import nts.arc.time.GeneralDate;

public interface ConvertibleAttendanceItem {
	
	String employeeId();
	
	GeneralDate workingDate();
	
	default Object toDomain(){
		return toDomain(employeeId(), workingDate());
	}
	
	Object toDomain(String employeeId, GeneralDate date);
	
	default boolean isHaveData() {
		return false;
	}
	
	default void exsistData() { }
}
