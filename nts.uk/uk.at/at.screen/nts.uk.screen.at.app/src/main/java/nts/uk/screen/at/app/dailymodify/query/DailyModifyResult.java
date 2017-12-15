package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

@Data
public class DailyModifyResult {

	/** Attendance items*/
	private Map<String, List<ItemValue>> items;
	
	/** Formatter information*/
	private Object formatter;
}
