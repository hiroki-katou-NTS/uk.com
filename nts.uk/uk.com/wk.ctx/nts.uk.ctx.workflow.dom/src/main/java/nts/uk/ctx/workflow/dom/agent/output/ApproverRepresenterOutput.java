package nts.uk.ctx.workflow.dom.agent.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 承認者の代行情報リスト
 * 
 * @author tutk
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproverRepresenterOutput {
	String approver;
	String representer;
	
	public boolean isPass() {
		return this.representer.equals("Pass");
	}
}
