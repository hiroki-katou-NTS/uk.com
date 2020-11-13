package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
	
	private int countEmp;
	
	private List<ApprSttEmpMailOutput> empMailLst;
}
