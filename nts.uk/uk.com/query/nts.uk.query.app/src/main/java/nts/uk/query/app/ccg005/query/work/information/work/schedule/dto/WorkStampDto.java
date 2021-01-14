package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkStampDto {
	/*
	 * 時刻
	 */
	private WorkTimeInformationDto timeDay;
	
	/*
	 * 場所コード
	 */
	private String locationCode;
}
