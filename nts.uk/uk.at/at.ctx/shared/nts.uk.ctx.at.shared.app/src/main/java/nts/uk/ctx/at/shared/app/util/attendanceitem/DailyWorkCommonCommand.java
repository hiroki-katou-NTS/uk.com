package nts.uk.ctx.at.shared.app.util.attendanceitem;

import lombok.Getter;
import nts.arc.time.GeneralDate;

public abstract class DailyWorkCommonCommand implements SetterCommonCommand {

	@Getter
	private String employeeId;
	
	@Getter
	private GeneralDate workDate;

	@Override
	public void forEmployee(String employeId) {
		this.employeeId = employeId;
	}

	@Override
	public void withDate(GeneralDate date) {
		this.workDate = date;
	}
	
	public abstract void updateData(Object data);
}
