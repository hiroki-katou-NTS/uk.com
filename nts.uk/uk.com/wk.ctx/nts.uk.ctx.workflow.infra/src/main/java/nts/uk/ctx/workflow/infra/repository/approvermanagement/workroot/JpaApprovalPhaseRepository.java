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
			+ " WHERE c.wwfmtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfmtApprovalPhasePK.approvalId = :approvalId";
	private static final String SELECT_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.wwfmtApprovalPhasePK.phaseOrder = :phaseOrder";
	private static final String DELETE_APHASE_BY_APPROVALID = "DELETE from WwfmtApprovalPhase c "
			+ " WHERE c.wwfmtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfmtApprovalPhasePK.approvalId = :approvalId";
	private static final String SELECT_FIRST_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.phaseOrder = 1";

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	@Override
	public List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String approvalId) {
		return this.queryProxy().query(SELECT_FROM_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
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
	public Optional<ApprovalPhase> getApprovalPhase(String companyId, String approvalId, int phaseOrder) {
		return this.queryProxy().query(SELECT_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
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
	public List<ApprovalPhase> getAllIncludeApprovers(String companyId, String approvalId) {
		List<ApprovalPhase> result = new ArrayList<>();
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String query = "SELECT phase.CID, phase.APPROVAL_ID, phase.PHASE_ORDER, phase.APPROVAL_FORM, phase.BROWSING_PHASE, phase.PHASE_ORDER, " +
						"approver.JOB_ID, approver.SID, approver.APPROVERORDER, approver.APPROVAL_ATR, approver.CONFIRM_PERSON " +
						"FROM WWFMT_APPROVAL_PHASE phase " +
						"LEFT JOIN WWFMT_APPROVER approver " +
						"ON phase.CID = approver.CID " +
						"AND phase.APPROVAL_ID = approver.APPROVAL_ID " +
						"AND phase.PHASE_ORDER = approver.PHASE_ORDER " +
						"WHERE phase.APPROVAL_ID = 'approvalId' "+
						"AND phase.CID = 'companyID' " ;
		query = query.replaceAll("companyID", companyId);
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
	public void deleteAllAppPhaseByApprovalId(String companyId, String approvalId) {
		this.getEntityManager().createQuery(DELETE_APHASE_BY_APPROVALID)
		.setParameter("companyId", companyId)
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
	public void deleteAppPhaseByAppPhId(String companyId, String approvalId, int phaseOrder) {
		WwfmtApprovalPhasePK appPhasePk = new WwfmtApprovalPhasePK(companyId, approvalId, phaseOrder);
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
				entity.wwfmtApprovalPhasePK.approvalId,
				entity.wwfmtApprovalPhasePK.phaseOrder,
				entity.approvalForm,
				entity.browsingPhase,
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
				entity.wwfmtAppoverPK.approvalId,
				entity.wwfmtAppoverPK.phaseOrder,
				entity.wwfmtAppoverPK.approverOrder,
				entity.jobId,
				entity.employeeId,
				entity.approvalAtr,
				entity.confirmPerson,
				entity.specWkpId);
		return domain;
	}
	
	/**
	 * convert domain ApprovalPhase to entity WwfmtApprovalPhase
	 * @param domain
	 * @return
	 */
	private WwfmtApprovalPhase toEntityAppPhase(ApprovalPhase domain){
		val entity = new WwfmtApprovalPhase();
		entity.wwfmtApprovalPhasePK = new WwfmtApprovalPhasePK(domain.getCompanyId(), domain.getApprovalId(), domain.getPhaseOrder());
		entity.approvalForm = domain.getApprovalForm().value;
		entity.browsingPhase = domain.getBrowsingPhase();
		return entity;
	}

	@Override
	public Optional<ApprovalPhase> getApprovalFirstPhase(String companyId, String approvalId) {
		return this.queryProxy().query(SELECT_FIRST_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("companyId", companyId)
				.setParameter("approvalId", approvalId)
				.getSingle(c->toDomainApPhase(c));
	}	
	
	@SneakyThrows
	private List<FullJoinWwfmtApprovalPhase> createFullJoinAppRootInstance(ResultSet rs){
		List<FullJoinWwfmtApprovalPhase> listFullData = new ArrayList<>();

		while (rs.next()) {
			listFullData.add(new FullJoinWwfmtApprovalPhase(
					rs.getString("CID"), 
					rs.getString("APPROVAL_ID"), 
					rs.getInt("PHASE_ORDER"), 
					rs.getInt("APPROVAL_FORM"), 
					rs.getInt("BROWSING_PHASE"), 
					rs.getString("JOB_ID"), 
					rs.getString("SID"), 
					rs.getInt(10), 
					rs.getInt("APPROVAL_ATR"), 
					rs.getInt("CONFIRM_PERSON")));
		}

		return listFullData;
	}
	
	private List<ApprovalPhase> toDomain(List<FullJoinWwfmtApprovalPhase> listFullJoin){
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoinWwfmtApprovalPhase::getPhaseOrder))
						.entrySet().stream().map(x -> {
					FullJoinWwfmtApprovalPhase first = x.getValue().get(0);
					String companyId = first.companyId;
					String approvalId = first.approvalId;
					int phaseOrder = first.phaseOrder;
					ApprovalForm approvalForm = EnumAdaptor.valueOf(first.approvalForm, ApprovalForm.class);
					int browsingPhase = first.browsingPhase;
					List<Approver> approvers = x.getValue().stream().map(y -> 
						new Approver(
								companyId, 
								approvalId, 
								phaseOrder, 
								y.approverOrder, 
								y.jobId, 
								y.employeeId, 
								EnumAdaptor.valueOf(y.approvalAtr, ApprovalAtr.class), 
								EnumAdaptor.valueOf(y.confirmPerson, ConfirmPerson.class),
								null)).collect(Collectors.toList());
					return new ApprovalPhase(companyId, approvalId, phaseOrder, approvalForm, browsingPhase, approvers);
				}).collect(Collectors.toList());
	}
}
