package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class TimeSpanForCalcDto {
	private Integer start;
	private Integer end;
}
