package nts.uk.ctx.hr.shared.ac.notice.aprrove;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprRootStateHrImport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprStateHrImport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproveRepository;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproverInfoHumamImport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.FrameHumanImport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PhaseSttHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.ApprovalStateHrPub;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.output.ApprovalRootStateHrExport;

@Stateless
public class ApproveImplRepository implements ApproveRepository {

	@Inject
	private ApprovalStateHrPub approvalStateHrPub;
	

	/**
	 * [RQ631]申請書の承認者と状況を取得する
	 * @param インスタンスID rootStateID
	 * @return
	 */
	@Override
	public ApprRootStateHrImport getApprovalRootStateHr(
			String rootStateID) {

		ApprovalRootStateHrExport approvalStateHrExport = this.approvalStateHrPub.getApprovalRootStateHr(rootStateID);

		ApprovalStateHrImport approvalStateHr = approvalStateHrExport.getApprState();
		
		if(approvalStateHrExport.isErrorFlg()) {
			
			return new ApprRootStateHrImport();
		}

		List<PhaseSttHrImport> lstPhaseStateResult = new ArrayList<>();

		if (!CollectionUtil.isEmpty(approvalStateHr.getLstPhaseState())) {

			lstPhaseStateResult = approvalStateHr.getLstPhaseState().stream().map(c -> {

				List<FrameHumanImport> lstApprovalFrameResult = c.getLstApprovalFrame().stream().map(f -> {

					List<ApproverInfoHumamImport> lstApproverInfo = f.getLstApproverInfo().stream().map(i -> {

								return new ApproverInfoHumamImport(i.getApproverID(), i.getApprovalAtr(), i.getAgentID(), i.getApprovalDate(), i.getApprovalReason());

								}).collect(Collectors.toList());

					return new FrameHumanImport(f.getFrameOrder(), f.getConfirmAtr(), f.getAppDate(), lstApproverInfo);

				}).collect(Collectors.toList());

				return new PhaseSttHrImport(c.getPhaseOrder(), c.getApprovalAtr(), c.getApprovalForm(), lstApprovalFrameResult);

			}).collect(Collectors.toList());

		}

		ApprRootStateHrImport approvalStateHrImport =

				new ApprRootStateHrImport(

						approvalStateHrExport.isErrorFlg(),

						new ApprStateHrImport(

								approvalStateHr.getRootStateID(),

								approvalStateHr.getAppDate(),

								approvalStateHr.getEmployeeID(),
								
								approvalStateHr.isReflectFlag(),

								lstPhaseStateResult));

		return approvalStateHrImport;
	}

	/**
	 * [RQ632]申請書を承認する
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 承認フェーズ枠番
	 */
	@Override
	public Integer approveHr(String rootStateID, String employeeID, String comment) {
		
		return this.approvalStateHrPub.approveHr(rootStateID, employeeID, comment);
	
	}

	
	/**
	 * [RQ633]申請書を否認する
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 否認を実行したかフラグ(true, false)
					true：否認を実行した
					false：否認を実行しなかった
	 */
	@Override
	public Boolean denyHr(String rootStateID, String employeeID, String comment) {
		
		return this.approvalStateHrPub.denyHr(rootStateID, employeeID, comment);
	
	}

	/**
	 * hr [RQ635]申請書を解除する
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @return ・解除を実行したかフラグ(true, false)
					true：解除を実行した
					false：解除を実行しなかった
	 */	
	@Override
	public Boolean releaseHr(String rootStateID, String employeeID) {
		
		return this.approvalStateHrPub.releaseHr(rootStateID, employeeID);
		
	}

	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（承認者まで）
	 * @author laitv
	 * @param インスタンスID rootStateID
	 * @param 「人事承認フェーズインスタンス」．順序 phaseOrder
	 * trả đơn về cho người đã phê duyệt
	 */
	@Override
	public void remandForApproverHr(String rootStateID, Integer phaseOrder) {
		this.approvalStateHrPub.remandForApproverHr(rootStateID, phaseOrder);
	}

	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（申請本人まで）
	 * @author laitv
	 * @param インスタンスID rootStateID
	 * trả đơn về cho người làm đơn
	 */
	@Override
	public void remandForApplicantHr(String rootStateID) {
		this.approvalStateHrPub.remandForApplicantHr(rootStateID);
		
	}

}
