package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppover;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtAppoverPK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhase;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalPhasePK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtApprovalRoutePs;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtPsApprovalRootPK;
/**
 * 
 * @author hoatt
 *
 */
@RequestScoped
public class JpaPersonApprovalRootRepository extends JpaRepository implements PersonApprovalRootRepository{

	 private static final String FIND_ALL = "SELECT c FROM WwfmtApprovalRoutePs c";
	 private static final String FIND_BY_CID = FIND_ALL
			   + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId";
	 private static final String FIN_BY_EMP = FIND_ALL
			   + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			   + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId";
	 private static final String SELECT_PS_APR_BY_ENDATE = FIN_BY_EMP
			   + " AND c.endDate = :endDate" 
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " AND c.applicationType = :applicationType";
	 private static final String SELECT_PS_APR_BY_ENDATE_CM_SYS = FIN_BY_EMP 
			   + " AND c.endDate = :endDate"
			   + " AND c.employmentRootAtr = 0"
			   + " AND c.sysAtr = :sysAtr";
	 private static final String SELECT_PS_APR_BY_ENDATE_CONFIRM = FIN_BY_EMP 
			   + " AND c.endDate = :endDate"
			   + " AND c.confirmationRootType = :confirmationRootType"
			   + " AND c.employmentRootAtr = :employmentRootAtr";
	 private static final String FIND_ALL_BY_BASEDATE = FIND_ALL + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			   + " AND c.startDate <= :baseDate"
			   + " AND c.endDate >= :baseDate"
			   + " AND c.sysAtr = :sysAtr";
	 private static final String FIND_BY_APP_TYPE = FIN_BY_EMP
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " AND c.applicationType = :applicationType"
			   + " ORDER BY c.startDate DESC";
	 private static final String SELECT_PSAPR_BY_CM_SYS = FIN_BY_EMP 
			   + " AND c.employmentRootAtr = 0"
			   + " AND c.sysAtr = :sysAtr"
			   + " ORDER BY c.startDate DESC";
	 private static final String FIND_BY_CFR_TYPE = FIN_BY_EMP 
			   + " AND c.confirmationRootType = :confirmationRootType"
			   + " AND c.employmentRootAtr = :employmentRootAtr"
			   + " ORDER BY c.startDate DESC";
	 private static final String FIND_PS_APP_LASTEST = FIN_BY_EMP
			 + " AND c.endDate = :endDate"
			 + " AND c.sysAtr = :sysAtr";
	 private static final String FIND_BY_DATE_EMP_CONFIRM = FIND_BY_CID 
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.confirmationRootType = :confirmationRootType"
				+ " AND c.employmentRootAtr = :employmentRootAtr";
	private static final String FIND_BY_DATE_EMP = FIND_BY_CID 
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.employmentRootAtr = :employmentRootAtr";
	 private static final String FIND_COMMON_PS_APP_LASTEST = FIN_BY_EMP
			 + " AND c.employmentRootAtr = 0 "
			 + " AND c.sysAtr = :sysAtr"
			 + " AND c.startDate = "
			 + "(SELECT MAX(c1.startDate) FROM WwfmtApprovalRoutePs c1 WHERE c1.wwfmtPsApprovalRootPK.companyId = :companyId"
			 + " AND c1.wwfmtPsApprovalRootPK.employeeId = :employeeId AND c1.employmentRootAtr = 0"
			 + " AND c1.sysAtr = :sysAtr)";
	 private static final String FIND_MONTHLY_PS_APP_LASTEST = FIN_BY_EMP
			 + " AND c.employmentRootAtr = 2"
			 + " AND c.confirmationRootType = 1"
			 + " AND c.startDate = (SELECT MAX(c1.startDate) FROM WwfmtApprovalRoutePs c1 WHERE c1.wwfmtPsApprovalRootPK.companyId = :companyId AND c1.wwfmtPsApprovalRootPK.employeeId = :employeeId AND c1.employmentRootAtr = 2 AND c1.confirmationRootType = 1)";
	 private static final String FIND_PART_HISTORY = FIN_BY_EMP
			 + " AND ((c.employmentRootAtr = 0 AND (c.applicationType IS NULL)) OR (c.employmentRootAtr = 2 AND c.confirmationRootType = 1))"
			 + " ORDER BY c.startDate DESC";
	 private static final String SELECT_PS_APR_BY_STARTDATE = FIN_BY_EMP
			 + " AND c.startDate = :startDate"
			 + " AND c.sysAtr = :sysAtr";
	 private static final String GET_ALL__MODE_COM = "SELECT c FROM WwfmtApprovalRoutePs c"
			 + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			 + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
			 + " ORDER BY c.startDate DESC";
	 private static final String GET_ALL_MODE_PRI_CM = "SELECT c FROM WwfmtApprovalRoutePs c"
			 + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			 + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
			 + " AND c.employmentRootAtr = 0"
			 + " AND c.sysAtr = :sysAtr"
			 + " ORDER BY c.startDate DESC";
	 private static final String GET_ALL_MODE_PRI_AP = "SELECT c FROM WwfmtApprovalRoutePs c"
			 + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			 + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
			 + " AND c.employmentRootAtr = 1"
			 + " AND c.applicationType = :applicationType"
			 + " ORDER BY c.startDate DESC";
	 private static final String GET_ALL_MODE_PRI_CF = "SELECT c FROM WwfmtApprovalRoutePs c"
			 + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
			 + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
			 + " AND c.employmentRootAtr = 2"
			 + " AND c.confirmationRootType = :confirmationRootType"
			 + " ORDER BY c.startDate DESC";
	 	//CMM018_ver2
		private static final String FIND_BY_ATR_WORK1 = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				+ " AND c.sysAtr = 0"
				+ " AND c.employmentRootAtr = 1"
				+ " AND c.applicationType IN :lstAppType";
		private static final String FIND_BY_ATR_WORK02 = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				+ " AND c.sysAtr = 0"
				+ " AND c.employmentRootAtr IN (0,2)";
		private static final String FIND_BY_ATR_HR0 = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				+ " AND c.sysAtr = 1"
				+ " AND c.employmentRootAtr = 0";
		private static final String FIND_BY_ATR_HR4 = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				+ " AND c.sysAtr = 1"
				+ " AND c.employmentRootAtr = 4"
				+ " AND c.noticeId IN :lstNoticeID";
		private static final String FIND_BY_ATR_HR5 = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				+ " AND c.sysAtr = 1"
				+ " AND c.employmentRootAtr = 5"
				+ " AND c.busEventId IN :lstEventID";
		private static final String FIND_BY_NTR_TYPE = FIN_BY_EMP 
				   + " AND c.noticeId = :noticeId"
				   + " AND c.employmentRootAtr = :employmentRootAtr"
				   + " ORDER BY c.startDate DESC";
		private static final String FIND_BY_EVR_TYPE = FIN_BY_EMP 
				   + " AND c.busEventId = :busEventId"
				   + " AND c.employmentRootAtr = :employmentRootAtr"
				   + " ORDER BY c.startDate DESC";
		private static final String SELECT_PS_APR_BY_ENDATE_NOTICE = FIN_BY_EMP 
				   + " AND c.endDate = :endDate"
				   + " AND c.noticeId = :noticeId"
				   + " AND c.employmentRootAtr = :employmentRootAtr";
		private static final String SELECT_PS_APR_BY_ENDATE_EVENT = FIN_BY_EMP 
				   + " AND c.endDate = :endDate"
				   + " AND c.busEventId = :busEventId"
				   + " AND c.employmentRootAtr = :employmentRootAtr";
		private static final String GET_ALL_MODE_PRI_NT = "SELECT c FROM WwfmtApprovalRoutePs c"
				 + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				 + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				 + " AND c.employmentRootAtr = 2"
				 + " AND c.noticeId = :noticeId"
				 + " ORDER BY c.startDate DESC";
		private static final String GET_ALL_MODE_PRI_EV = "SELECT c FROM WwfmtApprovalRoutePs c"
				 + " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				 + " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				 + " AND c.employmentRootAtr = 2"
				 + " AND c.busEventId = :busEventId"
				 + " ORDER BY c.startDate DESC";
		//CMM053
		private static final String FIND_BY_EDATE = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.wwfmtPsApprovalRootPK.employeeId = :employeeId"
				+ " AND c.sysAtr = :sysAtr"
				+ " AND c.endDate = :endDate";
		
