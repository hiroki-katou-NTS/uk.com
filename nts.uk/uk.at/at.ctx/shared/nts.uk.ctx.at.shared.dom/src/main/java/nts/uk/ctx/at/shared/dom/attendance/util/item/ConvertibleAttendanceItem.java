package nts.uk.ctx.at.shared.dom.attendance.util.item;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;

public interface ConvertibleAttendanceItem extends ItemConst {
	
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
