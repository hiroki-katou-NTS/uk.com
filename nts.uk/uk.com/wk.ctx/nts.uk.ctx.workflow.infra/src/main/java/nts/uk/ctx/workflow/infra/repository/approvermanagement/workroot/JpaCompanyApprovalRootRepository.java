package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRoot;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtComApprovalRootPK;
/**
 * 
 * @author hoatt
 *
 */
@RequestScoped
public class JpaCompanyApprovalRootRepository extends JpaRepository implements CompanyApprovalRootRepository{

	private static final String FIND_BY_ALL = "SELECT c FROM WwfmtComApprovalRoot c";
	private static final String FIND_BY_CID = FIND_BY_ALL
	   + " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId";
	private static final String FIND_BY_DATE = FIND_BY_CID 
	   + " AND c.endDate = :endDate"
	   + " AND c.applicationType = :applicationType"
	   + " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_DATE_CFR = FIND_BY_CID 
			   + " AND c.endDate = :endDate"
			   + " AND c.confirmationRootType = :confirmationRootType"
			   + " AND c.employmentRootAtr = :employmentRootAtr"; 
	private static final String SELECT_COM_APR_BY_DATE_APP_NULL = FIND_BY_CID 
				   + " AND c.endDate = :endDate"
				   + " AND c.employmentRootAtr = :employmentRootAtr"
				   + " AND c.applicationType IS NULL";
	private static final String FIND_BY_BASEDATE = FIND_BY_CID
				+ " AND c.sysAtr = :sysAtr"
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.employmentRootAtr = :rootAtr" 
				+ " AND c.applicationType = :appType";
	private static final String FIND_BY_BASEDATE_OF_COM = FIND_BY_CID
				+ " AND c.sysAtr = :sysAtr"
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.employmentRootAtr = 0";
	
	private static final String FIND_ALL_BY_BASEDATE = FIND_BY_CID
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.sysAtr = :sysAtr";
	private static final String FIND_BY_APP_TYPE = FIND_BY_CID 
			   + " AND c.applicationType = :applicationType"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	private static final String FIND_BY_CFR_TYPE = FIND_BY_CID 
			   + " AND c.confirmationRootType = :confirmationRootType"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	private static final String SELECT_COM_APR_APP_NULL = FIND_BY_CID 
				   + " AND c.employmentRootAtr = :employmentRootAtr"
				   + " AND c.applicationType IS NULL"
				   + " ORDER BY c.startDate DESC";
	private static final String FIND_LAST_BY_END_DATE = FIND_BY_CID 
					 +" AND c.endDate = :endDate";
	private static final String FIND_BY_DATE_EMP_CONFIRM = FIND_BY_CID 
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.confirmationRootType = :confirmationRootType"
				+ " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_DATE_EMP = FIND_BY_CID 
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.employmentRootAtr = :employmentRootAtr";
	
