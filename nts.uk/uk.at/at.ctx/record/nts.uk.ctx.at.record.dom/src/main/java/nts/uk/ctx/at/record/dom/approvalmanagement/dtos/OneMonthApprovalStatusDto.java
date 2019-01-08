/**
 * 5:53:25 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneMonthApprovalStatusDto {

	List<ClosureDto> lstClosure;

	GeneralDate startDate;

	GeneralDate endDate;

	List<ApprovalEmployeeDto> lstEmployee;
	
	String messageID;
	
}
