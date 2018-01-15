package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;


public interface ControlOfAttendanceItemsRepository {

	Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(BigDecimal attendanceItemId);
	
	void updateControlOfAttendanceItem(ControlOfAttendanceItems	controlOfAttendanceItems);	
}
