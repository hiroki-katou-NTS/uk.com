package nts.uk.ctx.at.record.pub.workinformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class WorkInfoOfDailyPerExport {
	
	private String employeeId;
	
	private GeneralDate ymd;

	public WorkInfoOfDailyPerExport(String employeeId, GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
	}
	
	
}
