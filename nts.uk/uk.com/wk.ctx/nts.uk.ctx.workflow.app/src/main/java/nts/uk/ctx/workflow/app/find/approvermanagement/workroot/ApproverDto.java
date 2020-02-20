package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
@Data
@AllArgsConstructor
public class ApproverDto {
	/**承認者Gコード*/
	private String jobGCD;
	/**社員ID*/
	private String employeeId;
	/** 社員コード*/
	private String empCode;
	/**Name*/
	private String name;
	/**順序*/
	private int approverOrder;
	/**確定者*/
	private int confirmPerson;
	/**confirmPerson Name*/
	private String confirmName;
	/**特定職場ID*/
	private String specWkpId;
	
	public static ApproverDto fromDomain(Approver domain, String name , String confirmName, String empCode){
		return new ApproverDto(
					domain.getJobGCD(),
					domain.getEmployeeId(),
					empCode,
					name,
					domain.getApproverOrder(),
					domain.getConfirmPerson().value,
					confirmName,
					domain.getSpecWkpId());
	}
}