	private static final String FIND_BY_EMP_CONFIRM = FIND_BY_CID
			 + " AND c.startDate <= :baseDate"
			 + " AND c.endDate >= :baseDate"
			 + " AND c.confirmationRootType = :confirmationRootType"
			 + " AND c.employmentRootAtr = 2";
	private static final String FIND_BY_ATR_WORK1 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 0"
			+ " AND c.employmentRootAtr = 1"
			+ " AND c.applicationType IN :lstAppType";
	private static final String FIND_BY_ATR_WORK02 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 0"
			+ " AND c.employmentRootAtr IN (0,2)";
	private static final String FIND_BY_ATR_HR02 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr IN (0,2)";
	private static final String FIND_BY_ATR_HR4 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 4"
			+ " AND c.noticeId IN :lstNoticeID";
	private static final String FIND_BY_ATR_HR5 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 5"
			+ " AND c.busEventId IN :lstEventID";
	private static final String FIND_BY_DATE_NTR = FIND_BY_CID 
			   + " AND c.endDate = :endDate"
			   + " AND c.noticeId = :noticeId"
			   + " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_DATE_EVR = FIND_BY_CID 
			   + " AND c.endDate = :endDate"
			   + " AND c.busEventId = :busEventId"
			   + " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_NTR_TYPE = FIND_BY_CID 
			   + " AND c.noticeId = :noticeId"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	private static final String FIND_BY_EVR_TYPE = FIND_BY_CID 
			   + " AND c.busEventId = :busEventId"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	/**
	 * getComRootStart CMM018
	 * @param companyId
	 * @param sysAtr
	 * @param lstAppType
	 * @param lstNoticeID
	 * @param lstEventID
	 * @return
	 */
	@Override
	public List<CompanyApprovalRoot> getComRootStart(String companyId, int sysAtr, List<Integer> lstAppType,
			List<String> lstNoticeID, List<String> lstEventID) {
		List<CompanyApprovalRoot> lstCom = new ArrayList<>();
		if(sysAtr == SystemAtr.WORK.value){//就業
			lstCom.addAll(this.queryProxy().query(FIND_BY_ATR_WORK02, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.getList(c->toDomainComApR(c)));
			if(!lstAppType.isEmpty()){
				lstCom.addAll(this.queryProxy().query(FIND_BY_ATR_WORK1, WwfmtComApprovalRoot.class)
						.setParameter("companyId", companyId)
						.setParameter("lstAppType", lstAppType)
						.getList(c->toDomainComApR(c)));
			}
		}else{//人事
			lstCom.addAll(this.queryProxy().query(FIND_BY_ATR_HR02, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.getList(c->toDomainComApR(c)));
			if(!lstNoticeID.isEmpty()){
				lstCom.addAll(this.queryProxy().query(FIND_BY_ATR_HR4, WwfmtComApprovalRoot.class)
						.setParameter("companyId", companyId)
						.setParameter("lstNoticeID", lstNoticeID)
						.getList(c->toDomainComApR(c)));
			}
			if(!lstEventID.isEmpty()){
				lstCom.addAll(this.queryProxy().query(FIND_BY_ATR_HR5, WwfmtComApprovalRoot.class)
						.setParameter("companyId", companyId)
						.setParameter("lstEventID", lstEventID)
						.getList(c->toDomainComApR(c)));
			}
		}
		return lstCom;
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
	public List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, Integer applicationType, int employmentRootAtr, String id) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_COM_APR_BY_DATE_APP_NULL, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("endDate", endDate)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//confirm
		if(employmentRootAtr == 2){
			return this.queryProxy().query(FIND_BY_DATE_CFR, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("endDate", endDate)
					.setParameter("confirmationRootType", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//notice
		if(employmentRootAtr == 4){
			return this.queryProxy().query(FIND_BY_DATE_NTR, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("endDate", endDate)
					.setParameter("noticeId", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//event
		if(employmentRootAtr == 5){
			return this.queryProxy().query(FIND_BY_DATE_EVR, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("endDate", endDate)
					.setParameter("busEventId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//15 app type
		return this.queryProxy().query(FIND_BY_DATE, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("endDate", endDate)
				.setParameter("applicationType", applicationType)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainComApR(c));
	}
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * @param cid
	 * @param workplaceId
	 * @param baseDate
	 * @param appType
	 * @return WorkplaceApprovalRoots
	 */
	@Override
	public Optional<CompanyApprovalRoot> findByBaseDate(String companyID, GeneralDate date, ApplicationType appType, EmploymentRootAtr rootAt, int sysAtr) {
		return this.queryProxy().query(FIND_BY_BASEDATE, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("sysAtr", sysAtr)
				.setParameter("baseDate", date)
				.setParameter("appType", appType.value)
				.setParameter("rootAtr", rootAt.value)
				.getSingle(c->toDomainComApR(c));
	}
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(共通の)
	 * @param cid
	 * @param workplaceId
	 * @param baseDate
	 * @param appType
	 * @return WorkplaceApprovalRoots
	 */
	@Override
	public Optional<CompanyApprovalRoot> findByBaseDateOfCommon(String companyID, GeneralDate date, int sysAtr) {
		return this.queryProxy().query(FIND_BY_BASEDATE_OF_COM, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("sysAtr", sysAtr)
				.setParameter("baseDate", date)
				.getSingle(c->toDomainComApR(c));
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
	 * add All Company Approval Root
	 * @param comAppRoot
	 */
	@Override
	public void addAllComApprovalRoot(List<CompanyApprovalRoot> comAppRoot) {
		List<WwfmtComApprovalRoot> lstEntity = new ArrayList<>();
		for (CompanyApprovalRoot com : comAppRoot) {
			lstEntity.add(toEntityComApR(com));
		}
		this.commandProxy().insertAll(lstEntity);
	}
	/**
	 * update All Company Approval Root
	 * @param comAppRoot
	 */
	@Override
	public void updateAllComApprovalRoot(List<CompanyApprovalRoot> comAppRoot) {
		List<WwfmtComApprovalRoot> lstEntity = new ArrayList<>();
		for (CompanyApprovalRoot com : comAppRoot) {
			WwfmtComApprovalRoot a = toEntityComApR(com);
			WwfmtComApprovalRoot x = this.queryProxy().find(a.wwfmtComApprovalRootPK, WwfmtComApprovalRoot.class).get();
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
	 * update Company Approval Root
	 * 
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
	/**
	 * convert entity WwfmtComApprovalRoot to domain CompanyApprovalRoot
	 * @param entity
	 * @return
	 */
	private CompanyApprovalRoot toDomainComApR(WwfmtComApprovalRoot entity){
		val domain = CompanyApprovalRoot.convert(entity.wwfmtComApprovalRootPK.companyId,
				entity.wwfmtComApprovalRootPK.approvalId,
				entity.wwfmtComApprovalRootPK.historyId,
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
	 * convert domain CompanyApprovalRoot to entity WwfmtComApprovalRoot
	 * @param domain
	 * @return
	 */
	private WwfmtComApprovalRoot toEntityComApR(CompanyApprovalRoot domain){
		val entity = new WwfmtComApprovalRoot();
		entity.wwfmtComApprovalRootPK = new WwfmtComApprovalRootPK(domain.getCompanyId(), domain.getApprovalId(),
				domain.getApprRoot().getHistoryItems().get(0).getHistoryId());
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
	@Override
	public List<CompanyApprovalRoot> findByBaseDate(String cid, GeneralDate baseDate, int sysAtr) {
		return this.queryProxy().query(FIND_ALL_BY_BASEDATE, WwfmtComApprovalRoot.class)
				.setParameter("companyId", cid)
				.setParameter("baseDate", baseDate)
				.setParameter("sysAtr", sysAtr)
				.getList(c->toDomainComApR(c));
	}
	/**
	 * get Company Approval Root By type
	 * @param companyId
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	@Override
	public List<CompanyApprovalRoot> getComApprovalRootByType(String companyId, Integer applicationType,
			int employmentRootAtr, String id) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_COM_APR_APP_NULL, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//confirm
		if(employmentRootAtr == 2){
			return this.queryProxy().query(FIND_BY_CFR_TYPE, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("confirmationRootType", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//notice
		if(employmentRootAtr == 4){
			return this.queryProxy().query(FIND_BY_NTR_TYPE, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("noticeId", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//event
		if(employmentRootAtr == 5){
			return this.queryProxy().query(FIND_BY_EVR_TYPE, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("busEventId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		//15 app type
		return this.queryProxy().query(FIND_BY_APP_TYPE, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyId)
				.setParameter("applicationType", applicationType)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainComApR(c));
	}
	@Override
	public List<CompanyApprovalRoot> getComAppRootLast(String companyID, GeneralDate endDate) {
		return this.queryProxy().query(FIND_LAST_BY_END_DATE,WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("endDate", endDate)
				.getList(c ->toDomainComApR(c));
	}
	@Override
	public List<CompanyApprovalRoot> getComAppRoot(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		if(confirmRootAtr==null){
			return this.queryProxy().query(FIND_BY_DATE_EMP, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyID)
					.setParameter("baseDate", date)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainComApR(c));
		}
		return this.queryProxy().query(FIND_BY_DATE_EMP_CONFIRM, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("baseDate", date)
				.setParameter("confirmationRootType", confirmRootAtr)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainComApR(c));
	}
	@Override
	public List<CompanyApprovalRoot> findEmpByConfirm(String companyID, ConfirmationRootType confirmType, GeneralDate date) {
		return this.queryProxy().query(FIND_BY_EMP_CONFIRM, WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("baseDate", date)
				.setParameter("confirmationRootType", confirmType.value)
				.getList(c->toDomainComApR(c));
	}
}
