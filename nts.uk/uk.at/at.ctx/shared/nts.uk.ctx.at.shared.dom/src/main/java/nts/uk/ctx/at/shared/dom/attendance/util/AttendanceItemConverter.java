package nts.uk.ctx.at.shared.dom.attendance.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;


public interface AttendanceItemConverter {
	
	OptionalItemRepository getOptionalItemRepo();

	Optional<ItemValue> convert(int attendanceItemId);

	List<ItemValue> convert(Collection<Integer> attendanceItemIds);
	
	void merge(ItemValue value);
	
	void merge(Collection<ItemValue> values);
}
