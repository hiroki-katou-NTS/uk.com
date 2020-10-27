package nts.uk.ctx.at.record.app.command.dailyperform.checkdata;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@Data
public class DailyModifyRCResult {
	/** Attendance items */
	private List<ItemValue> items;

	/** Formatter information */
	private Object formatter;

	private String employeeId;

	private GeneralDate date;

	public static DailyModifyRCResult builder() {
		return new DailyModifyRCResult();
	}

	public DailyModifyRCResult employeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public DailyModifyRCResult items(List<ItemValue> items) {
		this.items = items;
		return this;
	}

	public DailyModifyRCResult workingDate(GeneralDate date) {
		this.date = date;
		return this;
	}

	public DailyModifyRCResult completed() {
		return this;
	}
}
