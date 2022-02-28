package nts.uk.query.pubimp.workflow.workroot.approvalmanagement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalPhaseExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalRootExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalSettingInformationExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApproverExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.EmploymentAppHistoryItemExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.PersonApprovalRootExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.PersonApprovalRootPub;

@Stateless
public class PersonApprovalRootPubImpl implements PersonApprovalRootPub {

	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository;

	@Override
	public List<ApprovalSettingInformationExport> findApproverList(String cid, List<String> sids, GeneralDate baseDate,
			int systemAtr) {
		List<ApprovalSettingInformation> results = this.personApprovalRootRepository.getApprovalSettingByEmployees(cid,
				sids, baseDate, EnumAdaptor.valueOf(systemAtr, SystemAtr.class));
		return results.stream().map(result -> {
			PersonApprovalRoot personAppRootDomain = result.getPersonApprovalRoot();
			ApprovalRoot appRootDomain = personAppRootDomain.getApprRoot();
			
			List<ApprovalPhaseExport> approvalPhases = result.getApprovalPhases().stream()
					.map(data -> {
						List<ApproverExport> approver = data.getApprovers().stream()
								.map(x -> new ApproverExport(x.getJobGCD(), x.getEmployeeId(), x.getApproverOrder(), x.getConfirmPerson().value, x.getSpecWkpId()))
								.collect(Collectors.toList());
						return new ApprovalPhaseExport(approver, data.getApprovalId(), data.getPhaseOrder(), data.getApprovalForm().value, data.getBrowsingPhase(), data.getApprovalAtr().value);
					}).collect(Collectors.toList());
			List<EmploymentAppHistoryItemExport> historyItems = result.getPersonApprovalRoot().getApprRoot().getHistoryItems().stream()
					.map(data -> new EmploymentAppHistoryItemExport(data.getHistoryId(), data.getDatePeriod()))
					.collect(Collectors.toList());
			ApprovalRootExport apprRoot = new ApprovalRootExport(appRootDomain.getSysAtr().value, appRootDomain.getEmploymentRootAtr().value, historyItems, appRootDomain.getApplicationType().value, appRootDomain.getConfirmationRootType().value, appRootDomain.getNoticeId(), appRootDomain.getBusEventId());
			PersonApprovalRootExport personApprovalRoot = new PersonApprovalRootExport(cid, personAppRootDomain.getApprRoot().getHistoryItems().isEmpty()
					? ""
					: personAppRootDomain.getApprRoot().getHistoryItems().get(0).getApprovalId(), personAppRootDomain.getEmployeeId(), apprRoot, personAppRootDomain.getOperationMode().value);
			return new ApprovalSettingInformationExport(approvalPhases, personApprovalRoot);
		}).collect(Collectors.toList());
	}
}
