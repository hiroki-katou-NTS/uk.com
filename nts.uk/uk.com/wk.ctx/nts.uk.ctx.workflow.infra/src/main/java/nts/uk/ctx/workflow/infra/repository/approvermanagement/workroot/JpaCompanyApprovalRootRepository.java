package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
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
	private static final String SELECT_COM_APR_BY_SYS = FIND_BY_CID 
				   + " AND c.endDate = :endDate"
				   + " AND c.employmentRootAtr = 0"
				   + " AND c.sysAtr = :sysAtr";
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
	private static final String SELECT_COM_APR_SYS = FIND_BY_CID 
				   + " AND c.employmentRootAtr = 0"
				   + " AND c.sysAtr = :sysAtr"
				   + " ORDER BY c.startDate DESC";
	private static final String FIND_LAST_BY_END_DATE = FIND_BY_CID 
					 +" AND c.sysAtr = :sysAtr"
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
	private static final String FIND_BY_ATR_WORK1 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 0"
			+ " AND c.employmentRootAtr = 1"
			+ " AND c.applicationType IN :lstAppType";
	private static final String FIND_BY_ATR_WORK02 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 0"
			+ " AND c.employmentRootAtr IN (0,2)";
	private static final String FIND_BY_ATR_HR0 = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 0";
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
	
	private static final String FIND_COMMON;
	private static final String FIND_APPLICATION;
	private static final String FIND_CONFIRMATION;
	private static final String FIND_ANYITEM;
	private static final String FIND_NOTICE;
	private static final String FIND_BUS_EVENT;
	static {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT CID, APPROVAL_ID, HIST_ID, START_DATE, END_DATE, APP_TYPE, ");
		builder.append("CONFIRMATION_ROOT_TYPE, EMPLOYMENT_ROOT_ATR, SYSTEM_ATR, NOTICE_ID, BUS_EVENT_ID ");
		builder.append("FROM WWFMT_COM_APPROVAL_ROOT WHERE CID = 'companyID' ");
		builder.append("AND SYSTEM_ATR = 'sysAtr' AND START_DATE <= 'date' AND END_DATE >= 'date' ");
		builder.append("AND EMPLOYMENT_ROOT_ATR = 'rootAtr'");
		FIND_COMMON = builder.toString();
		
		builder = new StringBuilder();
		builder.append(FIND_COMMON);
		builder.append(" AND APP_TYPE = 'targetType'");
		FIND_APPLICATION = builder.toString();
		
		builder = new StringBuilder();
		builder.append(FIND_COMMON);
		builder.append(" AND CONFIRMATION_ROOT_TYPE = 'targetType'");
		FIND_CONFIRMATION = builder.toString();
		
		builder = new StringBuilder();
		builder.append(FIND_COMMON);
		// builder.append(" AND ANYITEM_APP_ID = 'targetType'");
		FIND_ANYITEM = builder.toString();
		
		builder = new StringBuilder();
		builder.append(FIND_COMMON);
		builder.append(" AND NOTICE_ID = 'targetType'");
		FIND_NOTICE = builder.toString();
		
		builder = new StringBuilder();
		builder.append(FIND_COMMON);
		builder.append(" AND BUS_EVENT_ID = 'targetType'");
		FIND_BUS_EVENT = builder.toString();
	}
	
	private static final String FIND_ALL_BY_BASEDATE_CM = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 0";
	private static final String FIND_ALL_BY_BASEDATE_NT = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 4"
			+ " AND c.noticeId IN :lstNoticeID";
	private static final String FIND_ALL_BY_BASEDATE_EV = "SELECT c FROM WwfmtComApprovalRoot c"
			+ " WHERE c.wwfmtComApprovalRootPK.companyId = :companyId"
			+ " AND c.startDate <= :baseDate"
			+ " AND c.endDate >= :baseDate"
			+ " AND c.sysAtr = 1"
			+ " AND c.employmentRootAtr = 5"
			+ " AND c.busEventId IN :lstEventID";
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
			List<Integer> lstNoticeID, List<String> lstEventID) {
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
			lstCom.addAll(this.queryProxy().query(FIND_BY_ATR_HR0, WwfmtComApprovalRoot.class)
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
	public List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, Integer applicationType,
			int employmentRootAtr, String id, int sysAtr) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_COM_APR_BY_SYS, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("endDate", endDate)
					.setParameter("sysAtr", sysAtr)
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
	
	@Override
	public Optional<CompanyApprovalRoot> findByBaseDate(String companyID, GeneralDate date, EmploymentRootAtr rootAtr, String targetType, int sysAtr) {
		String query = "";
		switch (rootAtr) {
		case APPLICATION:
			query = FIND_APPLICATION;
			break;
		case CONFIRMATION:
			query = FIND_CONFIRMATION;
			break;
		case ANYITEM:
			query = FIND_ANYITEM;
			break;
		case NOTICE:
			query = FIND_NOTICE;
			break;
		case BUS_EVENT:
			query = FIND_BUS_EVENT;
			break;
		default:
			return Optional.empty();
		}
		query = query.replaceAll("companyID", companyID);
		query = query.replaceAll("sysAtr", String.valueOf(sysAtr));
		query = query.replaceAll("date", date.toString("yyyy-MM-dd"));
		query = query.replaceAll("rootAtr", String.valueOf(rootAtr.value));
		query = query.replaceAll("targetType", targetType);
		try (PreparedStatement pstatement = this.connection().prepareStatement(query)) {
			Optional<CompanyApprovalRoot> op = new NtsResultSet(pstatement.executeQuery())
			.getSingle(x -> convertNtsResult(x));
			return op;
		} catch (Exception e) {
			throw new RuntimeException("CompanyApprovalRoot error");
		}
	}
	
	@Override
	public Optional<CompanyApprovalRoot> findByBaseDateOfCommon(String companyID, GeneralDate date, int sysAtr) {
		String query = FIND_COMMON;
		query = query.replaceAll("companyID", companyID);
		query = query.replaceAll("sysAtr", String.valueOf(sysAtr));
		query = query.replaceAll("date", date.toString("yyyy-MM-dd"));
		query = query.replaceAll("rootAtr", "0");
		try (PreparedStatement pstatement = this.connection().prepareStatement(query)) {
			return new NtsResultSet(pstatement.executeQuery())
			.getSingle(x -> convertNtsResult(x));
		} catch (Exception e) {
			throw new RuntimeException("CompanyApprovalRoot error");
		}
	}
	
	private CompanyApprovalRoot convertNtsResult(NtsResultRecord record) {
		return CompanyApprovalRoot.createSimpleFromJavaType(
				record.getString("CID"), 
				record.getString("APPROVAL_ID"), 
				record.getString("HIST_ID"), 
				record.getInt("APP_TYPE"), 
				record.getGeneralDate("START_DATE").toString("yyyy-MM-dd"), 
				record.getGeneralDate("END_DATE").toString("yyyy-MM-dd"), 
				// record.getString("BRANCH_ID"), 
				// record.getString("ANYITEM_APP_ID"), 
				record.getInt("CONFIRMATION_ROOT_TYPE"), 
				record.getInt("EMPLOYMENT_ROOT_ATR"), 
				record.getInt("SYSTEM_ATR"), 
				record.getInt("NOTICE_ID"), 
				record.getString("BUS_EVENT_ID"));
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
			// x.setBranchId(a.branchId);
			// x.setAnyItemAppId(a.anyItemAppId);
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
			// x.setBranchId(a.branchId);
			// x.setAnyItemAppId(a.anyItemAppId);
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
				// entity.branchId,
				// entity.anyItemAppId,
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
		// entity.branchId = domain.getApprRoot().getBranchId();
		entity.employmentRootAtr = domain.getApprRoot().getEmploymentRootAtr().value;
		entity.applicationType = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.APPLICATION) ?
				domain.getApprRoot().getApplicationType().value : null;
		entity.confirmationRootType = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.CONFIRMATION) ?
				domain.getApprRoot().getConfirmationRootType().value : null;
