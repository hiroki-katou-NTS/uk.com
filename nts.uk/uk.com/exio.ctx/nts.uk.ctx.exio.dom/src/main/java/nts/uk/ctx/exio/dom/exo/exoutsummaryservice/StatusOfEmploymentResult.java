package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Setter
@Getter
public class StatusOfEmploymentResult {
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	private GeneralDate refereneDate;
	
	private int statusOfEmployment;
	
	private int tempAbsenceFrNo;

	public StatusOfEmploymentResult(String employeeId, GeneralDate refereneDate, int statusOfEmployment,
			int tempAbsenceFrNo) {
		super();
		this.employeeId = employeeId;
		this.refereneDate = refereneDate;
		this.statusOfEmployment = statusOfEmployment;
		this.tempAbsenceFrNo = tempAbsenceFrNo;
	}
	
}
