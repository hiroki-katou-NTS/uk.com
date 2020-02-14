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

	@Override
	public ApprRootStateHrImport getApprovalRootStateHr(
			String rootStateID) {

		ApprovalRootStateHrExport approvalStateHrExport = this.approvalStateHrPub.getApprovalRootStateHr(rootStateID);

		ApprovalStateHrImport approvalStateHr = approvalStateHrExport.getApprState();

		List<PhaseSttHrImport> lstPhaseStateResult = new ArrayList<>();

		if (!CollectionUtil.isEmpty(approvalStateHr.getLstPhaseState())) {

			lstPhaseStateResult = approvalStateHr.getLstPhaseState().stream().map(c -> {

				List<FrameHumanImport> lstApprovalFrameResult = c.getLstApprovalFrame().stream().map(f -> {

					List<ApproverInfoHumamImport> lstApproverInfo = f.getLstApproverInfo().stream().map(i -> {

								return new ApproverInfoHumamImport(i.getApproverID(), i.getApprovalAtr(),

										i.getAgentID(), i.getApprovalDate(), i.getApprovalReason());

							}).collect(Collectors.toList());

					return new FrameHumanImport(f.getFrameOrder(), f.getConfirmAtr(), f.getAppDate(), lstApproverInfo);

				}).collect(Collectors.toList());

				return new PhaseSttHrImport(c.getPhaseOrder(), c.getApprovalAtr(), c.getApprovalForm(),
						lstApprovalFrameResult);

			}).collect(Collectors.toList());

		}

		ApprRootStateHrImport approvalStateHrImport =

				new ApprRootStateHrImport(

						approvalStateHrExport.isErrorFlg(),

						new ApprStateHrImport(

								approvalStateHr.getRootStateID(),

								approvalStateHr.getAppDate(),

								approvalStateHr.getEmployeeID(),

								lstPhaseStateResult));

		return approvalStateHrImport;
	}

}
