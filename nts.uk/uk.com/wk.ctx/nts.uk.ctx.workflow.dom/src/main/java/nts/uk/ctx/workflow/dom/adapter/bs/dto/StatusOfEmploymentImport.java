package nts.uk.ctx.workflow.dom.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class StatusOfEmploymentImport {
	
	private String employeeId;
	
	private GeneralDate refereneDate;
	
	private StatusOfEmployment statusOfEmployment;
	
	private int tempAbsenceFrNo;
	
}
