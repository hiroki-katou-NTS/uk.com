package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

@Data
public class DailyModifyResult {

	/** Attendance items*/
	private List<ItemValue> items;
	
	/** Formatter information*/
	private Object formatter;
	
	private String employeeId;
	
	private GeneralDate date;
}
