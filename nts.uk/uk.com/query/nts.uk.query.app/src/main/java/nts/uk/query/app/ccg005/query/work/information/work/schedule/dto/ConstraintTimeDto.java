package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConstraintTimeDto {
	//深夜拘束時間
	private Integer lateNightConstraintTime;
	
	//総拘束時間
	private Integer totalConstraintTime;
}
