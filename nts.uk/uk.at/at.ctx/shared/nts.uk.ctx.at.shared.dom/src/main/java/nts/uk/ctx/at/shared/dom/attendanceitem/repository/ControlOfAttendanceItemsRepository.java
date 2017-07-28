package nts.uk.ctx.at.shared.dom.attendanceitem.repository;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.attendanceitem.ControlOfAttendanceItems;

public interface ControlOfAttendanceItemsRepository {

	Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(BigDecimal attendanceItemId);
	
	void updateControlOfAttendanceItem(ControlOfAttendanceItems	controlOfAttendanceItems);	
}
