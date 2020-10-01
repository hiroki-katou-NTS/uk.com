package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

public interface AttendanceItemService {

	public List<ItemValue> getItemBy(AttendanceItemType type, ValueType... types);

	public List<ItemValue> getTimeAndCountItem(AttendanceItemType type);
}
