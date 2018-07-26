package nts.uk.ctx.at.shared.app.util.attendanceitem;

import lombok.Getter;
import nts.arc.time.GeneralDate;

public abstract class DailyWorkCommonCommand implements GetSetCommonCommand {

	private boolean toDelete = false;

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

	public void shouldDeleteIfNull() {
		this.toDelete = true;
	}
	
	public boolean shouldDelete() {
		return this.toDelete;
	}
}
