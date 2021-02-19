package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DigestionHourlyTimeTypeDto {
	/** 時間消化休暇かどうか */
	private boolean hourlyTimeType;
	/** 時間休暇種類 */
	private Integer appTimeType;
}
