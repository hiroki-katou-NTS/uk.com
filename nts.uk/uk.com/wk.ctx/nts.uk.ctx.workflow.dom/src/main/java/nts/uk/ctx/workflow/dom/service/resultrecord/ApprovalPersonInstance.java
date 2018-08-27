package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 承認者になる中間データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApprovalPersonInstance {
	
	/**
	 * 承認者としての承認ルート
	 */
	private List<ApprovalRouteDetails> approverRoute;
	
	/**
	 * 代行者としての承認ルート
	 */
	private List<ApprovalRouteDetails> agentRoute;
	
}