//		entity.anyItemAppId = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.ANYITEM) ?
//				domain.getApprRoot().getAnyItemApplicationId() : null;
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
			int employmentRootAtr, String id, int sysAtr) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_COM_APR_SYS, WwfmtComApprovalRoot.class)
					.setParameter("companyId", companyId)
					.setParameter("sysAtr", sysAtr)
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
	public List<CompanyApprovalRoot> getComAppRootLast(String companyID, GeneralDate endDate, int sysAtr) {
		return this.queryProxy().query(FIND_LAST_BY_END_DATE,WwfmtComApprovalRoot.class)
				.setParameter("companyId", companyID)
				.setParameter("sysAtr", sysAtr)
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
	public List<CompanyApprovalRoot> findByBaseDateJinji(String cid, GeneralDate baseDate,
			List<Integer> lstNoticeID, List<String> lstEventID) {
		List<CompanyApprovalRoot> lstResult = new ArrayList<>();
		lstResult.addAll(this.queryProxy().query(FIND_ALL_BY_BASEDATE_CM, WwfmtComApprovalRoot.class)
				.setParameter("companyId", cid)
				.setParameter("baseDate", baseDate)
				.getList(c->toDomainComApR(c)));
		if(!lstNoticeID.isEmpty()) {
			lstResult.addAll(this.queryProxy().query(FIND_ALL_BY_BASEDATE_NT, WwfmtComApprovalRoot.class)
					.setParameter("companyId", cid)
					.setParameter("baseDate", baseDate)
					.setParameter("lstNoticeID", lstNoticeID)
					.getList(c->toDomainComApR(c)));
		}
		if(!lstEventID.isEmpty()) {
			lstResult.addAll(this.queryProxy().query(FIND_ALL_BY_BASEDATE_EV, WwfmtComApprovalRoot.class)
					.setParameter("companyId", cid)
					.setParameter("baseDate", baseDate)
					.setParameter("lstEventID", lstEventID)
					.getList(c->toDomainComApR(c)));
		}
		return lstResult;
	}
}
