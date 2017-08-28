package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtWpApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtWpApprovalRootPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaWorkplaceApprovalRootRepository extends JpaRepository implements WorkplaceApprovalRootRepository{

	private final String FIND_WP_APR_ALL = "SELECT c FROM WwfmtWpApprovalRoot c";
	 private final String SELECT_FROM_WPAPR = FIND_WP_APR_ALL
	   + " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
	   + " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId";
	 private final String SELECT_WPAPR_BY_EDATE = SELECT_FROM_WPAPR
	   + " AND c.endDate = :endDate"
	   + " AND c.applicationType = :applicationType";
	 private final String SELECT_WPAPR_BY_EDATE_APP_NULL = SELECT_FROM_WPAPR
			   + " AND c.endDate = :endDate"
			   + " AND c.applicationType IS NULL";
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
	 * get WpApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param workplaceId
	 * @param historyId
	 * @return
	 */
	@Override
	public Optional<WorkplaceApprovalRoot> getWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId) {
		WwfmtWpApprovalRootPK pk = new WwfmtWpApprovalRootPK(companyId, approvalId, workplaceId, historyId);
		return this.queryProxy().find(pk, WwfmtWpApprovalRoot.class).map(c->toDomainWpApR(c));
	}
	/**
	 * get Workplace Approval Root By End date
	 * @param companyId
	 * @param workplaceId
	 * @param endDate
	 * @return
	 */
	@Override
	public List<WorkplaceApprovalRoot> getWpApprovalRootByEdate(String companyId, String workplaceId, GeneralDate endDate, Integer applicationType) {
		//common
		if(applicationType == null){
			return this.queryProxy().query(SELECT_WPAPR_BY_EDATE_APP_NULL, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("endDate", endDate)
					.getList(c->toDomainWpApR(c));
		}
		//15 app type
		return this.queryProxy().query(SELECT_WPAPR_BY_EDATE, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("endDate", endDate)
				.setParameter("applicationType", applicationType)
				.getList(c->toDomainWpApR(c));
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
	 * convert entity WwfmtWpApprovalRoot to domain WorkplaceApprovalRoot
	 * @param entity
	 * @return
	 */
	private WorkplaceApprovalRoot toDomainWpApR(WwfmtWpApprovalRoot entity){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		val domain = WorkplaceApprovalRoot.createSimpleFromJavaType(entity.wwfmtWpApprovalRootPK.companyId,
				entity.wwfmtWpApprovalRootPK.approvalId,
				entity.wwfmtWpApprovalRootPK.workplaceId,
				entity.wwfmtWpApprovalRootPK.historyId,
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
	 * convert domain WorkplaceApprovalRoot to entity WwfmtWpApprovalRoot
	 * @param domain
	 * @return
	 */
	private WwfmtWpApprovalRoot toEntityWpApR(WorkplaceApprovalRoot domain){
		val entity = new WwfmtWpApprovalRoot();
		entity.wwfmtWpApprovalRootPK = new WwfmtWpApprovalRootPK(domain.getCompanyId(), domain.getApprovalId(), domain.getWorkplaceId(), domain.getHistoryId());
		entity.startDate = domain.getPeriod().getStartDate();
		entity.endDate = domain.getPeriod().getEndDate();
		entity.applicationType = (domain.getApplicationType() == null ? null : domain.getApplicationType().value);
		entity.branchId = domain.getBranchId();
		entity.anyItemAppId = domain.getAnyItemApplicationId();
		entity.confirmationRootType = (domain.getConfirmationRootType() == null ? null : domain.getConfirmationRootType().value);
		entity.employmentRootAtr = domain.getEmploymentRootAtr().value;
		return entity;
	}
}
