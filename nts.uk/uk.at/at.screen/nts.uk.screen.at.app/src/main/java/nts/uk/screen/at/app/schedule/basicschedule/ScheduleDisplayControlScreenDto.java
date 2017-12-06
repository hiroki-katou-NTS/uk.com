package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDisplayControlScreenDto {

	/** 勤務就業記号表示区分 */
	public boolean symbolAtr;

	/** 半日表示区分 */
	public boolean symbolHalfDayAtr;

	/** 半日記号 */
	public String symbolHalfDayName;
}
