package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidaysRemainingEmployee {
	public HolidaysRemainingEmployee(String employeeCode, String employeeId, String employeeName, String workplaceCode,
			String workplaceId, String workplaceName) {
		super();
		this.employeeCode = employeeCode;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.workplaceCode = workplaceCode;
		this.workplaceId = workplaceId;
		this.workplaceName = workplaceName;
	}
	private String employeeCode;
	private String employeeId;
	private String employeeName;
	private String workplaceCode;
	private String workplaceId;
	private String workplaceName;
}
