package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhase;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhasePK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaApprovalPhaseRepository extends JpaRepository implements ApprovalPhaseRepository{
	
	private final String SELECT_FROM_APPHASE = "SELECT c FROM WwfmtApprovalPhase c"
			+ " WHERE c.wwfmtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfmtApprovalPhasePK.branchId = :branchId";
	private static final String DELETE_APHASE_BY_BRANCHID = "DELETE from WwfdtApprovalPhase c "
			+ " WHERE c.wwfdtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfdtApprovalPhasePK.branchId = :branchId";
	/**
	 * convert entity WwfmtApprovalPhase to domain ApprovalPhase
	 * @param entity
	 * @return
	 */
	private static ApprovalPhase toDomainApPhase(WwfmtApprovalPhase entity){
		val domain = ApprovalPhase.createSimpleFromJavaType(entity.wwfmtApprovalPhasePK.companyId,
				entity.wwfmtApprovalPhasePK.branchId,
				entity.wwfmtApprovalPhasePK.approvalPhaseId,
				entity.approvalForm,
				entity.browsingPhase,
				entity.displayOrder);
		return domain;
	}
	/**
	 * convert domain ApprovalPhase to entity WwfmtApprovalPhase
	 * @param domain
	 * @return
	 */
	private static WwfmtApprovalPhase toEntityAppPhase(ApprovalPhase domain){
		val entity = new WwfmtApprovalPhase();
		entity.wwfmtApprovalPhasePK = new WwfmtApprovalPhasePK(domain.getCompanyId(), domain.getBranchId(), domain.getApprovalPhaseId());
		entity.approvalForm = domain.getApprovalForm().value;
		entity.browsingPhase = domain.getBrowsingPhase();
		entity.displayOrder = domain.getOrderNumber();
		return entity;
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
	 * add All Approval Phase
	 * @param lstAppPhase
	 */
	@Override
	public void addAllApprovalPhase(List<ApprovalPhase> lstAppPhase) {
		List<WwfmtApprovalPhase> lstEntity = new ArrayList<>();
		for (ApprovalPhase approvalPhase : lstAppPhase) {
			WwfmtApprovalPhase approvalPhaseEntity = toEntityAppPhase(approvalPhase);
			lstEntity.add(approvalPhaseEntity);
		}
		this.commandProxy().insertAll(lstEntity);
	}
	/**
	 * delete All Approval Phase By Branch Id
	 * @param companyId
	 * @param branchId
	 */
	@Override
	public void deleteAllAppPhaseByBranchId(String companyId, String branchId) {
		this.getEntityManager().createQuery(DELETE_APHASE_BY_BRANCHID)
		.setParameter("companyId", companyId)
		.setParameter("branchId", branchId)
		.executeUpdate();
	}	
}
