package nts.uk.ctx.workflow.dom.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 承認を行った承認者
 * （承認者の社員ID, 代行者フラグ）
      true：承認代行者が承認を行った
      false：承認者が承認を行った。
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApproverWithFlagOutput {
	
	private String employeeID;
	
	private Boolean agentFlag;
	
}
