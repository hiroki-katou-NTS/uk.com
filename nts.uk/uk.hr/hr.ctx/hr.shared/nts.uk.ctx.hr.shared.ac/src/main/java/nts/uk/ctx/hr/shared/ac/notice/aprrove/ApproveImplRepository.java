package nts.uk.ctx.hr.shared.ac.notice.aprrove;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproveRepository;
import nts.uk.ctx.workflow.pub.hrapprovalstate.ApprovalStateHrPub;
import nts.uk.ctx.workflow.pub.hrapprovalstate.output.ApprovalRootStateHrExport;
@Stateless
public class ApproveImplRepository implements ApproveRepository{

	@Inject
	private ApprovalStateHrPub approvalStateHrPub;
	
	@Override
	public nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprovalRootStateHrImport getApprovalRootStateHr(String rootStateID) {
		
		ApprovalRootStateHrExport approvalStateHrExport = this.approvalStateHrPub.getApprovalRootStateHr(rootStateID);
		
		
		nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport approvalStateHr = approvalStateHrExport.getApprState();

		
		List<nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PhaseStateHrImport> lstPhaseStateResult = 
				
				approvalStateHr.getLstPhaseState().stream().map(c -> {

					List<nts.uk.ctx.hr.shared.dom.notice.report.registration.person.FrameHrImport> lstApprovalFrameResult =

							c.getLstApprovalFrame().stream().map(f -> {

								List<nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproverInfoHrImport> lstApproverInfo = 
										
										f.getLstApproverInfo().stream().map(i -> {
											
											return new nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproverInfoHrImport(
													
													i.getApproverID(), i.getApprovalAtr(), i.getAgentID(),
													
													i.getApprovalDate(), i.getApprovalReason());
											
										}).collect(Collectors.toList());

								return new nts.uk.ctx.hr.shared.dom.notice.report.registration.person.FrameHrImport(
								 		
										f.getFrameOrder(), f.getConfirmAtr(), f.getAppDate(), lstApproverInfo);

							}).collect(Collectors.toList());

					return new nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PhaseStateHrImport(
							
							c.getPhaseOrder(), c.getApprovalAtr(), c.getApprovalForm(),

							lstApprovalFrameResult);

				}).collect(Collectors.toList()); 
		
		nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprovalRootStateHrImport approvalStateHrImport = 
				
				new nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprovalRootStateHrImport(
						
				approvalStateHrExport.isErrorFlg(),
				
				new nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprovalStateHrImport(
						
						approvalStateHr.getRootStateID(),
						
						approvalStateHr.getAppDate(),
						
						approvalStateHr.getEmployeeID(),
						
						lstPhaseStateResult));
		
		return approvalStateHrImport;
	}

}
