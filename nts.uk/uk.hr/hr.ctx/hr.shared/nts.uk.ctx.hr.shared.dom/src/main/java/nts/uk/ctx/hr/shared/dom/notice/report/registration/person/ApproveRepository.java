package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

public interface ApproveRepository {
	/**
	 * hr [RQ631]申請書の承認者と状況を取得する
	 * @author lanlt
	 * @param インスタンスID rootStateID
	 * @return
	 */
	public ApprovalRootStateHrImport getApprovalRootStateHr(String rootStateID);
}
