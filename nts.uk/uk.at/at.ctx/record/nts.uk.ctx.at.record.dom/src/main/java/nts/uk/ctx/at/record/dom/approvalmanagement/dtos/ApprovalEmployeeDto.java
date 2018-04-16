/**
 * 3:23:04 PM Mar 13, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalEmployeeDto {
	
	private String employeeId;

	private String employeeCode;
	
	private String employeeName;
	
	private List<DateApprovalStatusDto> lstStatus;
	
}
