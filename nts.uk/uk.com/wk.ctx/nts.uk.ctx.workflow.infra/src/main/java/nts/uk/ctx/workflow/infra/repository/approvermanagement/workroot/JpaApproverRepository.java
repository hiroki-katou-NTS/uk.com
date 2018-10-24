package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppover;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppoverPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaApproverRepository extends JpaRepository implements ApproverRepository{

	private static final String SELECT_FROM_APPROVER = "SELECT c FROM WwfmtAppover c"
			+ " WHERE c.wwfmtAppoverPK.companyId = :companyId"
			+ " AND c.wwfmtAppoverPK.approvalPhaseId = :approvalPhaseId"
			+ " ORDER BY c.displayOrder ASC";
	private static final String DELETE_APPROVER_BY_APHASEID = "DELETE from WwfmtAppover c "
			+ " WHERE c.wwfmtAppoverPK.companyId = :companyId"
			+ " AND c.wwfmtAppoverPK.approvalPhaseId = :approvalPhaseId";
	
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
	/**
	 * add All Approver
	 * @param lstApprover
	 */
	@Override
	public void addAllApprover(List<Approver> lstApprover) {
		List<WwfmtAppover> lstEntity = new ArrayList<>();
		for (Approver appover : lstApprover) {
			WwfmtAppover approverEntity = toEntityApprover(appover);
			lstEntity.add(approverEntity);
		}
		this.commandProxy().insertAll(lstEntity);
		this.getEntityManager().flush();
	}
	/**
	 * delete All Approver By Approval Phase Id
	 * @param companyId
	 * @param approvalPhaseId
	 */
	@Override
	public void deleteAllApproverByAppPhId(String companyId, String approvalPhaseId) {
		this.getEntityManager().createQuery(DELETE_APPROVER_BY_APHASEID)
		.setParameter("companyId", companyId)
		.setParameter("approvalPhaseId", approvalPhaseId)
		.executeUpdate();
		this.getEntityManager().flush();
	}
	/**
	 * updateEmployeeIdApprover
	 * @param companyId
	 * @param approvalPhaseId
	 */
	@Override
	public void updateEmployeeIdApprover(Approver updateApprover) {
		WwfmtAppover a = toEntityApprover(updateApprover);
		WwfmtAppover x = this.queryProxy().find(a.wwfmtAppoverPK, WwfmtAppover.class).get();
		x.setEmployeeId(a.employeeId);
		this.commandProxy().update(x);
		this.getEntityManager().flush();
	}
	/**
	 * convert entity WwfmtAppover to domain Approver
	 * @param entity
	 * @return
	 */
	private Approver toDomainApprover(WwfmtAppover entity){
		val domain = Approver.createSimpleFromJavaType(entity.wwfmtAppoverPK.companyId,
				entity.wwfmtAppoverPK.branchId,
				entity.wwfmtAppoverPK.approvalPhaseId,
				entity.wwfmtAppoverPK.approverId,
				entity.jobId,
				entity.employeeId,
				entity.displayOrder,
				entity.approvalAtr,
				entity.confirmPerson);
		return domain;
	}
	/**
	 * convert domain Approver to entity WwfmtAppover
	 * @param domain
	 * @return
	 */
	private WwfmtAppover toEntityApprover(Approver domain){
		val entity = new WwfmtAppover();
		entity.wwfmtAppoverPK = new WwfmtAppoverPK(domain.getCompanyId(), domain.getBranchId(), domain.getApprovalPhaseId(), domain.getApproverId());
		entity.jobId = domain.getJobTitleId();
		entity.employeeId = domain.getEmployeeId();
		entity.displayOrder = domain.getOrderNumber();
		entity.approvalAtr = domain.getApprovalAtr().value;
		entity.confirmPerson = domain.getConfirmPerson().value;
		return entity;
	}
}
