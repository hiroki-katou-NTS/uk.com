package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApproverStateForAppDto {
	
	private String approverID;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private String approverName;
	
	private String approvalDate;
	
	private String approvalReason;
	
	private String approverMail;
}
