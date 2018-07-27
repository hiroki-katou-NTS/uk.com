package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppover;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhase;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhasePK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaApprovalPhaseRepository extends JpaRepository implements ApprovalPhaseRepository{
	
	private static final String SELECT_FROM_APPHASE = "SELECT c FROM WwfmtApprovalPhase c"
			+ " WHERE c.wwfmtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfmtApprovalPhasePK.branchId = :branchId";
	private static final String SELECT_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.wwfmtApprovalPhasePK.approvalPhaseId = :approvalPhaseId";
	private static final String DELETE_APHASE_BY_BRANCHID = "DELETE from WwfmtApprovalPhase c "
			+ " WHERE c.wwfmtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfmtApprovalPhasePK.branchId = :branchId";
	private static final String SELECT_FIRST_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.displayOrder = 1";

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
	 * get Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @param approvalPhaseId
	 * @return
	 */
	@Override
	public Optional<ApprovalPhase> getApprovalPhase(String companyId, String branchId, String approvalPhaseId) {
		return this.queryProxy().query(SELECT_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.setParameter("approvalPhaseId", approvalPhaseId)
				.getSingle(c->toDomainApPhase(c));
	}
	/**
	 * get All Approval Phase by Code include approvers
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	@Override
	public List<ApprovalPhase> getAllIncludeApprovers(String companyId, String branchId) {
		List<WwfmtApprovalPhase> enPhases = this.queryProxy().query(SELECT_FROM_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.getList();
		List<ApprovalPhase> result = new ArrayList<>();
		enPhases.stream().forEach(x -> {
			ApprovalPhase dPhase = toDomainApPhase(x);
			dPhase.addApproverList(x.wwfmtAppovers.stream().map(a -> toDomainApprover(a)).collect(Collectors.toList()));
			result.add(dPhase);
		});
		
		return result;
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
	 * add Approval Phase
	 * @param appPhase
	 */
	@Override
	public void addApprovalPhase(ApprovalPhase appPhase) {
		this.commandProxy().insert(toEntityAppPhase(appPhase));
	}
	/**
	 * update Approval Phase
	 * @param appPhase
	 */
	@Override
	public void updateApprovalPhase(ApprovalPhase appPhase) {
		this.commandProxy().update(toEntityAppPhase(appPhase));
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
	/**
	 * delete All Approval Phase By Branch Id
	 * @param companyId
	 * @param branchId
	 * @param approvalPhaseId
	 */
	@Override
	public void deleteAppPhaseByAppPhId(String companyId, String branchId, String approvalPhaseId) {
		WwfmtApprovalPhasePK appPhasePk = new WwfmtApprovalPhasePK(companyId, branchId, approvalPhaseId);
		this.commandProxy().remove(appPhasePk);
	}
	/**
	 * convert entity WwfmtApprovalPhase to domain ApprovalPhase
	 * @param entity
	 * @return
	 */
	private ApprovalPhase toDomainApPhase(WwfmtApprovalPhase entity){
		List<Approver> lstApprover = new ArrayList<>();
		for(WwfmtAppover approver: entity.wwfmtAppovers) {
			lstApprover.add(toDomainApprover(approver));
		}
		
		val domain = ApprovalPhase.createSimpleFromJavaType(entity.wwfmtApprovalPhasePK.companyId,
				entity.wwfmtApprovalPhasePK.branchId,
				entity.wwfmtApprovalPhasePK.approvalPhaseId,
				entity.approvalForm,
				entity.browsingPhase,
				entity.displayOrder,
				lstApprover);
		return domain;
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
	 * convert domain ApprovalPhase to entity WwfmtApprovalPhase
	 * @param domain
	 * @return
	 */
	private WwfmtApprovalPhase toEntityAppPhase(ApprovalPhase domain){
		val entity = new WwfmtApprovalPhase();
		entity.wwfmtApprovalPhasePK = new WwfmtApprovalPhasePK(domain.getCompanyId(), domain.getBranchId(), domain.getApprovalPhaseId());
		entity.approvalForm = domain.getApprovalForm().value;
		entity.browsingPhase = domain.getBrowsingPhase();
		entity.displayOrder = domain.getOrderNumber();
		return entity;
	}

	@Override
	public Optional<ApprovalPhase> getApprovalFirstPhase(String companyId, String branchId) {
		return this.queryProxy().query(SELECT_FIRST_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
				.setParameter("branchId", branchId)
				.getSingle(c->toDomainApPhase(c));
	}	
}
