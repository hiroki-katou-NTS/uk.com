package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRootPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaCompanyApprovalRootRepository extends JpaRepository implements CompanyApprovalRootRepository{

	private final String SELECT_FROM_COMAPR = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId";
	private final String SELECT_COMAPR_BY_EDATE = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.endDate = :endDate";
	/**
	 * convert entity WwfmtComApprovalRoot to domain CompanyApprovalRoot
	 * @param entity
	 * @return
	 */
	private static CompanyApprovalRoot toDomainComApR(WwfmtComApprovalRoot entity){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		val domain = CompanyApprovalRoot.createSimpleFromJavaType(entity.wwfmtComApprovalRootPK.companyId,
				entity.wwfmtComApprovalRootPK.approvalId,
				entity.wwfmtComApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate.localDate().format(formatter),
				entity.endDate.localDate().format(formatter),
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr);
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
	 * get ComApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 * @return
	 */
	@Override
	public Optional<CompanyApprovalRoot> getComApprovalRoot(String companyId, String approvalId, String historyId) {
		WwfmtComApprovalRootPK pk = new WwfmtComApprovalRootPK(companyId, approvalId, historyId);
		return this.queryProxy().find(pk, WwfmtComApprovalRoot.class).map(c->toDomainComApR(c));
	}
	/**
	 * get Company Approval Root By End date
	 * @param companyId
	 * @param endDate
	 * @return
	 */
	@Override
	public List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, Integer applicationType) {
		return this.queryProxy().query(SELECT_COMAPR_BY_EDATE, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("endDate", endDate)
				.getList(c->toDomainComApR(c));
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
}
