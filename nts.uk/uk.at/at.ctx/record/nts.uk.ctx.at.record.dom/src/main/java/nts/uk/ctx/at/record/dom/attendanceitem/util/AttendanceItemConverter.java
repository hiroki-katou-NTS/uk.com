package nts.uk.ctx.at.record.dom.attendanceitem.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public interface AttendanceItemConverter {
	
	OptionalItemRepository getOptionalItemRepo();

	Optional<ItemValue> convert(int attendanceItemId);

	List<ItemValue> convert(Collection<Integer> attendanceItemIds);
	
	void merge(ItemValue value);
	
	void merge(Collection<ItemValue> values);
}
