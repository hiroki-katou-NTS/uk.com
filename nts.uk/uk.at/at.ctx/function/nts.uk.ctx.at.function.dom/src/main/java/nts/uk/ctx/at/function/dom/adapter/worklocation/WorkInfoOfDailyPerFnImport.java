package nts.uk.ctx.at.function.dom.adapter.worklocation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class WorkInfoOfDailyPerFnImport {
	private String employeeId;
	
	private GeneralDate ymd;

	public WorkInfoOfDailyPerFnImport(String employeeId, GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
	}
	
}
