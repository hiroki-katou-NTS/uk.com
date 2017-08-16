package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfdtApprovalPhase;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfdtBranch;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfdtComApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfdtPsApprovalRoot;
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
		
	private static CompanyApprovalRoot toDomainComApR(WwfdtComApprovalRoot entity){
		val domain = CompanyApprovalRoot.createSimpleFromJavaType(entity.wwfdtComApprovalRootPK.companyId,
				entity.wwfdtComApprovalRootPK.historyId,
				entity.wwfdtComApprovalRootPK.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	private static PersonApprovalRoot toDomainPsApR(WwfdtPsApprovalRoot entity){
		val domain = PersonApprovalRoot.createSimpleFromJavaType(entity.wwfdtPsApprovalRootPK.companyId,
				entity.wwfdtPsApprovalRootPK.employeeId,
				entity.wwfdtPsApprovalRootPK.historyId,
				entity.wwfdtPsApprovalRootPK.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	private static ApprovalBranch toDomainBranch(WwfdtBranch entity){
		val domain = new ApprovalBranch(entity.wwfdtBranchPK.companyId,
				entity.wwfdtBranchPK.branchId,
				entity.number);
		return domain;
	}
	private static ApprovalPhase toDomainApPhase(WwfdtApprovalPhase entity){
		val domain = ApprovalPhase.createSimpleFromJavaType(entity.wwfdtApprovalPhasePK.companyId,
				entity.wwfdtApprovalPhasePK.branchId,
				entity.wwfdtApprovalPhasePK.approvalPhaseId,
				entity.approvalForm,
				entity.browsingPhase,
				entity.orderNumber);
		return domain;
	}
	
	@Override
	public List<PersonApprovalRoot> getAllPsApprovalRoot(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_FROM_PSAPR, WwfdtPsApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getList(c->toDomainPsApR(c));
	}
	@Override
	public List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId) {
		return this.queryProxy().query(SELECT_FROM_COMAPR, WwfdtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.getList(c->toDomainComApR(c));
	}
	@Override
	public Optional<ApprovalBranch> getApprovalBranch(String companyId, String branchId, int number) {
		return this.queryProxy().query(SELECT_FROM_APBRANCH,WwfdtBranch.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.getSingle(c->toDomainBranch(c));
	}

	@Override
	public List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String branchId) {
		return this.queryProxy().query(SELECT_FROM_APPHASE,WwfdtApprovalPhase.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.getList(c->toDomainApPhase(c));
	}



}