		private static final String GET_PSROOT_BY_SID_AND_APPROVALS = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.wwfmtPsApprovalRootPK.approvalId IN :approvalIds"
				+ " AND m.sysAtr = 0";
		
		private static final String GET_HIST_WITH_START_AFTER_BASEDATE = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.startDate >= :baseDate"
				+ " AND m.sysAtr = 0";
		
		private static final String GET_PREVIOUS_HISTORY = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.endDate = :baseDate"
				+ " AND m.sysAtr = 0";
		
		private static final String GET_HIST_WITH_END_AFTER_BASEDATE = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.endDate >= :baseDate"
				+ " AND m.sysAtr = 0";
		
		private static final String GET_EMP_HIST_WITH_START_AFTER_BASEDATE = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId IN :sids"
				+ " AND m.startDate >= :baseDate"
				+ " AND m.sysAtr = 0"
				+ " AND m.opeMode = 1";
		
		private static final String GET_ALL_EMP_HIST = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.sysAtr = :sysAtr";
		
		private static final String GET_HIST_FROM_BASEDATE = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.opeMode = 1"
				+ " AND m.sysAtr = 0"
				+ " AND m.startDate <= :periodEnd"
				+ " AND m.endDate >= :periodStart";
		
		private static final String FIND_COMMON;
		private static final String FIND_APPLICATION;
		private static final String FIND_CONFIRMATION;
		private static final String FIND_ANYITEM;
		private static final String FIND_NOTICE;
		private static final String FIND_BUS_EVENT;
		static {
			
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT CID, APPROVAL_ID, SID, HIST_ID, START_DATE, END_DATE, APP_TYPE, ");
			builder.append("CONFIRMATION_ROOT_TYPE, EMPLOYMENT_ROOT_ATR, SYSTEM_ATR, NOTICE_ID, BUS_EVENT_ID ");
			builder.append("FROM WWFMT_APPROVAL_ROUTE_PS WHERE CID = 'companyID' AND SID = 'employeeID' ");
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
		
		private static final String FIND_ALL_BY_BASEDATE_CM = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE  c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.sysAtr = 1"
				+ " AND c.employmentRootAtr = 0";
		private static final String FIND_ALL_BY_BASEDATE_NT = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE  c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.sysAtr = 1"
				+ " AND c.employmentRootAtr = 4"
				+ " AND c.noticeId IN :lstNoticeID";
		private static final String FIND_ALL_BY_BASEDATE_EV = "SELECT c FROM WwfmtApprovalRoutePs c"
				+ " WHERE  c.wwfmtPsApprovalRootPK.companyId = :companyId"
				+ " AND c.startDate <= :baseDate"
				+ " AND c.endDate >= :baseDate"
				+ " AND c.sysAtr = 1"
				+ " AND c.employmentRootAtr = 5"
				+ " AND c.busEventId IN :lstEventID";
		
		private static final String FIND_LIST_SID_REGISTERED = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " JOIN WwfmtAppover d "
				+ "		ON m.wwfmtPsApprovalRootPK.approvalId = d.wwfmtAppoverPK.approvalId"
				+ "		AND d.employeeId = :sid"
				+ " WHERE m.startDate <= :baseDate"
				+ "	AND m.endDate >= :baseDate";
		
		private static final String FIND_LIST_APPOVER = "SELECT d FROM WwfmtAppover d"
				+ " JOIN WwfmtApprovalRoutePs m"
				+ "		ON m.wwfmtPsApprovalRootPK.approvalId = d.wwfmtAppoverPK.approvalId"
				+ " WHERE m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.startDate <= :baseDate"
				+ "	AND m.endDate >= :baseDate";
		
		private static final String GET_START_OF_LASTEST_HIST = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.sysAtr = :sysAtr"
				+ " AND m.endDate = :baseDate"
				+ " AND m.opeMode = 1"
				+ " ORDER BY m.startDate DESC";
		
		@Override
		public List<String> getListSidRegistered(String sid, GeneralDate baseDate) {
			return this.queryProxy().query(FIND_LIST_SID_REGISTERED, WwfmtApprovalRoutePs.class)
					.setParameter("sid", sid)
					.setParameter("baseDate", baseDate)
					.getList(item -> item.wwfmtPsApprovalRootPK.employeeId);
		}
		
		@Override
		public List<String> getListAppover(String sid, GeneralDate baseDate) {
			return this.queryProxy().query(FIND_LIST_APPOVER, WwfmtAppover.class)
					.setParameter("sid", sid)
					.setParameter("baseDate", baseDate)
					.getList(item -> item.employeeId);
		}
		
	/**
	 * get all Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	@Override
	public List<PersonApprovalRoot> getPsRootStart(String companyId, String employeeId, int sysAtr,
			List<Integer> lstAppType, List<Integer> lstNoticeID, List<String> lstEventID) {
		List<PersonApprovalRoot> lstPs = new ArrayList<>();
		if(sysAtr == SystemAtr.WORK.value){//就業
			lstPs.addAll(this.queryProxy().query(FIND_BY_ATR_WORK02, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.getList(c->toDomainPsApR(c)));
			if(!lstAppType.isEmpty()){
				lstPs.addAll(this.queryProxy().query(FIND_BY_ATR_WORK1, WwfmtApprovalRoutePs.class)
						.setParameter("companyId", companyId)
						.setParameter("employeeId", employeeId)
						.setParameter("lstAppType", lstAppType)
						.getList(c->toDomainPsApR(c)));
			}
		}else{//人事
			lstPs.addAll(this.queryProxy().query(FIND_BY_ATR_HR0, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.getList(c->toDomainPsApR(c)));
			if(!lstNoticeID.isEmpty()){
				lstPs.addAll(this.queryProxy().query(FIND_BY_ATR_HR4, WwfmtApprovalRoutePs.class)
						.setParameter("companyId", companyId)
						.setParameter("employeeId", employeeId)
						.setParameter("lstNoticeID", lstNoticeID)
						.getList(c->toDomainPsApR(c)));
			}
			if(!lstEventID.isEmpty()){
				lstPs.addAll(this.queryProxy().query(FIND_BY_ATR_HR5, WwfmtApprovalRoutePs.class)
						.setParameter("companyId", companyId)
						.setParameter("employeeId", employeeId)
						.setParameter("lstEventID", lstEventID)
						.getList(c->toDomainPsApR(c)));
			}
		}
		return lstPs;
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
		this.commandProxy().remove(WwfmtApprovalRoutePs.class,comPK);
		this.getEntityManager().flush();
	}
	/**
	 * add Person Approval Root
	 * @param psAppRoot
	 */
	@Override
	public void addPsApprovalRoot(PersonApprovalRoot psAppRoot) {
		this.commandProxy().insert(toEntityPsApR(psAppRoot));
		this.getEntityManager().flush();
	}
	/**
	 * add All Person Approval Root
	 * @param psAppRoot
	 */
	@Override
	public void addAllPsApprovalRoot(List<PersonApprovalRoot> psAppRoot) {
		List<WwfmtApprovalRoutePs> lstEntity = new ArrayList<>();
		for (PersonApprovalRoot ps : psAppRoot) {
			lstEntity.add(toEntityPsApR(ps));
		}
		this.commandProxy().insertAll(lstEntity);
		this.getEntityManager().flush();
	}
	/**
	 * update Person Approval Root
	 * @param psAppRoot
	 */
	@Override
	public void updatePsApprovalRoot(PersonApprovalRoot psAppRoot) {
		WwfmtApprovalRoutePs a = toEntityPsApR(psAppRoot);
		Optional<WwfmtApprovalRoutePs> optEntity = this.queryProxy().find(a.wwfmtPsApprovalRootPK, WwfmtApprovalRoutePs.class);
		optEntity.ifPresent(x -> {
			x.setStartDate(a.startDate);
			x.setEndDate(a.endDate);
			x.setApplicationType(a.applicationType);
			x.setConfirmationRootType(a.confirmationRootType);
			x.setEmploymentRootAtr(a.employmentRootAtr);
			this.commandProxy().update(x);
			this.getEntityManager().flush();
		});
	}
	/**
	 * update All Person Approval Root
	 * @param psAppRoot
	 */
	@Override
	public void updateAllPsApprovalRoot(List<PersonApprovalRoot> psAppRoot) {
		List<WwfmtApprovalRoutePs> lstEntity = new ArrayList<>();
		for (PersonApprovalRoot ps : psAppRoot) {
			WwfmtApprovalRoutePs a = toEntityPsApR(ps);
			WwfmtApprovalRoutePs x = this.queryProxy().find(a.wwfmtPsApprovalRootPK, WwfmtApprovalRoutePs.class).get();
			x.setStartDate(a.startDate);
			x.setEndDate(a.endDate);
			x.setApplicationType(a.applicationType);
			x.setConfirmationRootType(a.confirmationRootType);
			x.setEmploymentRootAtr(a.employmentRootAtr);
			lstEntity.add(x);
		}
		
		this.commandProxy().updateAll(lstEntity);
		this.getEntityManager().flush();
	}
	/**
	 * get Person Approval Root By End date
	 * @param companyId
	 * @param employeeId
	 * @param endDate
	 * @return
	 */
	@Override
	public List<PersonApprovalRoot> getPsApprovalRootByEdate(String companyId, String employeeId, GeneralDate endDate, 
			Integer applicationType, int employmentRootAtr, String id, int sysAtr) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_PS_APR_BY_ENDATE_CM_SYS, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("endDate", endDate)
					.setParameter("sysAtr", sysAtr)
					.getList(c->toDomainPsApR(c));
		}
		if(employmentRootAtr == 2){//confirm
			return this.queryProxy().query(SELECT_PS_APR_BY_ENDATE_CONFIRM, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("endDate", endDate)
					.setParameter("confirmationRootType", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainPsApR(c));
		}
		if(employmentRootAtr == 4){//notice
			return this.queryProxy().query(SELECT_PS_APR_BY_ENDATE_NOTICE, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("endDate", endDate)
					.setParameter("noticeId", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainPsApR(c));
		}
		if(employmentRootAtr == 5){//event
			return this.queryProxy().query(SELECT_PS_APR_BY_ENDATE_EVENT, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("endDate", endDate)
					.setParameter("busEventId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainPsApR(c));
		}
		//15 app type
		return this.queryProxy().query(SELECT_PS_APR_BY_ENDATE, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("endDate", endDate)
				.setParameter("applicationType", applicationType)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainPsApR(c));
	}
	/**
	 * get PsApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param employeeId
	 * @param historyId
	 * @return
	 */
	@Override
	public Optional<PersonApprovalRoot> getPsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId) {
		WwfmtPsApprovalRootPK pk = new WwfmtPsApprovalRootPK(companyId, approvalId, employeeId, historyId);
		return this.queryProxy().find(pk, WwfmtApprovalRoutePs.class).map(c->toDomainPsApR(c));
	}
	
	@Override
	public Optional<PersonApprovalRoot> findByBaseDate(String companyID, String employeeID, GeneralDate date, EmploymentRootAtr rootAtr,
			String targetType, int sysAtr) {
		String query = "";
		switch (rootAtr) {
		case APPLICATION:
			query = FIND_APPLICATION;
			break;
		case CONFIRMATION:
			query = FIND_CONFIRMATION;
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
		query = query.replaceAll("employeeID", employeeID);
		query = query.replaceAll("sysAtr", String.valueOf(sysAtr));
		query = query.replaceAll("date", date.toString("yyyy-MM-dd"));
		query = query.replaceAll("rootAtr", String.valueOf(rootAtr.value));
		query = query.replaceAll("targetType", targetType);
		try (PreparedStatement pstatement = this.connection().prepareStatement(query)) {
			return new NtsResultSet(pstatement.executeQuery())
			.getSingle(x -> convertNtsResult(x));
		} catch (Exception e) {
			throw new RuntimeException("PersonApprovalRoot error");
		}
	}
	
	@Override
	public Optional<PersonApprovalRoot> findByBaseDateOfCommon(String companyID, String employeeID, GeneralDate baseDate, int sysAtr) {
		String query = FIND_COMMON;
		query = query.replaceAll("companyID", companyID);
		query = query.replaceAll("employeeID", employeeID);
		query = query.replaceAll("sysAtr", String.valueOf(sysAtr));
		query = query.replaceAll("date", baseDate.toString("yyyy-MM-dd"));
		query = query.replaceAll("rootAtr", "0");
		try (PreparedStatement pstatement = this.connection().prepareStatement(query)) {
			return new NtsResultSet(pstatement.executeQuery())
			.getSingle(x -> convertNtsResult(x));
		} catch (Exception e) {
			throw new RuntimeException("PersonApprovalRoot error");
		}
	}
	
	private PersonApprovalRoot convertNtsResult(NtsResultRecord record) {
		return PersonApprovalRoot.createSimpleFromJavaType(
				record.getString("CID"), 
				record.getString("APPROVAL_ID"), 
				record.getString("SID"), 
				record.getString("HIST_ID"), 
				record.getInt("APP_TYPE"), 
				record.getGeneralDate("START_DATE").toString("yyyy-MM-dd"), 
				record.getGeneralDate("END_DATE").toString("yyyy-MM-dd"), 
				record.getInt("CONFIRMATION_ROOT_TYPE"), 
				record.getInt("EMPLOYMENT_ROOT_ATR"), 
				record.getInt("SYSTEM_ATR"), 
				record.getInt("NOTICE_ID"), 
				record.getString("BUS_EVENT_ID"));
	}
	
	
	/**
	 * convert entity WwfmtApprovalRoutePs to domain PersonApprovalRoot
	 * @param entity
	 * @return
	 */
	private PersonApprovalRoot toDomainPsApR(WwfmtApprovalRoutePs entity){
		val domain = PersonApprovalRoot.convert(entity.wwfmtPsApprovalRootPK.companyId,
				entity.wwfmtPsApprovalRootPK.approvalId,
				entity.wwfmtPsApprovalRootPK.employeeId,
				entity.wwfmtPsApprovalRootPK.historyId,
				entity.employmentRootAtr != 2 ? entity.applicationType : entity.confirmationRootType,
				entity.startDate,
				entity.endDate,
				entity.confirmationRootType,
				entity.employmentRootAtr,
				entity.sysAtr,
				entity.noticeId,
				entity.busEventId,
				entity.opeMode);
		return domain;
	}
	/**
	 * convert domain PersonApprovalRoot to entity WwfmtApprovalRoutePs
	 * @param domain
	 * @return
	 */
	private WwfmtApprovalRoutePs toEntityPsApR(PersonApprovalRoot domain){
		val entity = new WwfmtApprovalRoutePs();
		entity.wwfmtPsApprovalRootPK = new WwfmtPsApprovalRootPK(domain.getCompanyId(),
				domain.getApprRoot().getHistoryItems().isEmpty() ? "" : domain.getApprRoot().getHistoryItems().get(0).getApprovalId(),
				domain.getEmployeeId(), domain.getApprRoot().getHistoryItems().get(0).getHistoryId());
		entity.sysAtr = domain.getApprRoot().getSysAtr().value;
		entity.startDate = domain.getApprRoot().getHistoryItems().get(0).start();
		entity.endDate = domain.getApprRoot().getHistoryItems().get(0).end();
		entity.employmentRootAtr = domain.getApprRoot().getEmploymentRootAtr().value;
		entity.applicationType = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.APPLICATION) ?
				domain.getApprRoot().getApplicationType().map(x -> x.value).orElse(null) : null;
		entity.confirmationRootType = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.CONFIRMATION) ?
				domain.getApprRoot().getConfirmationRootType().map(x -> x.value).orElse(null) : null;
		entity.noticeId = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.NOTICE) ?
				domain.getApprRoot().getNoticeId().orElse(null) : null;
		entity.busEventId = domain.getApprRoot().getEmploymentRootAtr().equals(EmploymentRootAtr.BUS_EVENT) ?
				domain.getApprRoot().getBusEventId().orElse(null) : null;
		entity.opeMode = domain.getOperationMode().value;
		return entity;
	}
	
