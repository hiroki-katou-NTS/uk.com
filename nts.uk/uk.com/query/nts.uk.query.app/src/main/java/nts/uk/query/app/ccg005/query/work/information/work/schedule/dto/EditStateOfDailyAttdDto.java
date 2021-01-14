package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditStateOfDailyAttdDto {
	/** 勤怠項目ID: 勤怠項目ID */
	private int attendanceItemId;
	
	/** 編集状態: 日別実績の編集状態 */
	private Integer editStateSetting;
}
