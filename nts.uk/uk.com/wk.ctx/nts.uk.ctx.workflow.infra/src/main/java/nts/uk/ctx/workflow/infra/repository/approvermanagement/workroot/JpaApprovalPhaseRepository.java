package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.FullJoinWwfmtApprovalPhase;
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
			+ " WHERE c.wwfmtApprovalPhasePK.approvalId = :approvalId";
	private static final String SELECT_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.wwfmtApprovalPhasePK.phaseOrder = :phaseOrder";
	private static final String DELETE_APHASE_BY_APPROVALID = "DELETE from WwfmtApprovalPhase c "
			+ " WHERE c.wwfmtApprovalPhasePK.approvalId = :approvalId";
	private static final String SELECT_FIRST_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.wwfmtApprovalPhasePK.phaseOrder = 1";

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	@Override
	public List<ApprovalPhase> getAllApprovalPhasebyCode(String approvalId) {
		return this.queryProxy().query(SELECT_FROM_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.getList(c->toDomainApPhase(c));
	}
	/**
	 * get Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @param approvalPhaseId
	 * @return
	 */
	@Override
	public Optional<ApprovalPhase> getApprovalPhase(String approvalId, int phaseOrder) {
		return this.queryProxy().query(SELECT_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.setParameter("phaseOrder", phaseOrder)
				.getSingle(c->toDomainApPhase(c));
	}
	/**
	 * get All Approval Phase by Code include approvers
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	@Override
	@SneakyThrows
	public List<ApprovalPhase> getAllIncludeApprovers(String approvalId) {
		List<ApprovalPhase> result = new ArrayList<>();
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String query = "SELECT phase.APPROVAL_ID, phase.PHASE_ORDER, phase.APPROVAL_FORM, phase.BROWSING_PHASE, phase.APPROVAL_ATR as PHASE_ATR, " +
						"approver.APPROVER_G_CD, approver.SID, approver.APPROVER_ORDER, approver.APPROVAL_ATR, approver.CONFIRM_PERSON, approver.SPEC_WKP_ID " +
						"FROM WWFMT_APPROVAL_PHASE phase " +
						"LEFT JOIN WWFMT_APPROVER approver " +
						"ON phase.APPROVAL_ID = approver.APPROVAL_ID " +
						"AND phase.PHASE_ORDER = approver.PHASE_ORDER " +
						"WHERE phase.APPROVAL_ID = 'approvalId' " ;
		query = query.replaceAll("approvalId", approvalId);
		try (PreparedStatement pstatement = con.prepareStatement(query)) {
			ResultSet rs = pstatement.executeQuery();
			List<ApprovalPhase> listResult = toDomain(createFullJoinAppRootInstance(rs));
			if(CollectionUtil.isEmpty(listResult)){
				result = Collections.emptyList();
			} else {
				result = listResult;
			}
		} 

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
		this.getEntityManager().flush();
	}
	/**
	 * add Approval Phase
	 * @param appPhase
	 */
	@Override
	public void addApprovalPhase(ApprovalPhase appPhase) {
		this.commandProxy().insert(toEntityAppPhase(appPhase));
		this.getEntityManager().flush();
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
	 * delete All Approval Phase By approvalId
	 * @param companyId
	 * @param approvalId
	 */
	@Override
	public void deleteAllAppPhaseByApprovalId(String approvalId) {
		this.getEntityManager().createQuery(DELETE_APHASE_BY_APPROVALID)
		.setParameter("approvalId", approvalId)
		.executeUpdate();
		this.getEntityManager().flush();
	}	
	/**
	 * delete All Approval Phase By approvalId
	 * @param companyId
	 * @param approvalId
	 * @param approvalPhaseId
	 */
	@Override
	public void deleteAppPhaseByAppPhId(String approvalId, int phaseOrder) {
		WwfmtApprovalPhasePK appPhasePk = new WwfmtApprovalPhasePK(approvalId, phaseOrder);
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
			lstApprover.add(approver.toDomainApprover());
		}
		
		val domain = ApprovalPhase.createSimpleFromJavaType(
				entity.wwfmtApprovalPhasePK.approvalId,
				entity.wwfmtApprovalPhasePK.phaseOrder,
				// entity.branchId,
				entity.approvalForm,
				entity.browsingPhase,
				entity.approvalAtr,
				lstApprover);
		return domain;
	}
	
	/**
	 * convert domain ApprovalPhase to entity WwfmtApprovalPhase
	 * @param domain
	 * @return
	 */
	private WwfmtApprovalPhase toEntityAppPhase(ApprovalPhase domain){
		val entity = new WwfmtApprovalPhase();
		entity.wwfmtApprovalPhasePK = new WwfmtApprovalPhasePK(domain.getApprovalId(), domain.getPhaseOrder());
		// entity.branchId = domain.getBranchId();
		entity.approvalForm = domain.getApprovalForm().value;
		entity.browsingPhase = domain.getBrowsingPhase();
		entity.approvalAtr = domain.getApprovalAtr().value;
		return entity;
	}

	@Override
	public Optional<ApprovalPhase> getApprovalFirstPhase(String approvalId) {
		return this.queryProxy().query(SELECT_FIRST_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.getSingle(c->toDomainApPhase(c));
	}	
	
	@SneakyThrows
	private List<FullJoinWwfmtApprovalPhase> createFullJoinAppRootInstance(ResultSet rs){
		List<FullJoinWwfmtApprovalPhase> listFullData = new ArrayList<>();

		while (rs.next()) {
			listFullData.add(new FullJoinWwfmtApprovalPhase(
					rs.getString("APPROVAL_ID"), 
					rs.getInt("PHASE_ORDER"),
					// rs.getString("BRANCH_ID"), 
					rs.getInt("APPROVAL_FORM"), 
					rs.getInt("BROWSING_PHASE"),
					rs.getInt("PHASE_ATR"),
					rs.getString("APPROVER_G_CD"), 
					rs.getString("SID"), 
					rs.getInt("APPROVER_ORDER"), 
					rs.getInt("APPROVAL_ATR"), 
					rs.getInt("CONFIRM_PERSON"),
					rs.getString("SPEC_WKP_ID")));
		}

		return listFullData;
	}
	
	private List<ApprovalPhase> toDomain(List<FullJoinWwfmtApprovalPhase> listFullJoin){
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoinWwfmtApprovalPhase::getPhaseOrder))
						.entrySet().stream().map(x -> {
					FullJoinWwfmtApprovalPhase first = x.getValue().get(0);
					String approvalId = first.approvalId;
					int phaseOrder = first.phaseOrder;
					// String branchId = first.branchId;
					int phaseAtr = first.phaseAtr;
					ApprovalForm approvalForm = EnumAdaptor.valueOf(first.approvalForm, ApprovalForm.class);
					int browsingPhase = first.browsingPhase;
					List<Approver> approvers = x.getValue().stream().map(y -> 
						new Approver(
								y.approverOrder, 
								y.jobGCD, 
								y.employeeId, 
								EnumAdaptor.valueOf(y.confirmPerson, ConfirmPerson.class),
								y.specWkpID)).collect(Collectors.toList());
					return new ApprovalPhase(approvalId, 
							phaseOrder, 
							// branchId, 
							approvalForm,
							browsingPhase, EnumAdaptor.valueOf(phaseAtr, ApprovalAtr.class), approvers);
				}).collect(Collectors.toList());
	}
}
