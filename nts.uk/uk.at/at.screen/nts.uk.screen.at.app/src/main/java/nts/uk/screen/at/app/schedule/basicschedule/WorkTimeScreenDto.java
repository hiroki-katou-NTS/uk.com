package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimeScreenDto {
	private String workTimeCode;
	private String name;
	private String abName;
	private String symbol;
	private int dailyWorkAtr;
	private int worktimeSetMethod;
	private int abolitionAtr;
	private String color;
	private String memo;
	private String note;
	private int workNo;
	private int useAtr;
	private int startTime;
	private int endTime;
}
