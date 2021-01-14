package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PremiumTimeOfDailyPerformanceDto {
	private List<PremiumTimeDto> premiumTimes;
}
