package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ApprovalRootMaster {
	
	/**
	 * フェーズ
	 */
	int phaseNumber;
	/**
	 * 承認形態
	 */
	String approvalForm;
	/**
	 * 社員名
	 */
	List<String> personName;

}
