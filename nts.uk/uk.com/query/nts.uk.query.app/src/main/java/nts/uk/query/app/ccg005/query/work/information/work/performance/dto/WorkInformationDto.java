package nts.uk.query.app.ccg005.query.work.information.work.performance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkInformationDto {
	/** 勤務種類コード **/
	private String workTypeCode;
	
	/** 就業時間帯コード **/
	private String workTimeCode;
}
