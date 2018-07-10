package nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * join object of KrcmtBusinessTypeOfHistory and KrcmtBusinessTypeOfEmployee
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class BusinessTypeOfEmpDto {

	private String companyId;

	private String employeeId;

	private String historyId;

	private GeneralDate startDate;
	
	private GeneralDate endDate;

	private String businessTypeCd;
}
