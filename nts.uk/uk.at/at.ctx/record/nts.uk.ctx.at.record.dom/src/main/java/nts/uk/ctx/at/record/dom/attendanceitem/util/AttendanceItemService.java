package nts.uk.ctx.at.record.dom.attendanceitem.util;

import java.util.List;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

public interface AttendanceItemService {

	public List<ItemValue> getItemBy(AttendanceItemType type, ValueType... types);

	public List<ItemValue> getTimeAndCountItem(AttendanceItemType type);
}
