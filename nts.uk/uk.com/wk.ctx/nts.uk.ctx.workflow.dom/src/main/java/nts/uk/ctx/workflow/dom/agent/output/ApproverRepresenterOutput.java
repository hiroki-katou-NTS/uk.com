package nts.uk.ctx.workflow.dom.agent.output;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 承認者の代行情報リスト
 * 
 * @author tutk
 *
 */
@Value
@AllArgsConstructor
public class ApproverRepresenterOutput {
	String approver;
	RepresenterInformationOutput representer;
	
	public boolean isPass() {
		return this.representer.getValue().equals(RepresenterInformationOutput.Path_Information);
	}
}
