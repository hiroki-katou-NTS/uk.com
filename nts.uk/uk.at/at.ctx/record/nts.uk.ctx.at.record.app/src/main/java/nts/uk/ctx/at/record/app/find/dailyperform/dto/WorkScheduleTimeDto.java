package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDto {

	/** 合計時間: 勤怠時間 */
	//TODO: confirm 
	private Integer total;
	
	/** 所定外時間: 勤怠時間 */
	private Integer excessOfStatutoryTime; 
	
	/** 所定内時間: 勤怠時間 */
	private Integer withinStatutoryTime;
}
