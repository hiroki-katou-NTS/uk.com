package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppover;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppoverPK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhase;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhasePK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtBranch;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRootPK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtPsApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtPsApprovalRootPK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtWpApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtWpApprovalRootPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaWorkAppApprovalRootRepository extends JpaRepository implements WorkAppApprovalRootRepository{

	private final String SELECT_FROM_COMAPR = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRoot.companyId = :companyId";
	private final String SELECT_FROM_PSAPR = "SELECT c FROM WwfmtPsApprovalRoot c"
			+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId";
	private final String SELECT_FROM_WPAPR = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId";
	private final String SELECT_FROM_APBRANCH = "SELECT c FROM WwfmtBranch c"
			+ " WHERE c.wwfmtBranchPK.companyId = :companyId"
			+ " AND c.wwfmtBranchPK.branchId = :branchId";
	private final String SELECT_FROM_APPHASE = "SELECT c FROM WwfmtApprovalPhase c"
			+ " WHERE c.wwfmtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfmtApprovalPhasePK.branchId = :branchId";
	private final String SELECT_FROM_APPROVER = "SELECT c FROM WwfmtAppover c"
			+ " WHERE c.wwfmtAppoverPK.companyId = :companyId"
			+ " AND c.wwfmtAppoverPK.approvalPhaseId = :approvalPhaseId";
	private static final String DELETE_APHASE_BY_BRANCHID = "DELETE from WwfdtApprovalPhase c "
			+ " WHERE c.wwfdtApprovalPhasePK.companyId = :companyId"
			+ " AND c.wwfdtApprovalPhasePK.branchId = :branchId";
	private static final String DELETE_APPROVER_BY_APHASEID = "DELETE from WwfmtAppover c "
			+ " WHERE c.wwfmtAppoverPK.companyId = :companyId"
			+ " AND c.wwfmtAppoverPK.approvalPhaseId = :approvalPhaseId";
	private final String SELECT_COMAPR_BY_EDATE = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRoot.companyId = :companyId"
			+ " AND c.endDate = :endDate";
	private final String SELECT_PSAPR_BY_EDATE = "SELECT c FROM WwfmtPsApprovalRoot c"
			+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
			+ " AND c.endDate = :endDate";
	private final String SELECT_WPAPR_BY_EDATE = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId"
			+ " AND c.endDate = :endDate";
		
	/**
	 * convert entity WwfmtComApprovalRoot to domain CompanyApprovalRoot
	 * @param entity
	 * @return
	 */
	private static CompanyApprovalRoot toDomainComApR(WwfmtComApprovalRoot entity){
		val domain = CompanyApprovalRoot.createSimpleFromJavaType(entity.wwfmtComApprovalRootPK.companyId,
				entity.wwfmtComApprovalRootPK.approvalId,
				entity.wwfmtComApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	/**
	 * convert entity WwfmtPsApprovalRoot to domain PersonApprovalRoot
	 * @param entity
	 * @return
	 */
	private static PersonApprovalRoot toDomainPsApR(WwfmtPsApprovalRoot entity){
		val domain = PersonApprovalRoot.createSimpleFromJavaType(entity.wwfmtPsApprovalRootPK.companyId,
				entity.wwfmtPsApprovalRootPK.approvalId,
				entity.wwfmtPsApprovalRootPK.employeeId,
				entity.wwfmtPsApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	/**
	 * convert entity WwfmtWpApprovalRoot to domain WorkplaceApprovalRoot
	 * @param entity
	 * @return
	 */
	private static WorkplaceApprovalRoot toDomainWpApR(WwfmtWpApprovalRoot entity){
		val domain = WorkplaceApprovalRoot.createSimpleFromJavaType(entity.wwfmtWpApprovalRootPK.companyId,
				entity.wwfmtWpApprovalRootPK.approvalId,
				entity.wwfmtWpApprovalRootPK.workplaceId,
				entity.wwfmtWpApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate.toString(),
				entity.endDate.toString(),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
		return domain;
	}
	/**
	 * convert entity WwfmtBranch to domain ApprovalBranch
	 * @param entity
	 * @return
	 */
	private static ApprovalBranch toDomainBranch(WwfmtBranch entity){
		val domain = new ApprovalBranch(entity.wwfmtBranchPK.companyId,
				entity.wwfmtBranchPK.branchId,
				entity.number);
		return domain;
	}
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
	 * convert entity WwfmtAppover to domain Approver
	 * @param entity
	 * @return
	 */
	private static Approver toDomainApprover(WwfmtAppover entity){
		val domain = Approver.createSimpleFromJavaType(entity.wwfmtAppoverPK.companyId,
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
	 * convert domain CompanyApprovalRoot to entity WwfmtComApprovalRoot
	 * @param domain
	 * @return
	 */
	private static WwfmtComApprovalRoot toEntityComApR(CompanyApprovalRoot domain){
		val entity = new WwfmtComApprovalRoot();
		entity.wwfmtComApprovalRootPK = new WwfmtComApprovalRootPK(domain.getCompanyId(), domain.getApprovalId(), domain.getHistoryId());
		entity.startDate = domain.getPeriod().getStartDate();
		entity.endDate = domain.getPeriod().getEndDate();
		entity.applicationType = domain.getApplicationType().value;
		entity.branchId = domain.getBranchId();
		entity.anyItemAppId = domain.getAnyItemApplicationId();
		entity.confirmationRootType = domain.getConfirmationRootType().value;
		entity.employmentRootAtr = domain.getEmploymentRootAtr().value;
		return entity;
	}
	/**
	 * convert domain PersonApprovalRoot to entity WwfmtPsApprovalRoot
	 * @param domain
	 * @return
	 */
	private static WwfmtPsApprovalRoot toEntityPsApR(PersonApprovalRoot domain){
		val entity = new WwfmtPsApprovalRoot();
		entity.wwfmtPsApprovalRootPK = new WwfmtPsApprovalRootPK(domain.getCompanyId(), domain.getApprovalId(), domain.getEmployeeId(), domain.getHistoryId());
		entity.startDate = domain.getPeriod().getStartDate();
		entity.endDate = domain.getPeriod().getEndDate();
		entity.applicationType = domain.getApplicationType().value;
		entity.branchId = domain.getBranchId();
		entity.anyItemAppId = domain.getAnyItemApplicationId();
		entity.confirmationRootType = domain.getConfirmationRootType().value;
		entity.employmentRootAtr = domain.getEmploymentRootAtr().value;
		return entity;
	}
	/**
	 * convert domain WorkplaceApprovalRoot to entity WwfmtWpApprovalRoot
	 * @param domain
	 * @return
	 */
	private static WwfmtWpApprovalRoot toEntityWpApR(WorkplaceApprovalRoot domain){
		val entity = new WwfmtWpApprovalRoot();
		entity.wwfmtWpApprovalRootPK = new WwfmtWpApprovalRootPK(domain.getCompanyId(), domain.getApprovalId(), domain.getWorkplaceId(), domain.getHistoryId());
		entity.startDate = domain.getPeriod().getStartDate();
		entity.endDate = domain.getPeriod().getEndDate();
		entity.applicationType = domain.getApplicationType().value;
		entity.branchId = domain.getBranchId();
		entity.anyItemAppId = domain.getAnyItemApplicationId();
		entity.confirmationRootType = domain.getConfirmationRootType().value;
		entity.employmentRootAtr = domain.getEmploymentRootAtr().value;
		return entity;
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
	 * convert domain Approver to entity WwfmtAppover
	 * @param domain
	 * @return
	 */
	private static WwfmtAppover toEntityApprover(Approver domain){
		val entity = new WwfmtAppover();
		entity.wwfmtAppoverPK = new WwfmtAppoverPK(domain.getCompanyId(), domain.getApprovalPhaseId(), domain.getApproverId());
		entity.jobId = domain.getJobTitleId();
		entity.employeeId = domain.getEmployeeId();
		entity.displayOrder = domain.getOrderNumber();
		entity.approvalAtr = domain.getApprovalAtr().value;
		entity.confirmPerson = domain.getConfirmPerson();
		return entity;
	}
	/**
	 * get all Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	@Override
	public List<PersonApprovalRoot> getAllPsApprovalRoot(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_FROM_PSAPR, WwfmtPsApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getList(c->toDomainPsApR(c));
	}
	/**
	 * get All Company Approval Root
	 * @param companyId
	 * @return
	 */
	@Override
	public List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId) {
		return this.queryProxy().query(SELECT_FROM_COMAPR, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.getList(c->toDomainComApR(c));
	}
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
	 * delete Company Approval Root
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 */
	@Override
	public void deleteComApprovalRoot(String companyId, String approvalId, String historyId) {
		WwfmtComApprovalRootPK comPK = new WwfmtComApprovalRootPK(companyId, approvalId, historyId);
		this.commandProxy().remove(WwfmtComApprovalRoot.class,comPK);
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
	}
	/**
	 * delete Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @param historyId
	 */
	@Override
	public void deletePsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId) {
		WwfmtPsApprovalRootPK comPK = new WwfmtPsApprovalRootPK(companyId, approvalId, employeeId, historyId);
		this.commandProxy().remove(WwfmtPsApprovalRoot.class,comPK);
	}
	/**
	 * get All Workplace Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @return
	 */
	@Override
	public List<WorkplaceApprovalRoot> getAllWpApprovalRoot(String companyId, String workplaceId) {
		return this.queryProxy().query(SELECT_FROM_WPAPR, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.getList(c->toDomainWpApR(c));
	}
	/**
	 * delete Person Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @param historyId
	 */
	@Override
	public void deleteWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId) {
		WwfmtWpApprovalRootPK comPK = new WwfmtWpApprovalRootPK(companyId, approvalId, workplaceId, historyId);
		this.commandProxy().remove(WwfmtWpApprovalRoot.class,comPK);
	}
	/**
	 * add Company Approval Root
	 * @param comAppRoot
	 */
	@Override
	public void addComApprovalRoot(CompanyApprovalRoot comAppRoot) {
		this.commandProxy().insert(toEntityComApR(comAppRoot));
	}
	/**
	 * add Person Approval Root
	 * @param psAppRoot
	 */
	@Override
	public void addPsApprovalRoot(PersonApprovalRoot psAppRoot) {
		this.commandProxy().insert(toEntityPsApR(psAppRoot));
	}
	/**
	 * add Workplace Approval Root
	 * @param wpAppRoot
	 */
	@Override
	public void addWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot) {
		this.commandProxy().insert(toEntityWpApR(wpAppRoot));
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
	}
	/**
	 * update Company Approval Root
	 * @param comAppRoot
	 */
	@Override
	public void updateComApprovalRoot(CompanyApprovalRoot comAppRoot) {
		WwfmtComApprovalRoot a = toEntityComApR(comAppRoot);
		WwfmtComApprovalRoot x = this.queryProxy().find(a.wwfmtComApprovalRootPK, WwfmtComApprovalRoot.class).get();
		x.setStartDate(a.startDate);
		x.setEndDate(a.endDate);
		x.setApplicationType(a.applicationType);
		x.setBranchId(a.branchId);
		x.setAnyItemAppId(a.anyItemAppId);
		x.setConfirmationRootType(a.confirmationRootType);
		x.setEmploymentRootAtr(a.employmentRootAtr);
		this.commandProxy().update(x);
	}
	/**
	 * update Person Approval Root
	 * @param psAppRoot
	 */
	@Override
	public void updatePsApprovalRoot(PersonApprovalRoot psAppRoot) {
		WwfmtPsApprovalRoot a = toEntityPsApR(psAppRoot);
		WwfmtPsApprovalRoot x = this.queryProxy().find(a.wwfmtPsApprovalRootPK, WwfmtPsApprovalRoot.class).get();
		x.setStartDate(a.startDate);
		x.setEndDate(a.endDate);
		x.setApplicationType(a.applicationType);
		x.setBranchId(a.branchId);
		x.setAnyItemAppId(a.anyItemAppId);
		x.setConfirmationRootType(a.confirmationRootType);
		x.setEmploymentRootAtr(a.employmentRootAtr);
		this.commandProxy().update(x);
	}
	/**
	 * update Workplace Approval Root
	 * @param wpAppRoot
	 */
	@Override
	public void updateWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot) {
		WwfmtWpApprovalRoot a = toEntityWpApR(wpAppRoot);
		WwfmtWpApprovalRoot x = this.queryProxy().find(a.wwfmtWpApprovalRootPK, WwfmtWpApprovalRoot.class).get();
		x.setStartDate(a.startDate);
		x.setEndDate(a.endDate);
		x.setApplicationType(a.applicationType);
		x.setBranchId(a.branchId);
		x.setAnyItemAppId(a.anyItemAppId);
		x.setConfirmationRootType(a.confirmationRootType);
		x.setEmploymentRootAtr(a.employmentRootAtr);
		this.commandProxy().update(x);
	}
	/**
	 * get Company Approval Root By End date
	 * @param companyId
	 * @param endDate
	 * @return
	 */
	@Override
	public List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, String endDate) {
		return this.queryProxy().query(SELECT_COMAPR_BY_EDATE, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("endDate", endDate)
				.getList(c->toDomainComApR(c));
	}
	/**
	 * get Workplace Approval Root By End date
	 * @param companyId
	 * @param workplaceId
	 * @param endDate
	 * @return
	 */
	@Override
	public List<WorkplaceApprovalRoot> getWpApprovalRootByEdate(String companyId, String workplaceId, String endDate) {
		return this.queryProxy().query(SELECT_WPAPR_BY_EDATE, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("endDate", endDate)
				.getList(c->toDomainWpApR(c));
	}
	/**
	 * get Person Approval Root By End date
	 * @param companyId
	 * @param employeeId
	 * @param endDate
	 * @return
	 */
	@Override
	public List<PersonApprovalRoot> getPsApprovalRootByEdate(String companyId, String employeeId, String endDate) {
		return this.queryProxy().query(SELECT_PSAPR_BY_EDATE, WwfmtPsApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("endDate", endDate)
				.getList(c->toDomainPsApR(c));
	}
}
