package nts.uk.ctx.at.record.dom.dailyprocess.calc.converter;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public interface DailyRecordToAttendanceItemConverter {

	Optional<ItemValue> convert(int attendanceItemId);

	List<ItemValue> convert(List<Integer> attendanceItemIds);
}