	/**
	 * Convert ApprovalPhase to entity WwfmtApprovalPhase and WwfmtAppover
	 */
	private WwfmtApprovalPhase toEntityAppPhase(ApprovalPhase domain) {
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
	
	private List<ApprovalSettingInformation> createApprovalSettingInformations(List<WwfmtApprovalRoutePs> rootEntities,
			List<WwfmtApprovalPhase> phaseEntities) {
		return rootEntities.stream()
				.map(root -> {
					List<WwfmtApprovalPhase> phases = phaseEntities.stream()
							.filter(x -> x.wwfmtApprovalPhasePK.approvalId.equals(root.wwfmtPsApprovalRootPK.approvalId))
							.collect(Collectors.toList());
					return this.createApprovalSettingInfoFromEntities(root, phases);
				})
				.collect(Collectors.toList());
	}
	
	private ApprovalSettingInformation createApprovalSettingInfoFromEntities(WwfmtApprovalRoutePs root, List<WwfmtApprovalPhase> phases) {
		List<ApprovalPhase> approvalPhases = phases.stream().map(this::toDomainApPhase).collect(Collectors.toList());
		return new ApprovalSettingInformation(approvalPhases, toDomainPsApR(root)) ;
	}
	
	private ApprovalPhase toDomainApPhase(WwfmtApprovalPhase entity){
		List<Approver> lstApprover = new ArrayList<>();
		for(WwfmtAppover approver: entity.wwfmtAppovers) {
			lstApprover.add(approver.toDomainApprover());
		}
		
		val domain = ApprovalPhase.createSimpleFromJavaType(
				entity.wwfmtApprovalPhasePK.approvalId,
				entity.wwfmtApprovalPhasePK.phaseOrder,
				entity.approvalForm,
				entity.browsingPhase,
				entity.approvalAtr,
				lstApprover);
		return domain;
	}
	
	@Override
	public List<PersonApprovalRoot> findAllByBaseDate(String companyId, GeneralDate baseDate, int sysAtr) {
		List<PersonApprovalRoot> data = this.queryProxy().query(FIND_ALL_BY_BASEDATE, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("baseDate", baseDate)
				.setParameter("sysAtr", sysAtr)
				.getList(c->toDomainPsApR(c));
		return data;
	}
	/**
	 * get Person Approval Root By type
	 * @param companyId
	 * @param employeeId
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	@Override
	public List<PersonApprovalRoot> getPsApprovalRootByType(String companyId, String employeeId,
			Integer applicationType, int employmentRootAtr, String id, int sysAtr) {
		//common
		if(employmentRootAtr == 0){
			return this.queryProxy().query(SELECT_PSAPR_BY_CM_SYS, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("sysAtr", sysAtr)
					.getList(c->toDomainPsApR(c));
		}
		//confirm
		if(employmentRootAtr == 2){
			return this.queryProxy().query(FIND_BY_CFR_TYPE, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("confirmationRootType", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainPsApR(c));
		}
		//notice
		if(employmentRootAtr == 4){
			return this.queryProxy().query(FIND_BY_NTR_TYPE, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("noticeId", applicationType)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainPsApR(c));
		}
		//event
		if(employmentRootAtr == 5){
			return this.queryProxy().query(FIND_BY_EVR_TYPE, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("busEventId", id)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c->toDomainPsApR(c));
		}
		//15 app type
		return this.queryProxy().query(FIND_BY_APP_TYPE, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("applicationType", applicationType)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c->toDomainPsApR(c));
	}
	@Override
	public List<PersonApprovalRoot> getPsAppRootLastest(String companyId, String employeeId, GeneralDate endDate, int sysAtr) {
		
		return this.queryProxy().query(FIND_PS_APP_LASTEST,WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("endDate", endDate)
				.setParameter("sysAtr", sysAtr)
				.getList(c -> toDomainPsApR(c));
	}
	@Override
	public List<PersonApprovalRoot> getPsAppRoot(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		if(confirmRootAtr==null){
			return this.queryProxy().query(FIND_BY_DATE_EMP, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyID)
					.setParameter("baseDate", date)
					.setParameter("employmentRootAtr", employmentRootAtr)
					.getList(c -> toDomainPsApR(c));
		}
		return this.queryProxy().query(FIND_BY_DATE_EMP_CONFIRM, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyID)
				.setParameter("baseDate", date)
				.setParameter("confirmationRootType", confirmRootAtr)
				.setParameter("employmentRootAtr", employmentRootAtr)
				.getList(c -> toDomainPsApR(c));
	}

	@Override
	public Optional<PersonApprovalRoot> getNewestCommonPsAppRoot(String companyId, String employeeId, int sysAtr){
		return this.queryProxy().query(FIND_COMMON_PS_APP_LASTEST, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("sysAtr", sysAtr)
				.getSingle(c->toDomainPsApR(c));
	}
	
	@Override
	public Optional<PersonApprovalRoot> getNewestMonthlyPsAppRoot(String companyId, String employeeId){
		return this.queryProxy().query(FIND_MONTHLY_PS_APP_LASTEST, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getSingle(c->toDomainPsApR(c));
	}

	@Override
	public List<PersonApprovalRoot> getPastHistory(String companyId, String employeeId){
		return this.queryProxy().query(FIND_PART_HISTORY, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getList(c->toDomainPsApR(c));
	}

	@Override
	public List<PersonApprovalRoot> getPsApprovalRootBySdate(String companyId, String employeeId,
			GeneralDate startDate, int sysAtr){
		return this.queryProxy().query(SELECT_PS_APR_BY_STARTDATE, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", startDate)
				.setParameter("sysAtr", sysAtr)
				.getList(c->toDomainPsApR(c));
	}
	
	@Override
	public Optional<PersonApprovalRoot> getHistLastestCom(String companyId, String employeeId) {
		List<PersonApprovalRoot> lst =  this.queryProxy().query(GET_ALL__MODE_COM, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getList(c->toDomainPsApR(c));
		return !lst.isEmpty()? Optional.of(lst.get(0)) : Optional.empty();
	}
	@Override
	public Optional<PersonApprovalRoot> getHistLastestPri(String companyId, String employeeId, int employmentRootAtr,
			Integer applicationType, String id, int sysAtr) {
		List<PersonApprovalRoot> lst = new ArrayList<>();
		if(employmentRootAtr == EmploymentRootAtr.COMMON.value){//common
			lst = this.queryProxy().query(GET_ALL_MODE_PRI_CM, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("sysAtr", sysAtr)
					.getList(c->toDomainPsApR(c));
		}else if(employmentRootAtr == EmploymentRootAtr.APPLICATION.value){//application
			lst = this.queryProxy().query(GET_ALL_MODE_PRI_AP, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("applicationType", applicationType)
					.getList(c->toDomainPsApR(c));
		}else if(employmentRootAtr == EmploymentRootAtr.CONFIRMATION.value){
			lst = this.queryProxy().query(GET_ALL_MODE_PRI_CF, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("confirmationRootType", applicationType)
					.getList(c->toDomainPsApR(c));
		}else if(employmentRootAtr == EmploymentRootAtr.NOTICE.value){
			lst = this.queryProxy().query(GET_ALL_MODE_PRI_NT, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("noticeId", applicationType)
					.getList(c->toDomainPsApR(c));
		}else{//event
			lst = this.queryProxy().query(GET_ALL_MODE_PRI_EV, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("employeeId", employeeId)
					.setParameter("busEventId", id)
					.getList(c->toDomainPsApR(c));
		}
		return !lst.isEmpty()? Optional.of(lst.get(0)) : Optional.empty();
	}
	@Override
	public List<PersonApprovalRoot> getByEndDate(String companyId, String employeeId, int sysAtr, GeneralDate endDate) {
		return this.queryProxy().query(FIND_BY_EDATE, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("sysAtr", sysAtr)
				.setParameter("endDate", endDate)
				.getList(c->toDomainPsApR(c));
	}
	@Override
	public List<PersonApprovalRoot> findByBaseDateJinji(String companyId, GeneralDate baseDate,
			List<Integer> lstNoticeID, List<String> lstEventID) {
		List<PersonApprovalRoot> lstResult = new ArrayList<>();
		lstResult.addAll(this.queryProxy().query(FIND_ALL_BY_BASEDATE_CM, WwfmtApprovalRoutePs.class)
				.setParameter("companyId", companyId)
				.setParameter("baseDate", baseDate)
				.getList(c->toDomainPsApR(c)));
		if(!lstNoticeID.isEmpty()) {
			lstResult.addAll(this.queryProxy().query(FIND_ALL_BY_BASEDATE_NT, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("baseDate", baseDate)
					.setParameter("lstNoticeID", lstNoticeID)
					.getList(c->toDomainPsApR(c)));
		}
		if(!lstEventID.isEmpty()) {
			lstResult.addAll(this.queryProxy().query(FIND_ALL_BY_BASEDATE_EV, WwfmtApprovalRoutePs.class)
					.setParameter("companyId", companyId)
					.setParameter("baseDate", baseDate)
					.setParameter("lstEventID", lstEventID)
					.getList(c->toDomainPsApR(c)));
		}
		return lstResult;
	}
	@Override
	public List<PersonApprovalRoot> getAppRootByDatePeriod(String cid, DatePeriod period, SystemAtr sysAtr,
			List<Integer> lstRootAtr) {
		String sql = "SELECT * "
				+ "FROM WWFMT_APPROVAL_ROUTE_PS WHERE CID = @companyID "
				+ "AND SYSTEM_ATR = @sysAtr AND START_DATE <= @eDate AND END_DATE >= @sDate "
				+ "AND EMPLOYMENT_ROOT_ATR IN @rootAtr";
		List<PersonApprovalRoot> lstResult = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", cid)
				.paramInt("sysAtr", sysAtr.value)
				.paramDate("sDate", period.start())
				.paramDate("eDate", period.end())
				.paramInt("rootAtr", lstRootAtr)
			.getList(x -> convertNtsResult(x));
		return lstResult;
	}

	@Override
	public List<PersonApprovalRoot> getPersonApprovalRoots(String cid, String sid, GeneralDate baseDate,
			List<ApplicationType> appTypes, List<ConfirmationRootType> confirmationRootTypes) {
		
		List<Integer> intAppTypes = appTypes.stream().map(x -> x.value).collect(Collectors.toList());
		List<Integer> intConfirmationRootTypes = confirmationRootTypes.stream().map(x -> x.value).collect(Collectors.toList());
		String queryString = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.startDate <= :baseDate"
				+ " AND m.endDate >= :baseDate"
				+ " AND m.sysAtr = 0"
				+ " AND m.opeMode = 1";
		
		List<WwfmtApprovalRoutePs> entities = this.queryProxy().query(queryString, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("baseDate", baseDate)
				.getList();
		
		return entities.stream()
				.filter(m -> (m.employmentRootAtr == 0)
						|| (m.employmentRootAtr == 1 && intAppTypes.contains(m.applicationType))
						|| (m.employmentRootAtr == 2 && intConfirmationRootTypes.contains(m.confirmationRootType)))
				.map(m -> toDomainPsApR(m))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalSettingInformation> getApprovalSettingByEmployees(String cid, List<String> sids, GeneralDate baseDate,
			SystemAtr sysAtr) {
		
		String rootQuery = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId IN :sids"
				+ " AND m.startDate <= :baseDate"
				+ " AND m.endDate >= :baseDate"
				+ " AND m.sysAtr = :sysAtr";
		String phaseQuery = "SELECT phase FROM WwfmtApprovalPhase phase"
				+ " WHERE phase.wwfmtApprovalPhasePK.approvalId IN :approvalIds";
		
		List<WwfmtApprovalRoutePs> rootEntities = this.queryProxy()
				.query(rootQuery, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sids", sids)
				.setParameter("baseDate", baseDate)
				.setParameter("sysAtr", sysAtr.value)
				.getList();
		if (rootEntities.isEmpty()) {
			return new ArrayList<ApprovalSettingInformation>();
		}
		
		List<String> approvalIds = rootEntities.stream()
				.map(x -> x.wwfmtPsApprovalRootPK.approvalId)
				.collect(Collectors.toList());
		List<WwfmtApprovalPhase> phaseEntities = this.queryProxy()
				.query(phaseQuery, WwfmtApprovalPhase.class)
				.setParameter("approvalIds", approvalIds)
				.getList();
		
		return this.createApprovalSettingInformations(rootEntities, phaseEntities);
	}
	
	@Override
	public List<PersonApprovalRoot> getPsRootBySidAndApprovals(String cid, String sid, List<String> approvalIds) {
		return this.queryProxy()
				.query(GET_PSROOT_BY_SID_AND_APPROVALS, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("approvalIds", approvalIds)
				.getList(this::toDomainPsApR);
	}

	@Override
	public List<PersonApprovalRoot> getHistWithStartAfterBaseDate(String cid, String sid, GeneralDate baseDate) {
		return this.queryProxy()
				.query(GET_HIST_WITH_START_AFTER_BASEDATE, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("baseDate", baseDate)
				.getList(this::toDomainPsApR);
	}

	@Override
	public void deleteHistFromApprovals(List<String> approvalIds) {
		String rootQuery = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.approvalId IN :approvalIds";
		String phaseQuery = "SELECT phase FROM WwfmtApprovalPhase phase"
				+ " WHERE phase.wwfmtApprovalPhasePK.approvalId IN :approvalIds";
		String approverQuery = "SELECT approver FROM WwfmtAppover approver"
				+ " WHERE approver.wwfmtAppoverPK.approvalId IN :approvalIds";
		
		List<WwfmtPsApprovalRootPK> rootEntitiePks = this.queryProxy()
				.query(rootQuery, WwfmtApprovalRoutePs.class)
				.setParameter("approvalIds", approvalIds)
				.getList(x -> x.wwfmtPsApprovalRootPK);
		List<WwfmtApprovalPhasePK> phaseEntitiePks = this.queryProxy()
				.query(phaseQuery, WwfmtApprovalPhase.class)
				.setParameter("approvalIds", approvalIds)
				.getList(x -> x.wwfmtApprovalPhasePK);
		List<WwfmtAppoverPK> approverEntitiePks = this.queryProxy()
				.query(approverQuery, WwfmtAppover.class)
				.setParameter("approvalIds", approvalIds)
				.getList(x -> x.wwfmtAppoverPK);
		this.commandProxy().removeAll(WwfmtApprovalRoutePs.class, rootEntitiePks);
		this.commandProxy().removeAll(WwfmtApprovalPhase.class, phaseEntitiePks);
		this.commandProxy().removeAll(WwfmtAppover.class, approverEntitiePks);
		this.getEntityManager().flush();
	}

	@Override
	public List<PersonApprovalRoot> getPreviousHistory(String cid, String sid, GeneralDate baseDate) {
		return this.queryProxy()
				.query(GET_PREVIOUS_HISTORY, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("baseDate", baseDate)
				.getList(this::toDomainPsApR);
	}

	@Override
	public List<PersonApprovalRoot> getHistWithEndAfterBaseDate(String cid, String sid, GeneralDate baseDate) {
		return this.queryProxy()
				.query(GET_HIST_WITH_END_AFTER_BASEDATE, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("baseDate", baseDate)
				.getList(this::toDomainPsApR);
	}

	@Override
	public List<ApprovalSettingInformation> getHistIncludeBaseDate(String cid, String sid, GeneralDate baseDate) {
		List<WwfmtApprovalRoutePs> rootEntities = this.queryProxy()
			.query(GET_HIST_WITH_START_AFTER_BASEDATE, WwfmtApprovalRoutePs.class)
			.setParameter("cid", cid)
			.setParameter("sid", sid)
			.setParameter("baseDate", baseDate)
			.getList();
		
		if (rootEntities.isEmpty()) {
			return new ArrayList<ApprovalSettingInformation>();
		}
		List<String> approvalIds = rootEntities.stream()
				.map(x -> x.wwfmtPsApprovalRootPK.approvalId)
				.collect(Collectors.toList());
		
		String phaseQuery = "SELECT phase FROM WwfmtApprovalPhase phase"
				+ " WHERE phase.wwfmtApprovalPhasePK.approvalId IN :approvalIds";
		List<WwfmtApprovalPhase> phaseEntities = this.queryProxy()
				.query(phaseQuery, WwfmtApprovalPhase.class)
				.setParameter("approvalIds", approvalIds)
				.getList();
		
		return this.createApprovalSettingInformations(rootEntities, phaseEntities);
	}

	
	@Override
	public List<PersonApprovalRoot> getEmpHistWithStartAfterBaseDate(String cid, List<String> sids,
			GeneralDate baseDate) {
		if (sids.isEmpty()) {
			return new ArrayList<PersonApprovalRoot>();
		}
		
		return this.queryProxy()
				.query(GET_EMP_HIST_WITH_START_AFTER_BASEDATE, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sids", sids)
				.setParameter("baseDate", baseDate)
				.getList(this::toDomainPsApR);
	}
	
	@Override
	public List<PersonApprovalRoot> getAllEmpHist(String cid, String sid, SystemAtr systemAtr) {
		return this.queryProxy()
				.query(GET_ALL_EMP_HIST, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("sysAtr", systemAtr.value)
				.getList(this::toDomainPsApR);
	}

	@Override
	public Optional<GeneralDate> getStartOfLastestHist(String cid, String sid) {
		 Optional<WwfmtApprovalRoutePs> entity = this.queryProxy()
				 .query(GET_START_OF_LASTEST_HIST, WwfmtApprovalRoutePs.class)
				 .setParameter("cid", cid)
				 .setParameter("sid", sid)
				 .setParameter("sysAtr", SystemAtr.WORK.value)
				 .setParameter("baseDate", GeneralDate.ymd(9999, 12, 31))
				 .getList()
				 .stream()
				 .findFirst();
		
		 if (entity.isPresent()) {
			 return Optional.ofNullable(entity.get().startDate);
		 }
		 return Optional.empty();
	}

	@Override
	public Optional<PersonApprovalRoot> getSmallestHistFromBaseDate(String cid, String sid, GeneralDate baseDate,
			List<ApplicationType> appTypes, List<ConfirmationRootType> confirmTypes) {
		List<Integer> intAppTypes = appTypes.stream().map(x -> x.value).collect(Collectors.toList());
		List<Integer> intConfirmationRootTypes = appTypes.stream().map(x -> x.value).collect(Collectors.toList());
		String queryString = "SELECT m FROM WwfmtApprovalRoutePs m"
				+ " WHERE m.wwfmtPsApprovalRootPK.companyId = :cid"
				+ " AND m.wwfmtPsApprovalRootPK.employeeId = :sid"
				+ " AND m.opeMode = 1"
				+ " AND m.sysAtr = 0"
				+ " AND m.startDate > :baseDate"
				+ " ORDER BY m.startDate ASC";
		List<WwfmtApprovalRoutePs> entities = this.queryProxy()
				.query(queryString, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("baseDate", baseDate)
				.getList();
		
		return entities.stream()
				.filter(m -> (m.employmentRootAtr == 0)
						|| (m.employmentRootAtr == 1 && intAppTypes.contains(m.applicationType))
						|| (m.employmentRootAtr == 2 && intConfirmationRootTypes.contains(m.confirmationRootType)))
				.map(this::toDomainPsApR)
				.findFirst();
	}

	@Override
	public List<PersonApprovalRoot> getHistFromBaseDate(String cid, String sid, DatePeriod period) {
		return this.queryProxy()
				.query(GET_HIST_FROM_BASEDATE, WwfmtApprovalRoutePs.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid)
				.setParameter("periodEnd", period.end())
				.setParameter("periodStart", period.start())
				.getList(this::toDomainPsApR);
	}

	@Override
	public void insertPersonApprovalRootAndPhases(PersonApprovalRoot personApprovalRoot,
			List<ApprovalPhase> approvalPhases) {
		WwfmtApprovalRoutePs rootEntitiy = this.toEntityPsApR(personApprovalRoot);
		List<WwfmtApprovalPhase> phaseEntities = approvalPhases.stream()
				.map(this::toEntityAppPhase)
				.collect(Collectors.toList());
		this.commandProxy().insert(rootEntitiy);
		this.commandProxy().insertAll(phaseEntities);
		
	}


	
}
