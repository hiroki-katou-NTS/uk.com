package nts.uk.ctx.workflow.dom.agent.output;

import java.util.List;

import lombok.Value;

@Value
public class ApprovalAgencyInfoOutput {
	/** 承認者の代行情報リスト*/
	List<ApproverRepresenterOutput> listApproverAndRepresenterSID;
	
	/** 承認代行者リスト*/
	List<String> listRepresenterSID;

	/**   
	 * 全承認者パス設定フラグ
	 * true：承認者リストに全員パス設定した, false：そうではない
	 */
	boolean flag;
}
