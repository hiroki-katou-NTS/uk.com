package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import java.util.List;

public interface AtItemNameAdapter {
	List<AttItemNameImport> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type);
}
