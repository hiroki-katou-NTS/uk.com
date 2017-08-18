package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppover;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhase;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtBranch;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtPsApprovalRoot;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaWorkAppApprovalRootRepository extends JpaRepository implements WorkAppApprovalRootRepository{
	
	private final String SELECT_FROM_COMAPR = "SELECT c FROM WwfdtComApprovalRoot c"
			+ " WHERE c.wwfdtComApprovalRoot.companyId = :companyId";
	private final String SELECT_FROM_PSAPR = "SELECT c FROM WwfdtPsApprovalRoot c"
			+ " WHERE c.wwfdtPsApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfdtPsApprovalRootPK.employeeId = :employeeId";
	private final String SELECT_FROM_APBRANCH = "SELECT c FROM WwfdtBranch c"
			+ " WHERE c.wwfdtBranchPK.companyId = :companyId"
			+ " AND c.wwfdtBranchPK.branchId = :branchId";
	private final String SELECT_FROM_APPHASE = "SELECT c FROM WwfdtApprovalPhase c"
			+ " WHERE c.wwfdtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfdtApprovalPhasePK.branchId = :branchId";
	private final String SELECT_FROM_APPROVER = "SELECT c FROM WwfdtAppover c"
			+ " WHERE c.wwfdtAppoverPK.companyId = :companyId"
			+ " AND c.wwfdtAppoverPK.approvalPhaseId = :approvalPhaseId";
		
	private static CompanyApprovalRoot toDomainComApR(WwfmtComApprovalRoot entity){
		val domain = CompanyApprovalRoot.createSimpleFromJavaType(entity.wwfmtComApprovalRootPK.companyId,
				entity.wwfmtComApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	private static PersonApprovalRoot toDomainPsApR(WwfmtPsApprovalRoot entity){
		val domain = PersonApprovalRoot.createSimpleFromJavaType(entity.wwfmtPsApprovalRootPK.companyId,
				entity.wwfmtPsApprovalRootPK.employeeId,
				entity.wwfmtPsApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	private static ApprovalBranch toDomainBranch(WwfmtBranch entity){
		val domain = new ApprovalBranch(entity.wwfmtBranchPK.companyId,
				entity.wwfmtBranchPK.branchId,
				entity.number);
		return domain;
	}
	private static ApprovalPhase toDomainApPhase(WwfmtApprovalPhase entity){
		val domain = ApprovalPhase.createSimpleFromJavaType(entity.wwfmtApprovalPhasePK.companyId,
				entity.wwfmtApprovalPhasePK.branchId,
				entity.wwfmtApprovalPhasePK.approvalPhaseId,
				entity.approvalForm,
				entity.browsingPhase,
				entity.orderNumber);
		return domain;
	}
	private static Approver toDomainApprover(WwfmtAppover entity){
		val domain = Approver.createSimpleFromJavaType(entity.wwfmtAppoverPK.companyId,
				entity.wwfmtAppoverPK.approvalPhaseId,
				entity.wwfmtAppoverPK.approverId,
				entity.jobId,
				entity.employeeId,
				entity.orderNumber,
				entity.approvalAtr,
				entity.confirmPerson);
		return domain;
	}
	/**
	 * get all Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	@Override
	public List<PersonApprovalRoot> getAllPsApprovalRoot(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_FROM_PSAPR, WwfmtPsApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getList(c->toDomainPsApR(c));
	}
	/**
	 * get All Company Approval Root
	 * @param companyId
	 * @return
	 */
	@Override
	public List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId) {
		return this.queryProxy().query(SELECT_FROM_COMAPR, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.getList(c->toDomainComApR(c));
	}
	/**
	 * get Approval Branch
	 * @param companyId
	 * @param branchId
	 * @param number
	 * @return
	 */
	@Override
	public Optional<ApprovalBranch> getApprovalBranch(String companyId, String branchId, int number) {
		return this.queryProxy().query(SELECT_FROM_APBRANCH,WwfmtBranch.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.getSingle(c->toDomainBranch(c));
	}
	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	@Override
	public List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String branchId) {
		return this.queryProxy().query(SELECT_FROM_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.getList(c->toDomainApPhase(c));
	}
	/**
	 * get All Approver By Code
	 * @param companyId
	 * @param approvalPhaseId
	 * @return
	 */
	@Override
	public List<Approver> getAllApproverByCode(String companyId, String approvalPhaseId) {
		return this.queryProxy().query(SELECT_FROM_APPROVER, WwfmtAppover.class)
				.setParameter("companyId", companyId)
				.setParameter("approvalPhaseId", approvalPhaseId)
				.getList(c -> toDomainApprover(c));
	}



}
