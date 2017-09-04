package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranchRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtBranch;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaApprovalBranchRepository extends JpaRepository implements ApprovalBranchRepository{

	private final String SELECT_FROM_APBRANCH = "SELECT c FROM WwfmtBranch c"
			+ " WHERE c.wwfmtBranchPK.companyId = :companyId"
			+ " AND c.wwfmtBranchPK.branchId = :branchId";

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
	 * convert entity WwfmtBranch to domain ApprovalBranch
	 * @param entity
	 * @return
	 */
	private ApprovalBranch toDomainBranch(WwfmtBranch entity){
		val domain = new ApprovalBranch(entity.wwfmtBranchPK.companyId,
				entity.wwfmtBranchPK.branchId,
				entity.number);
		return domain;
	}
}
