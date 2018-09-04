package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasicScheItemValueDto {

	private String employeeId;
	
	private GeneralDate date;
	
	private int attendanceItemId;
	
	private String attendanceItemName;
	
	private String value;
	
	private int attr;
	
	private int valueType;
}
