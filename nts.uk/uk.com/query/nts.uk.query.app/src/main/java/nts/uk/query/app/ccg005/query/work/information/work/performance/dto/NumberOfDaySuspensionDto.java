package nts.uk.query.app.ccg005.query.work.information.work.performance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NumberOfDaySuspensionDto {
	//振休振出日数
	private Integer days;

	//振休振出区分
	private Integer classifiction;
}
