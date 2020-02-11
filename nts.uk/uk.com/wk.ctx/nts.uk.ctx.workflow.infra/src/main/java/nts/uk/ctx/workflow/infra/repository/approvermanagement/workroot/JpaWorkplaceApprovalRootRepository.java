package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtWpApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtWpApprovalRootPK;
/**
 * 
 * @author hoatt
 *
 */
@RequestScoped
public class JpaWorkplaceApprovalRootRepository extends JpaRepository implements WorkplaceApprovalRootRepository{

	private static final String FIND_BY_ALL = "SELECT c FROM WwfmtWpApprovalRoot c";
	private static final String FIND_BY_CID = FIND_BY_ALL
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId";
	private static final String FIND_BY_WKPID = FIND_BY_ALL
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId";
	private static final String SELECT_WPAPR_BY_EDATE = FIND_BY_WKPID
			+ " AND c.endDate = :endDate"
			+ " AND c.employmentRootAtr = :employmentRootAtr"
			+ " AND c.applicationType = :applicationType";
	private static final String SELECT_WPAPR_BY_EDATE_APP_NULL = FIND_BY_WKPID
			   + " AND c.endDate = :endDate"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " AND c.applicationType IS NULL";
	private static final String SELECT_WPAPR_BY_EDATE_CONFIRM = FIND_BY_WKPID
			   + " AND c.endDate = :endDate" 
			   + " AND c.confirmationRootType = :confirmationRootType"
			   + " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_BASEDATE = FIND_BY_WKPID
			+ " AND c.sysAtr = :sysAtr"
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.employmentRootAtr = :rootAtr" 
			+ " AND c.applicationType = :appType";
	private static final String FIND_BY_BASEDATE_OF_COM = FIND_BY_WKPID
			+ " AND c.sysAtr = :sysAtr"
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.employmentRootAtr = 0";
	private static final String FIND_ALL_BY_BASEDATE = FIND_BY_ALL + " WHERE  c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.sysAtr = :sysAtr";
	private static final String FIND_BY_APP_TYPE = FIND_BY_WKPID
			+ " AND c.employmentRootAtr = :employmentRootAtr"
			+ " AND c.applicationType = :applicationType"
			+ " ORDER BY c.startDate DESC";
	private static final String SELECT_WPAPR_BY_APP_NULL = FIND_BY_WKPID
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " AND c.applicationType IS NULL"
			   + " ORDER BY c.startDate DESC";
	private static final String FIND_BY_CFR_TYPE = FIND_BY_WKPID
			   + " AND c.confirmationRootType = :confirmationRootType"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	private static final String FIND_WP_APP_LAST = FIND_BY_WKPID
			+ " AND c.endDate = :endDate";
	private static final String FIND_BY_DATE_EMP_CONFIRM = FIND_BY_CID 
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.confirmationRootType = :confirmationRootType"
			+ " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_DATE_EMP = FIND_BY_CID 
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.employmentRootAtr = :employmentRootAtr";	
	private static final String FIND_BY_EMP_CONFIRM = FIND_BY_WKPID
			 + " AND c.startDate <= :baseDate"
			 + " AND c.endDate >= :baseDate"
			 + " AND c.confirmationRootType = :confirmationRootType"
			 + " AND c.employmentRootAtr = 2";
	//CMM018_ver2
	private static final String FIND_BY_ATR_WORK1 = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId"
			+ " AND c.sysAtr = 0"
			+ " AND c.employmentRootAtr = 1"
			+ " AND c.applicationType IN :lstAppType";
	private static final String FIND_BY_ATR_WORK02 = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId"
			+ " AND c.sysAtr = 0"
			+ " AND c.employmentRootAtr IN (0,2)";
	private static final String FIND_BY_ATR_HR02 = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr IN (0,2)";
	private static final String FIND_BY_ATR_HR4 = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 4"
			+ " AND c.noticeId IN :lstNoticeID";
	private static final String FIND_BY_ATR_HR5 = "SELECT c FROM WwfmtWpApprovalRoot c"
			+ " WHERE c.wwfmtWpApprovalRootPK.companyId = :companyId"
			+ " AND c.wwfmtWpApprovalRootPK.workplaceId = :workplaceId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 5"
			+ " AND c.busEventId IN :lstEventID";
	private static final String FIND_BY_NTR_TYPE = FIND_BY_WKPID
			   + " AND c.noticeId = :noticeId"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	private static final String FIND_BY_EVR_TYPE = FIND_BY_WKPID
			   + " AND c.busEventId = :busEventId"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	private static final String SELECT_WPAPR_BY_EDATE_NOTICE = FIND_BY_WKPID
			   + " AND c.endDate = :endDate" 
			   + " AND c.noticeId = :noticeId"
			   + " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String SELECT_WPAPR_BY_EDATE_EVENT = FIND_BY_WKPID
			   + " AND c.endDate = :endDate" 
			   + " AND c.busEventId = :busEventId"
			   + " AND c.employmentRootAtr = :employmentRootAtr";
	/**
	 * get All Workplace Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @return
	 */
	@Override
	public List<WorkplaceApprovalRoot> getWpRootStart(String companyId, String workplaceId, int sysAtr,
			List<Integer> lstAppType, List<String> lstNoticeID, List<String> lstEventID) {
		List<WorkplaceApprovalRoot> lstWp = new ArrayList<>();
		if(sysAtr == SystemAtr.WORK.value){//就業
			lstWp.addAll(this.queryProxy().query(FIND_BY_ATR_WORK02, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.getList(c->toDomainWpApR(c)));
			if(!lstAppType.isEmpty()){
				lstWp.addAll(this.queryProxy().query(FIND_BY_ATR_WORK1, WwfmtWpApprovalRoot.class)
						.setParameter("companyId", companyId)
						.setParameter("workplaceId", workplaceId)
						.setParameter("lstAppType", lstAppType)
						.getList(c->toDomainWpApR(c)));
			}
		}else{//人事
			lstWp.addAll(this.queryProxy().query(FIND_BY_ATR_HR02, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.getList(c->toDomainWpApR(c)));
			if(!lstNoticeID.isEmpty()){
				lstWp.addAll(this.queryProxy().query(FIND_BY_ATR_HR4, WwfmtWpApprovalRoot.class)
						.setParameter("companyId", companyId)
						.setParameter("workplaceId", workplaceId)
						.setParameter("lstNoticeID", lstNoticeID)
						.getList(c->toDomainWpApR(c)));
			}
			if(!lstEventID.isEmpty()){
				lstWp.addAll(this.queryProxy().query(FIND_BY_ATR_HR5, WwfmtWpApprovalRoot.class)
						.setParameter("companyId", companyId)
						.setParameter("workplaceId", workplaceId)
						.setParameter("lstEventID", lstEventID)
						.getList(c->toDomainWpApR(c)));
			}
		}
		return lstWp;
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
	public List<WorkplaceApprovalRoot> getWpApprovalRootByEdate(String companyId, String workplaceId, GeneralDate endDate, Integer applicationType,
			int employmentRootAtr, String id) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_WPAPR_BY_EDATE_APP_NULL, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("endDate", endDate)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//confirm
		if(employmentRootAtr == 2){
			return this.queryProxy().query(SELECT_WPAPR_BY_EDATE_CONFIRM, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("endDate", endDate)
					.setParameter("confirmationRootType", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//confirm
		if(employmentRootAtr == 4){
			return this.queryProxy().query(SELECT_WPAPR_BY_EDATE_NOTICE, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("endDate", endDate)
					.setParameter("noticeId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//confirm
		if(employmentRootAtr == 5){
			return this.queryProxy().query(SELECT_WPAPR_BY_EDATE_EVENT, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("endDate", endDate)
					.setParameter("busEventId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//15 app type
		return this.queryProxy().query(SELECT_WPAPR_BY_EDATE, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("endDate", endDate)
				.setParameter("applicationType", applicationType)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainWpApR(c));
	}
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * @param cid
	 * @param workplaceId
	 * @param baseDate
	 * @param appType
	 * @return WorkplaceApprovalRoots
	 */
	@Override
	public Optional<WorkplaceApprovalRoot> findByBaseDate(String companyID, String workplaceID, GeneralDate date, ApplicationType appType,
			EmploymentRootAtr rootAtr, int sysAtr) {
		return this.queryProxy().query(FIND_BY_BASEDATE, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("workplaceId", workplaceID)
				.setParameter("sysAtr", sysAtr)
				.setParameter("baseDate", date)
				.setParameter("appType", appType.value)
				.setParameter("rootAtr", rootAtr.value)
				.getSingle(c->toDomainWpApR(c));
	}
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(共通の)
	 * @param cid
	 * @param workplaceId
	 * @param baseDate
	 * @param appType
	 * @return WorkplaceApprovalRoots
	 */
	@Override
	public Optional<WorkplaceApprovalRoot> findByBaseDateOfCommon(String companyID, String workplaceID, GeneralDate date, int sysAtr) {
		return this.queryProxy().query(FIND_BY_BASEDATE_OF_COM, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("workplaceId", workplaceID)
				.setParameter("sysAtr", sysAtr)
				.setParameter("baseDate", date)
				.getSingle(c->toDomainWpApR(c));
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
	 * add All Workplace Approval Root
	 * @param wpAppRoot
	 */
	@Override
	public void addAllWpApprovalRoot(List<WorkplaceApprovalRoot> wpAppRoot) {
		List<WwfmtWpApprovalRoot> lstEntity = new ArrayList<>();
		for (WorkplaceApprovalRoot wp : wpAppRoot) {
			lstEntity.add(toEntityWpApR(wp));
		}
		this.commandProxy().insertAll(lstEntity);
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
	 * update All Workplace Approval Root
	 * @param wpAppRoot
	 */
	@Override
	public void updateAllWpApprovalRoot(List<WorkplaceApprovalRoot> wpAppRoot) {
		List<WwfmtWpApprovalRoot> lstEntity = new ArrayList<>();
		for (WorkplaceApprovalRoot wp : wpAppRoot) {
			WwfmtWpApprovalRoot a = toEntityWpApR(wp);
			WwfmtWpApprovalRoot x = this.queryProxy().find(a.wwfmtWpApprovalRootPK, WwfmtWpApprovalRoot.class).get();
			x.setStartDate(a.startDate);
			x.setEndDate(a.endDate);
			x.setApplicationType(a.applicationType);
			x.setBranchId(a.branchId);
			x.setAnyItemAppId(a.anyItemAppId);
			x.setConfirmationRootType(a.confirmationRootType);
			x.setEmploymentRootAtr(a.employmentRootAtr);
			lstEntity.add(x);
		}
		
		this.commandProxy().updateAll(lstEntity);
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
		val domain = WorkplaceApprovalRoot.convert(entity.wwfmtWpApprovalRootPK.companyId,
				entity.wwfmtWpApprovalRootPK.approvalId,
				entity.wwfmtWpApprovalRootPK.workplaceId,
				entity.wwfmtWpApprovalRootPK.historyId,
				entity.applicationType,
				entity.startDate,
				entity.endDate,
				entity.branchId,
				entity.anyItemAppId,
				entity.confirmationRootType,
				entity.employmentRootAtr,
				entity.sysAtr,
				entity.noticeId,
				entity.busEventId);
		return domain;
	}
	
	/**
	 * convert domain WorkplaceApprovalRoot to entity WwfmtWpApprovalRoot
	 * @param domain
	 * @return
	 */
	private WwfmtWpApprovalRoot toEntityWpApR(WorkplaceApprovalRoot domain){
		val entity = new WwfmtWpApprovalRoot();
		entity.wwfmtWpApprovalRootPK = new WwfmtWpApprovalRootPK(domain.getCompanyId(), domain.getApprovalId(),
				domain.getWorkplaceId(), domain.getApprRoot().getHistoryItems().get(0).getHistoryId());
		entity.sysAtr = domain.getApprRoot().getSysAtr().value;
		entity.startDate = domain.getApprRoot().getHistoryItems().get(0).start();
		entity.endDate = domain.getApprRoot().getHistoryItems().get(0).end();
		entity.branchId = domain.getApprRoot().getBranchId();
		entity.employmentRootAtr = domain.getApprRoot().getEmploymentRootAtr().value;
		entity.applicationType = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.APPLICATION) ?
				domain.getApprRoot().getApplicationType().value : null;
		entity.confirmationRootType = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.CONFIRMATION) ?
				domain.getApprRoot().getConfirmationRootType().value : null;
		entity.anyItemAppId = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.ANYITEM) ?
				domain.getApprRoot().getAnyItemApplicationId() : null;
		entity.noticeId = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.NOTICE) ?
				domain.getApprRoot().getNoticeId() : null;
		entity.busEventId = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.BUS_EVENT) ?
				domain.getApprRoot().getBusEventId() : null;
		return entity;
	}
	
	public List<WorkplaceApprovalRoot> findAllByBaseDate(String companyId, GeneralDate baseDate, int sysAtr){
		List<WorkplaceApprovalRoot> data = this.queryProxy()
				.query(FIND_ALL_BY_BASEDATE, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("baseDate", baseDate)
				.setParameter("sysAtr", sysAtr)
				.getList(c->toDomainWpApR(c));
		return data;
	}
	/**
	 * get Work place Approval Root By type
	 * @param companyId
	 * @param workplaceId
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	@Override
	public List<WorkplaceApprovalRoot> getWpApprovalRootByType(String companyId, String workplaceId, Integer applicationType,
			int employmentRootAtr, String id) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_WPAPR_BY_APP_NULL, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//confirm
		if(employmentRootAtr == 2){
			return this.queryProxy().query(FIND_BY_CFR_TYPE, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("confirmationRootType", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//notice
		if(employmentRootAtr == 4){
			return this.queryProxy().query(FIND_BY_NTR_TYPE, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("noticeId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//event
		if(employmentRootAtr == 5){
			return this.queryProxy().query(FIND_BY_EVR_TYPE, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("workplaceId", workplaceId)
					.setParameter("busEventId ", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainWpApR(c));
		}
		//15 app type
		return this.queryProxy().query(FIND_BY_APP_TYPE, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("applicationType", applicationType)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainWpApR(c));
	}

	@Override
	public List<WorkplaceApprovalRoot> getWpAppRootLast(String companyId, String workplaceId, GeneralDate endDate) {
		
		return this.queryProxy().query(FIND_WP_APP_LAST, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("endDate", endDate)
				.getList(c -> toDomainWpApR(c));
	}

	@Override
	public List<WorkplaceApprovalRoot> getWpAppRoot(String companyID, GeneralDate date, 
			Integer employmentRootAtr, Integer confirmRootAtr) {
		if(confirmRootAtr==null){
			return this.queryProxy().query(FIND_BY_DATE_EMP, WwfmtWpApprovalRoot.class)
					.setParameter("companyId", companyID)
					.setParameter("baseDate", date)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c -> toDomainWpApR(c));
		}
		return this.queryProxy().query(FIND_BY_DATE_EMP_CONFIRM, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("baseDate", date)
				.setParameter("confirmationRootType", confirmRootAtr)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c -> toDomainWpApR(c));
	}

	@Override
	public List<WorkplaceApprovalRoot> findEmpByConfirm(String companyID, String workplaceID, 
			ConfirmationRootType confirmType, GeneralDate date) {
		return this.queryProxy().query(FIND_BY_EMP_CONFIRM, WwfmtWpApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("workplaceId", workplaceID)
				.setParameter("baseDate", date)
				.setParameter("confirmationRootType", confirmType.value)
				.getList(c->toDomainWpApR(c));
	}
}
