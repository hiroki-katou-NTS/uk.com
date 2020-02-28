package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface ApproveRepository {
	/**
	 * hr [RQ631]申請書の承認者と状況を取得する
	 * @param インスタンスID rootStateID
	 * @return
	 */
	public ApprRootStateHrImport getApprovalRootStateHr(String rootStateID);
	
	/**
	 * hr [RQ632]申請書を承認する
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 承認フェーズ枠番
	 */
	public Integer approveHr(String rootStateID, String employeeID, String comment);

	/**
	 * hr [RQ633]申請書を否認する
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 否認を実行したかフラグ(true, false)
					true：否認を実行した
					false：否認を実行しなかった
	 */
	public Boolean denyHr(String rootStateID, String employeeID, String comment);
	
	/**
	 * hr [RQ635]申請書を解除する
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @return ・解除を実行したかフラグ(true, false)
					true：解除を実行した
					false：解除を実行しなかった
	 */
	public Boolean releaseHr(String rootStateID, String employeeID);
	
	
	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（承認者まで）
	 * @author laitv
	 * @param インスタンスID rootStateID
	 * @param 「人事承認フェーズインスタンス」．順序 phaseOrder
	 * trả đơn về cho người đã phê duyệt
	 */
	public void remandForApproverHr(String rootStateID, Integer phaseOrder);
	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（申請本人まで）
	 * @author laitv
	 * @param インスタンスID rootStateID
	 * trả đơn về cho người làm đơn
	 */
	public void remandForApplicantHr(String rootStateID);
	

}



