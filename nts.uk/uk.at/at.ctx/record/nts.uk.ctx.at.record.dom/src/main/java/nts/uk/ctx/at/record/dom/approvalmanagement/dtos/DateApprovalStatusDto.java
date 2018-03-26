/**
 * 3:24:01 PM Mar 13, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.dtos;

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
public class DateApprovalStatusDto {
	
	private GeneralDate date;

	private int status;
}
