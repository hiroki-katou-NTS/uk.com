package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheLogDto {
	
	private int attendanceItemId;
	
	private String attendanceItemName;
	
	private String before;
	
	private String after;

	private int attr;
	
	private int valueType;
	
}
