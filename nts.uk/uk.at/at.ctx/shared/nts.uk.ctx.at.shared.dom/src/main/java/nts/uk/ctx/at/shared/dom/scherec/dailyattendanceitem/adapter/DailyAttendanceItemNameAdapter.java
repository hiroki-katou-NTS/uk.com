package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter;

import java.util.List;
import java.util.Map;

public interface DailyAttendanceItemNameAdapter {
	
	List<DailyAttendanceItemNameAdapterDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds);
	
	Map<Integer, String> getDailyAttendanceItemNameAsMapName(List<Integer> dailyAttendanceItemIds);

}
