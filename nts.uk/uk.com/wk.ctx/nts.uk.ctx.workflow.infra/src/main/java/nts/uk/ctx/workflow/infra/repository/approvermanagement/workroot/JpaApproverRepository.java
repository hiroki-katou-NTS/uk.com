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
			+ " WHERE c.wwfmtAppoverPK.approvalId = :approvalId"
			+ " AND c.wwfmtAppoverPK.phaseOrder = :phaseOrder"
			+ " ORDER BY c.wwfmtAppoverPK.approverOrder ASC";
	private static final String DELETE_APPROVER_BY_PHASEORDER = "DELETE from WwfmtAppover c "
			+ " WHERE c.wwfmtAppoverPK.approvalId = :approvalId"
			+ " AND c.wwfmtAppoverPK.phaseOrder = :phaseOrder";
	
	/**
	 * get All Approver By Code
	 * @param companyId
	 * @param approvalPhaseId
	 * @return
	 */
	@Override
	public List<Approver> getAllApproverByCode(String approvalId, int phaseOrder) {
		return this.queryProxy().query(SELECT_FROM_APPROVER, WwfmtAppover.class)
				.setParameter("approvalId", approvalId)
				.setParameter("phaseOrder", phaseOrder)
				.getList(c -> c.toDomainApprover());
	}
	/**
	 * add All Approver
	 * @param lstApprover
	 */
	@Override
	public void addAllApprover(String approvalId, int phaseOrder, List<Approver> lstApprover) {
		List<WwfmtAppover> lstEntity = new ArrayList<>();
		for (Approver appover : lstApprover) {
			WwfmtAppover approverEntity = toEntityApprover(approvalId, phaseOrder, appover);
			lstEntity.add(approverEntity);
		}
		this.commandProxy().insertAll(lstEntity);
		this.getEntityManager().flush();
	}
	/**
	 * delete All Approver By phaseOrder
	 * @param companyId
	 * @param phaseOrder
	 */
	@Override
	public void deleteAllApproverByAppPhId(String approvalId, int phaseOrder) {
		this.getEntityManager().createQuery(DELETE_APPROVER_BY_PHASEORDER)
		.setParameter("approvalId", approvalId)
		.setParameter("phaseOrder", phaseOrder)
		.executeUpdate();
		this.getEntityManager().flush();
	}
	/**
	 * updateEmployeeIdApprover
	 * @param companyId
	 * @param approvalPhaseId
	 */
	@Override
	public void updateEmployeeIdApprover(String approvalId, int phaseOrder, Approver updateApprover) {
		WwfmtAppover a = toEntityApprover(approvalId, phaseOrder, updateApprover);
		WwfmtAppover x = this.queryProxy().find(a.wwfmtAppoverPK, WwfmtAppover.class).get();
		x.setEmployeeId(a.employeeId);
		this.commandProxy().update(x);
		this.getEntityManager().flush();
	}
	/**
	 * convert domain Approver to entity WwfmtAppover
	 * @param domain
	 * @return
	 */
	private WwfmtAppover toEntityApprover(String approvalId, int phaseOrder, Approver domain){
		val entity = new WwfmtAppover();
		entity.wwfmtAppoverPK = new WwfmtAppoverPK(approvalId, phaseOrder, domain.getApproverOrder());
		entity.jobGCD = domain.getJobGCD();
		entity.employeeId = domain.getEmployeeId();
		entity.confirmPerson = domain.getConfirmPerson().value;
		entity.specWkpId = domain.getSpecWkpId();
		return entity;
	}
}
