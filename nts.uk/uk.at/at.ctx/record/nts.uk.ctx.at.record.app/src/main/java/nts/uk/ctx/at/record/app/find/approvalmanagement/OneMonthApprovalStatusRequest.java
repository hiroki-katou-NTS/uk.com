/**
 * 4:58:36 PM Mar 13, 2018
 */
package nts.uk.ctx.at.record.app.find.approvalmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneMonthApprovalStatusRequest {
	private Integer closureIdParam;
	private GeneralDate startDateParam;
	private GeneralDate endDateParam;
}
