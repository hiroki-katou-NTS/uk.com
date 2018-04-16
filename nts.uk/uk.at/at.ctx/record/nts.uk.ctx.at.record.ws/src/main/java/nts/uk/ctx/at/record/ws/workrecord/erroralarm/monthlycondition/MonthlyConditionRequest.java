/**
 * 5:16:36 PM Mar 31, 2018
 */
package nts.uk.ctx.at.record.ws.workrecord.erroralarm.monthlycondition;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
public class MonthlyConditionRequest {
	public String checkId;
	public String errorCode;
}
