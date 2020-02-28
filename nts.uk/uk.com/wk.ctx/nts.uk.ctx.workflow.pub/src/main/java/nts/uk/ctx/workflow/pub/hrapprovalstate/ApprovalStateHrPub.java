package nts.uk.ctx.workflow.pub.hrapprovalstate;

import java.util.List;

import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.PhaseStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.output.ApprovalRootStateHrExport;

public interface ApprovalStateHrPub {
	/**
	 * 1.人事承認フェーズ毎の承認者を取得する(getApproverFromPhase)
	 * @author hoatt
	 * @param 人事承認フェーズ phaseHr
	 * @return 承認者社員ID一覧
	 */
	public List<String> getApproverFromPhaseHr(PhaseStateHrImport phaseHr);
	/**
	 * [RQ631]申請書の承認者と状況を取得する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @return
	 */
	public ApprovalRootStateHrExport getApprovalRootStateHr(String rootStateID);
	/**
	 * [RQ632]申請書を承認する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 承認フェーズ枠番
	 */
	public Integer approveHr(String rootStateID, String employeeID, String comment);
	/**
	 * [RQ633]申請書を否認する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 否認を実行したかフラグ(true, false)
					true：否認を実行した
					false：否認を実行しなかった
	 */
	public Boolean denyHr(String rootStateID, String employeeID, String comment);
	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（承認者まで）
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 「人事承認フェーズインスタンス」．順序 phaseOrder
	 */
	public void remandForApproverHr(String rootStateID, Integer phaseOrder);
	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（申請本人まで）
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 */
	public void remandForApplicantHr(String rootStateID);
	/**
	 * [RQ635]申請書を解除する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @return ・解除を実行したかフラグ(true, false)
					true：解除を実行した
					false：解除を実行しなかった
	 */
	public Boolean releaseHr(String rootStateID, String employeeID);
	/**
	 * [RQ637]承認ルートインスタンスを新規作成する
	 * @author hoatt
	 * @param 人事承認状態 apprSttHr
	 * @return エラーフラグ　＝　True 失敗した場合 
	 * 		        エラーフラグ　＝　False OK場合 
	 */
	public boolean createApprStateHr(ApprovalStateHrImport apprSttHr);
	/**
	 * 2.承認全体が完了したか
	 * @param 承認ルートインスタンス apprState
	 * @return 承認完了フラグ(true, false)
				　true：承認全体が完了
				　false：承認全体がまだ未完了
	 */
	public Boolean isApprovedAllHr(ApprovalStateHrImport apprState);
}
