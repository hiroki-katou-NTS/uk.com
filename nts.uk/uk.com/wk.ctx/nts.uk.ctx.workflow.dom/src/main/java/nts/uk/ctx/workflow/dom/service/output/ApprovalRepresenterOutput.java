package nts.uk.ctx.workflow.dom.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRepresenterOutput {
	
	/*・承認者の代行情報リスト（承認者が入る）
	※[承認者の社員ID, 代行情報]のセット
	　代行未設定：[承認者の社員ID, なし]
	　代行パスを設定：[承認者の社員ID, パス]
	　代行承認者を設定：[承認者の社員ID, 承認代行者の社員ID]*/
	private List<ApprovalRepresenterInforOutput> listApprovalAgentInfor;
	
	/*承認代行者リスト（代行者としての一覧）*/
	private List<String> listAgent;
	
	/*全承認者パス設定フラグ(true, false)
	   true：承認者リストに全員パス設定した
	   false：そうではない*/
	private Boolean allPathSetFlag;
	
}
