package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public interface GetSetCommonCommand extends ItemConst {

	public void setRecords(ConvertibleAttendanceItem item);
	
	public void forEmployee(String employeId);
	
	public void withDate(GeneralDate date);
	
	public default void updateData(Object data) {}
	
	public default void updateDatas(List<?> data) {
		data.stream().forEach(c -> updateData(c));
	}
	
	public default void updateDataO(Optional<?> data) {
		updateData(data.orElse(null));
	}
	
	public default Object toDomain() { return null; }
	
	public default Object toDto() { return null; }
}
