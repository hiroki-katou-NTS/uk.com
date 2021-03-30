package nts.uk.ctx.office.dom.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

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
}
