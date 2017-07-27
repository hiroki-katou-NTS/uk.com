package nts.uk.ctx.at.shared.dom.attendanceitem.repository;


import java.util.List;

import nts.uk.ctx.at.shared.dom.attendanceitem.DisplayAndInputControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;

public interface DAIControlOfAttendanceItemsRepository {
	
	List<DisplayAndInputControlOfAttendanceItems> getListControlOfAttendanceItem(BusinessTypeCode workTypeCode);
	
	void updateListControlOfAttendanceItem(List<DisplayAndInputControlOfAttendanceItems> lstDisplayAndInputControlOfAttendanceItems);

}
