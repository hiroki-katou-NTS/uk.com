package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.*;

/**
 * 
 * @author hoatt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class JpaApprovalPhaseRepository extends JpaRepository implements ApprovalPhaseRepository{
	
	private static final String SELECT_FROM_APPHASE = "SELECT c FROM WwfmtApprovalPhase c"
			+ " WHERE c.wwfmtApprovalPhasePK.approvalId = :approvalId";
	private static final String SELECT_FROM_APPHASE_BY_LIST_ID = "SELECT c FROM WwfmtApprovalPhase c"
			+ " WHERE c.wwfmtApprovalPhasePK.approvalId IN :approvalIds";
	private static final String SELECT_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.wwfmtApprovalPhasePK.phaseOrder = :phaseOrder";
	private static final String DELETE_APHASE_BY_APPROVALID = "DELETE from WwfmtApprovalPhase c "
			+ " WHERE c.wwfmtApprovalPhasePK.approvalId = :approvalId";
	private static final String SELECT_FIRST_APPHASE = SELECT_FROM_APPHASE
			+ " AND c.wwfmtApprovalPhasePK.phaseOrder = 1";
	private static final String GET_FROM_APPROVAL_IDS = "SELECT m FROM WwfmtApprovalPhase m"
			+ " WHERE m.wwfmtApprovalPhasePK.approvalId IN :approvalIds";

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	@Override
	public List<ApprovalPhase> getAllApprovalPhasebyCode(String approvalId) {
		return this.queryProxy().query(SELECT_FROM_APPHASE, WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.getList(this::toDomainApPhase);
	}

	@Override
	public List<ApprovalPhase> getAllApprovalPhaseByListId(List<String> approvalIds) {
		if (CollectionUtil.isEmpty(approvalIds)) return new ArrayList<>();
		return this.queryProxy().query(SELECT_FROM_APPHASE_BY_LIST_ID, WwfmtApprovalPhase.class)
				.setParameter("approvalIds", approvalIds)
				.getList(this::toDomainApPhase);
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
		return this.queryProxy().query(SELECT_APPHASE, WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.setParameter("phaseOrder", phaseOrder)
				.getSingle(this::toDomainApPhase);
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
						"approver.APPROVER_G_CD, approver.SID, approver.APPROVER_ORDER, approver.CONFIRM_PERSON, approver.SPEC_WKP_ID " +
						"FROM WWFMT_APPROVAL_PHASE phase " +
						"RIGHT JOIN WWFMT_APPROVER approver " +
						"ON phase.APPROVAL_ID = approver.APPROVAL_ID " +
						"AND phase.PHASE_ORDER = approver.PHASE_ORDER " +
						"WHERE phase.APPROVAL_ID = 'approvalId' " ;
		query = query.replaceAll("approvalId", approvalId);
		try (PreparedStatement pstatement = con.prepareStatement(query)) {
			
			List<ApprovalPhase> listResult = toDomain(createFullJoinAppRootInstance(new NtsResultSet(pstatement.executeQuery())));
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
		WwfmtApprovalPhase entity = toEntityAppPhase(appPhase);
		Optional<WwfmtApprovalPhase> optPhase = this.queryProxy().query(SELECT_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("approvalId", appPhase.getApprovalId())
				.setParameter("phaseOrder", appPhase.getPhaseOrder())
				.getSingle();
		if (optPhase.isPresent()) {
			WwfmtApprovalPhase phase = optPhase.get();
			phase.approvalAtr = entity.approvalAtr;
			phase.approvalForm = entity.approvalForm;
			phase.browsingPhase = entity.browsingPhase;
			phase.wwfmtAppovers.clear();
			phase.wwfmtAppovers.addAll(entity.wwfmtAppovers);
			this.commandProxy().update(phase);
		} else {
			this.commandProxy().insert(entity);
		}
	}
	/**
	 * delete All Approval Phase By approvalId
	 * @param approvalId
	 */
	@Override
	public void deleteAllAppPhaseByApprovalId(String approvalId) {
		List<WwfmtApprovalPhase> entities = this.queryProxy().query(SELECT_FROM_APPHASE, WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.getList();
        this.commandProxy().removeAll(entities);
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
		this.commandProxy().remove(WwfmtApprovalPhase.class, appPhasePk);
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
				lstApprover
		);
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
		entity.wwfmtAppovers = new ArrayList<>();
		for (Approver appover : domain.getApprovers()) {
			WwfmtAppover tmp = new WwfmtAppover();
			tmp.wwfmtAppoverPK = new WwfmtAppoverPK(domain.getApprovalId(), domain.getPhaseOrder(), appover.getApproverOrder());
			tmp.jobGCD = appover.getJobGCD();
			tmp.employeeId = appover.getEmployeeId();
			tmp.confirmPerson = appover.getConfirmPerson().value;
			tmp.specWkpId = appover.getSpecWkpId();
			entity.wwfmtAppovers.add(tmp);
		}
		return entity;
	}
	
	/**
	 * Convert ApprovalPhase to entity WwfmtApprovalPhase and WwfmtAppover
	 */
	private WwfmtApprovalPhase toEntity(ApprovalPhase domain) {
		WwfmtApprovalPhase entity = new WwfmtApprovalPhase();
		entity.wwfmtApprovalPhasePK = new WwfmtApprovalPhasePK(domain.getApprovalId(), domain.getPhaseOrder());
		entity.approvalForm = domain.getApprovalForm().value;
		entity.browsingPhase = domain.getBrowsingPhase();
		entity.approvalAtr = domain.getApprovalAtr().value;
		
		List<WwfmtAppover> wwfmtAppovers = domain.getApprovers().stream()
				.map(x -> this.toEntityApprover(domain.getApprovalId(), domain.getPhaseOrder(), x))
				.collect(Collectors.toList());
		entity.wwfmtAppovers = wwfmtAppovers;
		return entity;
	}
	
	/**
	 * Convert Approver to WwfmtAppover
	 */
	private WwfmtAppover toEntityApprover(String approvalId, int phaseOrder, Approver domain) {
		val entity = new WwfmtAppover();
		entity.wwfmtAppoverPK = new WwfmtAppoverPK(approvalId, phaseOrder, domain.getApproverOrder());
		entity.jobGCD = domain.getJobGCD();
		entity.employeeId = domain.getEmployeeId();
		entity.confirmPerson = domain.getConfirmPerson().value;
		entity.specWkpId = domain.getSpecWkpId();
		return entity;
	}

	@Override
	public Optional<ApprovalPhase> getApprovalFirstPhase(String approvalId) {
		return this.queryProxy().query(SELECT_FIRST_APPHASE,WwfmtApprovalPhase.class)
				.setParameter("approvalId", approvalId)
				.getSingle(c->toDomainApPhase(c));
	}	
	
	@SneakyThrows
	private List<FullJoinWwfmtApprovalPhase> createFullJoinAppRootInstance(NtsResultSet nrs){
		
		List<FullJoinWwfmtApprovalPhase> listFullData =
				nrs.getList(rs -> new FullJoinWwfmtApprovalPhase(
						rs.getString("APPROVAL_ID"), 
						rs.getInt("PHASE_ORDER"),
						// rs.getString("BRANCH_ID"), 
						rs.getInt("APPROVAL_FORM"), 
						rs.getInt("BROWSING_PHASE"),
						rs.getInt("PHASE_ATR"),
						rs.getString("APPROVER_G_CD"), 
						rs.getString("SID"), 
						rs.getInt("APPROVER_ORDER"), 
						rs.getInt("CONFIRM_PERSON"),
						rs.getString("SPEC_WKP_ID")));


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
	
	@Override
	public List<ApprovalPhase> getFromApprovalIds(String cid, List<String> approvalIds) {
		if (approvalIds.isEmpty()) {
			return new ArrayList<ApprovalPhase>();
		}
		
		return this.queryProxy()
				.query(GET_FROM_APPROVAL_IDS, WwfmtApprovalPhase.class)
				.setParameter("approvalIds", approvalIds)
				.getList(this::toDomainApPhase);
	}
	
	@Override
	public void deleteByApprovalIds(List<String> approvalIds) {
		if (approvalIds.isEmpty()) {
			return;
		}
		
		List<WwfmtApprovalPhase> entities = this.queryProxy()
				.query(GET_FROM_APPROVAL_IDS, WwfmtApprovalPhase.class)
				.setParameter("approvalIds", approvalIds)
				.getList();
		
		this.commandProxy().removeAll(entities);
		
	}
	
	@Override
	public void insertAll(List<ApprovalPhase> approvalPhases) {
		List<WwfmtApprovalPhase> entities = approvalPhases.stream()
				.map(x -> toEntity(x))
				.collect(Collectors.toList());
		
		this.commandProxy().insertAll(entities);
	}
}
