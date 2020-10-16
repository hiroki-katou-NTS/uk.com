package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;

public interface ConvertibleAttendanceItem extends ItemConst, AttendanceItemDataGate {
	
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
