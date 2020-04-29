/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EmployeeInfoDto {
		
	String sid;
	GeneralDate startDate;
	GeneralDate endDate;
}
