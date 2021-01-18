package nts.uk.query.app.ccg005.query.work.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

@Data
@Builder
public class WorkInfoOfDailyPerformanceDto {

	// 社員ID
	private String sid;
	
	// 年月日
	private GeneralDate ymd;
	
	// 直行区分
	private Integer goStraightAtr;
	
	// 直帰区分
	private Integer backStraightAtr;
	
	public static WorkInfoOfDailyPerformanceDto toDto (WorkInfoOfDailyPerformance domain) {
		if (domain == null) {
			return WorkInfoOfDailyPerformanceDto.builder().build();
		}
		return WorkInfoOfDailyPerformanceDto.builder()
				.sid(domain.getEmployeeId())
				.ymd(domain.getYmd())
				.goStraightAtr(domain.getWorkInformation().getGoStraightAtr().value)
				.backStraightAtr(domain.getWorkInformation().getBackStraightAtr().value)
				.build();
	}
}
