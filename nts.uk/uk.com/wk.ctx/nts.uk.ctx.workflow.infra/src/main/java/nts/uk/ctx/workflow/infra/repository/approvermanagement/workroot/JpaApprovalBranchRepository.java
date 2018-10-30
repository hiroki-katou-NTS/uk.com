package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranchRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtBranch;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtBranchPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaApprovalBranchRepository extends JpaRepository implements ApprovalBranchRepository{

	private static final String SELECT_FROM_APBRANCH = "SELECT c FROM WwfmtBranch c"
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
	 * add all branch
	 * @param lstBranch
	 */
	@Override
	public void addAllBranch(List<ApprovalBranch> lstBranch) {
		List<WwfmtBranch> lstEntity = new ArrayList<>();
		for (ApprovalBranch branch : lstBranch) {
			lstEntity.add(toEntity(branch));
		}
		this.commandProxy().insertAll(lstEntity);
		this.getEntityManager().flush();
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
	private WwfmtBranch toEntity(ApprovalBranch domain){
		val entity = new WwfmtBranch();
		entity.wwfmtBranchPK = new WwfmtBranchPK(domain.getCompanyId(), domain.getBranchId());
		entity.number = domain.getNumber();
		return entity;
	}
	/**
	 * delete Branch
	 * @param branchId
	 */
	@Override
	public void deleteBranch(String companyId, String branchId) {
		this.commandProxy().remove(WwfmtBranch.class, new WwfmtBranchPK(companyId,branchId));
		this.getEntityManager().flush();
	}
}
