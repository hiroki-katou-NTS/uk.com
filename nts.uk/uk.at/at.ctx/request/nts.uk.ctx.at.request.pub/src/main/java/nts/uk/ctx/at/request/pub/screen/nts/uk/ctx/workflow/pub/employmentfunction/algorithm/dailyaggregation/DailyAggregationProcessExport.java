package nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation;

import lombok.Data;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class DailyAggregationProcessExport {

	/**申請者*/
	private String employeeID;
	
	/**申請日*/
	private GeneralDate appDate;
	
	/**申請種類*/
	private Integer appType;
	
	/**申請表示名\ */
	private String appTypeName;
	
}
