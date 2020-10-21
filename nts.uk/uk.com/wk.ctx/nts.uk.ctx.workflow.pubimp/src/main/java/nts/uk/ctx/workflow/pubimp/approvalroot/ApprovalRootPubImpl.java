package nts.uk.ctx.workflow.pubimp.approvalroot;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.ApprovalRootService;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.JobtitleToApproverService;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.AdjustedApprovalPhases;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.service.RemandService;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverInfoExport;

@Stateless
public class ApprovalRootPubImpl implements ApprovalRootPub {

	@Inject
	private ApprovalRootService approvalRootService;

	@Inject
	private JobtitleToApproverService jobtitleToApproverService;
	
	@Inject
	private RemandService remandService;

	@Override
	public List<ApprovalRootExport> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr,
			int appType, GeneralDate standardDate, int sysAtr) {
		List<ApprovalRootOutput> approvalData = this.approvalRootService.getApprovalRootOfSubjectRequest(cid, sid,
				employmentRootAtr, appType, standardDate, sysAtr);
		if (CollectionUtil.isEmpty(approvalData)) {
			return Collections.emptyList();
		}

		return approvalData.stream().map(x -> {
			ApprovalRootExport export = new ApprovalRootExport(x.getCompanyId(), x.getWorkplaceId(), x.getApprovalId(),
					x.getEmployeeId(), x.getHistoryId(), x.getStartDate(), x.getEndDate()
					// x.getBranchId(),
					// x.getAnyItemApplicationId()
					);

			export.addDataType(x.getApplicationType(), x.getConfirmationRootType(), x.getEmploymentRootAtr());
			export.addBeforeApprovers(this.convertApprovalPhaseListBefore(x.getBeforePhases()));
			export.addAfterApprovers(this.convertApprovalPhaseListAfter(x.getAfterPhases()));
			return export;
		}).collect(Collectors.toList());
	}

	/**
	 * Convert before adjustment data
	 * 
	 * @param beforePhases
	 * @return
	 */
	private List<ApprovalPhaseExport> convertApprovalPhaseListBefore(List<ApprovalPhase> beforePhases) {
		return beforePhases.stream().map(m -> {
			ApprovalPhaseExport phase = new ApprovalPhaseExport(m.getApprovalId(), m.getPhaseOrder(),
					m.getApprovalForm().value, m.getBrowsingPhase(), m.getApprovalAtr().value);
			phase.addApproverList(m.getApprovers().stream()
					.map(a -> new ApproverInfoExport(a.getJobGCD(), a.getEmployeeId(),
							a.getApproverOrder(),
							a.getConfirmPerson() == ConfirmPerson.CONFIRM ? true : false))
					.collect(Collectors.toList()));
			return phase;
		}).collect(Collectors.toList());
	}

	/**
	 * convert after adjustment data
	 * 
	 * @param afterPhases
	 * @return
	 */
	private List<ApprovalPhaseExport> convertApprovalPhaseListAfter(AdjustedApprovalPhases afterPhases) {
		return afterPhases.getPhases().stream().map(m -> {
			ApprovalPhaseExport phase = new ApprovalPhaseExport(m.getApprovalId(),
					m.getPhaseOrder(), m.getApprovalForm(), m.getBrowsingPhase(), m.getApprovalAtr());

			phase.addApproverList(m.getApprovers().stream().map(a -> {
				ApproverInfoExport export = new ApproverInfoExport(a.getJobGCD(), a.getSid(),
						a.getApproverOrder(), a.getIsConfirmPerson());
				export.addEmployeeName(a.getName());
				return export;
			}).collect(Collectors.toList()));
			return phase;
		}).collect(Collectors.toList());
	}

	@Override
	public List<ApproverInfoExport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		List<ApproverInfo> approvers = this.jobtitleToApproverService.convertToApprover(cid, sid, baseDate, jobTitleId);

		if (CollectionUtil.isEmpty(approvers)) {
			return Collections.emptyList();
		}
		
		return approvers.stream().map(x -> {
			ApproverInfoExport export = new ApproverInfoExport(x.getJobGCD(), x.getSid(),
					x.getApproverOrder(), x.getIsConfirmPerson());
			export.addEmployeeName(x.getName());
			return export;
		}).collect(Collectors.toList());

	}
	/**
	 * get phase current
	 */
	@Override
	public Integer getCurrentApprovePhase(String rootStateID, Integer rootType) {
		return remandService.getCurrentApprovePhase(rootStateID, rootType);
	}

}
