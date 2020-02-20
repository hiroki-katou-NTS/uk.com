package nts.uk.ctx.hr.notice.app.find.report.regis.person.approve;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeApproveDto {
	
	private int phraseNum;
	
	private int employeeNum;
	
	private String employeeName;
	
	private String status;
	
	private GeneralDate approveDate;
	
	private String comment;
	
}
