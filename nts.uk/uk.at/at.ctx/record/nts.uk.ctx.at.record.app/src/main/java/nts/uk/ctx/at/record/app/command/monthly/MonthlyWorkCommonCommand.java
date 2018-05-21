package nts.uk.ctx.at.record.app.command.monthly;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.SetterCommonCommand;

public abstract class MonthlyWorkCommonCommand implements SetterCommonCommand {

	/** 年月: 年月 */
	@Getter
	private YearMonth yearMonth;

	/** 社員ID: 社員ID */
	@Getter
	private String employeeId;

	/** 締めID: 締めID */
	@Getter
	private int closureId;

	/** 締め日: 日付 */
	@Getter
	private ClosureDateDto closureDate;

	@Override
	public void withDate(GeneralDate date) {
	}


	@Override
	public void forEmployee(String employeId) {
		this.employeeId(employeId);
	}

	public abstract void updateData(Object data);

	public void yearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	public void employeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void closureId(int closureId) {
		this.closureId = closureId;
	}

	public void closureDate(ClosureDateDto closureDate) {
		this.closureDate = closureDate;
	}
}
