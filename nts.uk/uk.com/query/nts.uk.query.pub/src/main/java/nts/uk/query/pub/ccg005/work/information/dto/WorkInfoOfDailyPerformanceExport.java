package nts.uk.query.pub.ccg005.work.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class WorkInfoOfDailyPerformanceExport {

	// 社員ID
	private String sid;
	
	// 年月日
	private GeneralDate ymd;
	
	// 直行区分
	private Integer goStraightAtr;
	
	// 直帰区分
	private Integer backStraightAtr;
}
