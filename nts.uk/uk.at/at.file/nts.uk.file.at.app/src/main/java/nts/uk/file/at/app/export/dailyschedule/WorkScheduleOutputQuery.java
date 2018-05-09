package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class WorkScheduleOutputQuery implements Cloneable {
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
	private Integer outputCode;
	
	private String workplaceId;
	
	private List<String> employeeId;
	
	private String userId;

	@Override
	public WorkScheduleOutputQuery clone() throws CloneNotSupportedException {
		return (WorkScheduleOutputQuery) super.clone();
	}
}
