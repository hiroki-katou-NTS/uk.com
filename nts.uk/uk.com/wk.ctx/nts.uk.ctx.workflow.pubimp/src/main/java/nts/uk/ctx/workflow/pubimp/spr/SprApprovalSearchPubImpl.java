package nts.uk.ctx.workflow.pubimp.spr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.JudgmentApprovalStatusService;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutputNew;
import nts.uk.ctx.workflow.pub.spr.SprApprovalSearchPub;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalComSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalPersonSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalPhaseSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalRootStateSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalWorkplaceSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApproverSprExport;
import nts.uk.ctx.workflow.pub.spr.export.JudgmentSprExportNew;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class SprApprovalSearchPubImpl implements SprApprovalSearchPub {
	
	@Inject
	private CompanyApprovalRootRepository companyApprovalRootRepository;
	
	@Inject
	private WorkplaceApprovalRootRepository workplaceApprovalRootRepository; 
	
	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository; 
	
	@Inject
	private ApprovalPhaseRepository approvalPhaseRepository;
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
	@Inject
	private AgentRepository agentRepository;

	@Override
	public List<ApprovalComSprExport> getApprovalRootCom(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		return companyApprovalRootRepository.getComAppRoot(companyID, date, employmentRootAtr, confirmRootAtr)
				.stream().map(x -> new ApprovalComSprExport(
						companyID, 
						x.getApprovalId(), 
						x.getApprRoot().getApplicationType().map(v -> v.value).orElse(null), 
						// x.getApprRoot().getBranchId(), 
						// x.getApprRoot().getAnyItemApplicationId(), 
						confirmRootAtr, 
						employmentRootAtr))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalPhaseSprExport> getAllIncludeApprovers(String companyId, String approvalId) {
		return approvalPhaseRepository.getAllIncludeApprovers(approvalId)
			.stream().map(x -> ApprovalPhaseSprExport.createFromJavaType(
					companyId, 
					approvalId, 
					x.getPhaseOrder(), 
					x.getApprovalForm().value, 
					x.getBrowsingPhase(),
					x.getApprovalAtr().value,
					x.getApprovers().stream().map(y -> ApproverSprExport.createFromJavaType(
							companyId, 
							approvalId, 
							x.getPhaseOrder(), 
							y.getApproverOrder(), 
							y.getJobGCD(), 
							y.getEmployeeId(), 
							y.getConfirmPerson().value)).collect(Collectors.toList())))
			.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalWorkplaceSprExport> getApprovalRootWp(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		return workplaceApprovalRootRepository.getWpAppRoot(companyID, date, employmentRootAtr, confirmRootAtr)
				.stream().map(x -> new ApprovalWorkplaceSprExport(
						x.getCompanyId(), 
						x.getApprovalId(), 
						x.getWorkplaceId(), 
						// x.getApprRoot().getBranchId(), 
						// x.getApprRoot().getAnyItemApplicationId(), 
						confirmRootAtr, 
						employmentRootAtr, 
						x.getApprRoot().getApplicationType().map(v -> v.value).orElse(null)))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalPersonSprExport> getApprovalRootPs(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		return personApprovalRootRepository.getPsAppRoot(companyID, date, employmentRootAtr, confirmRootAtr)
				.stream().map(x -> new ApprovalPersonSprExport(
						x.getCompanyId(), 
						x.getApprRoot().getHistoryItems().isEmpty() ? "" : x.getApprRoot().getHistoryItems().get(0).getApprovalId(),
						x.getEmployeeId(), 
						x.getApprRoot().getApplicationType().map(v -> v.value).orElse(null), 
						// x.getApprRoot().getBranchId(), 
						// x.getApprRoot().getAnyItemApplicationId(), 
						confirmRootAtr, 
						employmentRootAtr))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ApprovalRootStateSprExport> getAppByApproverDate(String companyID, String approverID, GeneralDate date) {
		List<ApprovalRootState> result =  new ArrayList<>();
		List<ApprovalRootState> approverLst = approvalRootStateRepository.getByApproverPeriod(companyID, approverID, new DatePeriod(date, date));
		result.addAll(approverLst);
		GeneralDate systemDate = GeneralDate.today();
		List<Agent> agentInfoOutputs = agentRepository.findAgentForSpr(companyID, approverID, systemDate, systemDate);
		agentInfoOutputs.forEach(agent -> {
			List<ApprovalRootState> approverAgentLst = approvalRootStateRepository.getByApproverPeriod(companyID, agent.getEmployeeId(), 
					new DatePeriod(date, date));
			result.addAll(approverAgentLst);
		});
		return result.stream().map(x -> new ApprovalRootStateSprExport(
					x.getRootStateID(), 
					x.getRootType().value, 
					x.getApprovalRecordDate(), 
					x.getEmployeeID()))
			.collect(Collectors.toList());
	}

	@Override
	public JudgmentSprExportNew judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID, Integer rootType) {
		ApproverPersonOutputNew approverPersonOutput = judgmentApprovalStatusService
				.judgmentTargetPersonCanApprove(companyID, rootStateID, employeeID, rootType);
		return new JudgmentSprExportNew(
				approverPersonOutput.getAuthorFlag(), 
				approverPersonOutput.getApprovalAtr().value, 
				approverPersonOutput.getExpirationAgentFlag(),
				approverPersonOutput.getApprovalPhaseAtr().value);
	}
	
}
