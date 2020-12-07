package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttWkpEmpMailOutput {
	private String wkpID;
	
	private String wkpCD;
	
	private String wkpName;
	
	private String hierarchyCode;
	
	@Setter
	private int countEmp;
	
	@Setter
	private List<ApprSttEmpMailOutput> empMailLst;
}
