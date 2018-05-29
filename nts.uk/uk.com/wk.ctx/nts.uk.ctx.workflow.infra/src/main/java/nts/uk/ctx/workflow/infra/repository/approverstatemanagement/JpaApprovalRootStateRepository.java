package nts.uk.ctx.workflow.infra.repository.approverstatemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdpApprovalFramePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdpApprovalPhaseStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdpApprovalRootStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdpApproverStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdtApprovalFrame;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdtApprovalPhaseState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdtApprovalRootState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application.WwfdtApproverState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdpApprovalFrameDayPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdpApprovalPhaseDayPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdpApprovalRootDayPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdpApproverDayPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdtApprovalFrameDay;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdtApprovalPhaseDay;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdtApprovalRootDay;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday.WwfdtApproverDay;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdpApprovalFrameMonthPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdpApprovalPhaseMonthPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdpApprovalRootMonthPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdpApproverMonthPK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdtApprovalFrameMonth;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdtApprovalPhaseMonth;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdtApprovalRootMonth;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth.WwfdtApproverMonth;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalRootStateRepository extends JpaRepository implements ApprovalRootStateRepository {

	private static final String SELECT_APP_BY_ID;
	private static final String SELECT_CF_DAY_BY_ID;
	private static final String SELECT_CF_MONTH_BY_ID;
	
	private static final String SELECT_APPS_BY_ID;
	
	private static final String SELECT_APP_BY_DATE;
	private static final String SELECT_CF_DAY_BY_DATE;
	private static final String SELECT_CF_MONTH_BY_DATE;
	
	private static final String SELECT_APP_BY_EMP_DATE;
	private static final String SELECT_CF_DAY_BY_EMP_DATE;
	private static final String SELECT_CF_MONTH_BY_EMP_DATE;
	
	private static final String SELECT_BY_LIST_EMP_DATE;
	
	private static final String SELECT_APPS_BY_EMP_AND_DATES;
	private static final String SELECT_CFS_DAY_BY_EMP_AND_DATES;
	private static final String SELECT_CFS_MONTH_BY_EMP_AND_DATES;

	private static final String SELECT_APPS_BY_APPROVER;
	private static final String SELECT_CFS_DAY_BY_APPROVER;
	private static final String SELECT_CFS_MONTH_BY_APPROVER;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.wwfdpApprovalRootStatePK.rootStateID = :rootStateID");
		SELECT_APP_BY_ID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootDay e");
		builderString.append(" WHERE e.wwfdpApprovalRootDayPK.rootStateID = :rootStateID");
		SELECT_CF_DAY_BY_ID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootMonth e");
		builderString.append(" WHERE e.wwfdpApprovalRootMonthPK.rootStateID = :rootStateID");
		SELECT_CF_MONTH_BY_ID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM WwfdtApprovalRootState c");
		builderString.append(" WHERE c.wwfdpApprovalRootStatePK.rootStateID IN ");
		builderString.append("(SELECT DISTINCT a.wwfdpApprovalRootStatePK.rootStateID");
		builderString.append(" FROM WwfdtAppRootStateSimple a");
		builderString.append(" JOIN WwfdtAppStateSimple b ");
		builderString.append(" ON a.wwfdpApprovalRootStatePK.rootStateID = b.wwfdpApproverStatePK.rootStateID");
		builderString.append(" WHERE (b.wwfdpApproverStatePK.approverID = :approverID");
		builderString.append(" OR b.wwfdpApproverStatePK.approverID IN");
		builderString.append(" (SELECT d.cmmmtAgentPK.employeeId FROM CmmmtAgent d WHERE d.agentSid1 = :approverID))");
		builderString.append(" AND a.wwfdpApprovalRootStatePK.rootStateID IN :rootStateIDs)");
		SELECT_APPS_BY_ID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		SELECT_APP_BY_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootDay e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		SELECT_CF_DAY_BY_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootMonth e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		SELECT_CF_MONTH_BY_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.employeeID = :employeeID");
		SELECT_APP_BY_EMP_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootDay e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.employeeID = :employeeID");
		SELECT_CF_DAY_BY_EMP_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootMonth e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.employeeID = :employeeID");
		SELECT_CF_MONTH_BY_EMP_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.rootType = :rootType");
		builderString.append(" AND e.employeeID IN :employeeID");
		SELECT_BY_LIST_EMP_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate IN :recordDate");
		builderString.append(" AND e.employeeID IN :employeeID");
		SELECT_APPS_BY_EMP_AND_DATES = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootDay e");
		builderString.append(" WHERE e.recordDate IN :recordDate");
		builderString.append(" AND e.employeeID IN :employeeID");
		SELECT_CFS_DAY_BY_EMP_AND_DATES = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootMonth e");
		builderString.append(" WHERE e.recordDate IN :recordDate");
		builderString.append(" AND e.employeeID IN :employeeID");
		SELECT_CFS_MONTH_BY_EMP_AND_DATES = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM WwfdtApprovalRootState c");
		builderString.append(" WHERE c.wwfdpApprovalRootStatePK.rootStateID IN ");
		builderString.append("(SELECT DISTINCT a.wwfdpApprovalRootStatePK.rootStateID");
		builderString.append(" FROM WwfdtAppRootStateSimple a JOIN WwfdtAppStateSimple b ");
		builderString.append(" ON a.wwfdpApprovalRootStatePK.rootStateID = b.wwfdpApproverStatePK.rootStateID ");
		builderString.append(" WHERE (b.wwfdpApproverStatePK.approverID = :approverID");
		builderString.append(" OR b.wwfdpApproverStatePK.approverID IN");
		builderString.append(" (SELECT d.cmmmtAgentPK.employeeId FROM CmmmtAgent d WHERE d.agentSid1 = :approverID))");
		builderString.append(" AND b.companyID = :companyID");
		builderString.append(" AND b.recordDate >= :startDate AND b.recordDate <= :endDate)");
		SELECT_APPS_BY_APPROVER = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM WwfdtApprovalRootDay c");
		builderString.append(" WHERE c.wwfdpApprovalRootDayPK.rootStateID IN ");
		builderString.append("(SELECT DISTINCT a.wwfdpApprovalRootDayPK.rootStateID");
		builderString.append(" FROM WwfdtAppRootDaySimple a JOIN WwfdtApproverDaySimple b ");
		builderString.append(" ON a.wwfdpApprovalRootDayPK.rootStateID = b.wwfdpApproverDayPK.rootStateID ");
		builderString.append(" WHERE (b.wwfdpApproverDayPK.approverID = :approverID");
		builderString.append(" OR b.wwfdpApproverDayPK.approverID IN");
		builderString.append(" (SELECT d.cmmmtAgentPK.employeeId FROM CmmmtAgent d WHERE d.agentSid1 = :approverID");
		builderString.append(" AND :startDate <= d.endDate AND :endDate >= d.startDate))");
		builderString.append(" AND b.companyID = :companyID");
		builderString.append(" AND b.recordDate >= :startDate AND b.recordDate <= :endDate)");
		SELECT_CFS_DAY_BY_APPROVER = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM WwfdtApprovalRootMonth c");
		builderString.append(" WHERE c.wwfdpApprovalRootMonthPK.rootStateID IN ");
		builderString.append("(SELECT DISTINCT a.wwfdpApprovalRootMonthPK.rootStateID");
		builderString.append(" FROM WwfdtApprovalRootMonth a JOIN WwfdtApproverMonth b ");
		builderString.append(" ON a.wwfdpApprovalRootMonthPK.rootStateID = b.wwfdpApproverMonthPK.rootStateID ");
		builderString.append(" WHERE (b.wwfdpApproverMonthPK.approverID = :approverID");
		builderString.append(" OR b.wwfdpApproverMonthPK.approverID IN");
		builderString.append(" (SELECT d.cmmmtAgentPK.employeeId FROM CmmmtAgent d WHERE d.agentSid1 = :approverID))");
		builderString.append(" AND b.companyID = :companyID");
		builderString.append(" AND b.recordDate >= :startDate AND b.recordDate <= :endDate)");
		SELECT_CFS_MONTH_BY_APPROVER = builderString.toString();
		
	}
	
	@Override
	public Optional<ApprovalRootState> findByID(String rootStateID, Integer rootType) {
		switch (rootType) {
		case 1:
			return this.queryProxy().query(SELECT_CF_DAY_BY_ID, WwfdtApprovalRootDay.class)
					.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
		case 2:
			return this.queryProxy().query(SELECT_CF_MONTH_BY_ID, WwfdtApprovalRootMonth.class)
					.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
		default:
			return this.queryProxy().query(SELECT_APP_BY_ID, WwfdtApprovalRootState.class)
					.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
		}
	}
	
	@Override
	public List<ApprovalRootState> findEmploymentApps(List<String> rootStateIDs, String approverID) {
		return this.queryProxy().query(SELECT_APPS_BY_ID, WwfdtApprovalRootState.class)
				.setParameter("rootStateIDs", rootStateIDs)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain());
	}
	
	@Override
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID) {
		return this.queryProxy().query(SELECT_APP_BY_ID, WwfdtApprovalRootState.class)
				.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
	}

	@Override
	public void insert(String companyID, ApprovalRootState approvalRootState, Integer rootType) {
		switch (rootType) {
		case 1:
			this.commandProxy().insert(WwfdtApprovalRootDay.fromDomain(companyID, approvalRootState));
			break;
		case 2:
			this.commandProxy().insert(WwfdtApprovalRootMonth.fromDomain(companyID, approvalRootState));
			break;
		default:
			this.commandProxy().insert(WwfdtApprovalRootState.fromDomain(companyID, approvalRootState));
		}
		this.getEntityManager().flush();
	}

	@Override
	public void update(ApprovalRootState approvalRootState, Integer rootType) {
		switch (rootType) {
		case 1:
			WwfdtApprovalRootDay wwfdtApprovalRootDay = this.queryProxy()
					.find(new WwfdpApprovalRootDayPK(approvalRootState.getRootStateID()), WwfdtApprovalRootDay.class).get();
			wwfdtApprovalRootDay.listWwfdtApprovalPhaseDay = approvalRootState.getListApprovalPhaseState().stream()
					.map(x -> updateEntityWwfdtApprovalPhaseDay(x)).collect(Collectors.toList());
			this.commandProxy().update(wwfdtApprovalRootDay);
			break;
		case 2:
			WwfdtApprovalRootMonth wwfdtApprovalRootMonth = this.queryProxy()
					.find(new WwfdpApprovalRootMonthPK(approvalRootState.getRootStateID()), WwfdtApprovalRootMonth.class).get();
			wwfdtApprovalRootMonth.listWwfdtApprovalPhaseMonth = approvalRootState.getListApprovalPhaseState().stream()
					.map(x -> updateEntityWwfdtApprovalPhaseMonth(x)).collect(Collectors.toList());
			this.commandProxy().update(wwfdtApprovalRootMonth);
			break;
		default:
			WwfdtApprovalRootState wwfdtApprovalRootState = this.queryProxy()
					.find(new WwfdpApprovalRootStatePK(approvalRootState.getRootStateID()), WwfdtApprovalRootState.class).get();
			wwfdtApprovalRootState.listWwfdtApprovalPhaseState = approvalRootState.getListApprovalPhaseState().stream()
					.map(x -> updateEntityWwfdtApprovalPhaseState(x)).collect(Collectors.toList());
			this.commandProxy().update(wwfdtApprovalRootState);
		}
		this.getEntityManager().flush();
	}

	@Override
	public void delete(String rootStateID, Integer rootType) {
		switch (rootType) {
		case 1:
			this.commandProxy().remove(WwfdtApprovalRootDay.class, new WwfdpApprovalRootDayPK(rootStateID));
			break;
		case 2:
			this.commandProxy().remove(WwfdtApprovalRootMonth.class, new WwfdpApprovalRootMonthPK(rootStateID));
			break;
		default:
			this.commandProxy().remove(WwfdtApprovalRootState.class, new WwfdpApprovalRootStatePK(rootStateID));
		}
	}
	
	private WwfdtApprovalPhaseState updateEntityWwfdtApprovalPhaseState(ApprovalPhaseState approvalPhaseState) {
		WwfdtApprovalPhaseState wwfdtApprovalPhaseState = this.queryProxy().find(
				new WwfdpApprovalPhaseStatePK(
						approvalPhaseState.getRootStateID(), 
						approvalPhaseState.getPhaseOrder()), WwfdtApprovalPhaseState.class).get();
		wwfdtApprovalPhaseState.approvalAtr = approvalPhaseState.getApprovalAtr().value;
		wwfdtApprovalPhaseState.approvalForm = approvalPhaseState.getApprovalForm().value;
		wwfdtApprovalPhaseState.listWwfdtApprovalFrame = approvalPhaseState.getListApprovalFrame().stream()
				.map(x -> updateEntityWwfdtApprovalFrame(x)).collect(Collectors.toList());
		return wwfdtApprovalPhaseState;
	}

	private WwfdtApprovalFrame updateEntityWwfdtApprovalFrame(ApprovalFrame approvalFrame){
		WwfdtApprovalFrame wwfdtApprovalFrame = this.queryProxy().find(
				new WwfdpApprovalFramePK(
						approvalFrame.getRootStateID(), 
						approvalFrame.getPhaseOrder(), 
						approvalFrame.getFrameOrder()), WwfdtApprovalFrame.class).get();
		wwfdtApprovalFrame.approvalAtr = approvalFrame.getApprovalAtr().value;
		wwfdtApprovalFrame.confirmAtr = approvalFrame.getConfirmAtr().value;
		wwfdtApprovalFrame.approverID = approvalFrame.getApproverID();
		wwfdtApprovalFrame.representerID = approvalFrame.getRepresenterID();
		wwfdtApprovalFrame.approvalDate = approvalFrame.getApprovalDate();
		wwfdtApprovalFrame.approvalReason = approvalFrame.getApprovalReason();
		wwfdtApprovalFrame.listWwfdtApproverState = approvalFrame.getListApproverState().stream()
				.map(x -> updateEntityWwfdtApproverState(x)).collect(Collectors.toList());
		return wwfdtApprovalFrame;
	}

	private WwfdtApproverState updateEntityWwfdtApproverState(ApproverState approverState) {
		WwfdtApproverState wwfdtApproverState = this.queryProxy().find(
				new WwfdpApproverStatePK(
						approverState.getRootStateID(), 
						approverState.getPhaseOrder(), 
						approverState.getFrameOrder(), 
						approverState.getApproverID()), WwfdtApproverState.class).get();
		return wwfdtApproverState;
	}
	
	private WwfdtApprovalPhaseDay updateEntityWwfdtApprovalPhaseDay(ApprovalPhaseState approvalPhaseState) {
		WwfdtApprovalPhaseDay wwfdtApprovalPhaseDay = this.queryProxy().find(
				new WwfdpApprovalPhaseDayPK(
						approvalPhaseState.getRootStateID(), 
						approvalPhaseState.getPhaseOrder()), WwfdtApprovalPhaseDay.class).get();
		wwfdtApprovalPhaseDay.approvalAtr = approvalPhaseState.getApprovalAtr().value;
		wwfdtApprovalPhaseDay.approvalForm = approvalPhaseState.getApprovalForm().value;
		wwfdtApprovalPhaseDay.listWwfdtApprovalFrameDay = approvalPhaseState.getListApprovalFrame().stream()
				.map(x -> updateEntityWwfdtApprovalFrameDay(x)).collect(Collectors.toList());
		return wwfdtApprovalPhaseDay;
	}

	private WwfdtApprovalFrameDay updateEntityWwfdtApprovalFrameDay(ApprovalFrame approvalFrame){
		WwfdtApprovalFrameDay wwfdtApprovalFrameDay = this.queryProxy().find(
				new WwfdpApprovalFrameDayPK(
						approvalFrame.getRootStateID(), 
						approvalFrame.getPhaseOrder(), 
						approvalFrame.getFrameOrder()), WwfdtApprovalFrameDay.class).get();
		wwfdtApprovalFrameDay.approvalAtr = approvalFrame.getApprovalAtr().value;
		wwfdtApprovalFrameDay.confirmAtr = approvalFrame.getConfirmAtr().value;
		wwfdtApprovalFrameDay.approverID = approvalFrame.getApproverID();
		wwfdtApprovalFrameDay.representerID = approvalFrame.getRepresenterID();
		wwfdtApprovalFrameDay.approvalDate = approvalFrame.getApprovalDate();
		wwfdtApprovalFrameDay.approvalReason = approvalFrame.getApprovalReason();
		wwfdtApprovalFrameDay.listWwfdtApproverDay = approvalFrame.getListApproverState().stream()
				.map(x -> updateEntityWwfdtApproverDay(x)).collect(Collectors.toList());
		return wwfdtApprovalFrameDay;
	}

	private WwfdtApproverDay updateEntityWwfdtApproverDay(ApproverState approverState) {
		WwfdtApproverDay wwfdtApproverDay = this.queryProxy().find(
				new WwfdpApproverDayPK(
						approverState.getRootStateID(), 
						approverState.getPhaseOrder(), 
						approverState.getFrameOrder(), 
						approverState.getApproverID()), WwfdtApproverDay.class).get();
		return wwfdtApproverDay;
	}
	
	private WwfdtApprovalPhaseMonth updateEntityWwfdtApprovalPhaseMonth(ApprovalPhaseState approvalPhaseState) {
		WwfdtApprovalPhaseMonth wwfdtApprovalPhaseMonth = this.queryProxy().find(
				new WwfdpApprovalPhaseMonthPK(
						approvalPhaseState.getRootStateID(), 
						approvalPhaseState.getPhaseOrder()), WwfdtApprovalPhaseMonth.class).get();
		wwfdtApprovalPhaseMonth.approvalAtr = approvalPhaseState.getApprovalAtr().value;
		wwfdtApprovalPhaseMonth.approvalForm = approvalPhaseState.getApprovalForm().value;
		wwfdtApprovalPhaseMonth.listWwfdtApprovalFrameMonth = approvalPhaseState.getListApprovalFrame().stream()
				.map(x -> updateEntityWwfdtApprovalFrameMonth(x)).collect(Collectors.toList());
		return wwfdtApprovalPhaseMonth;
	}

	private WwfdtApprovalFrameMonth updateEntityWwfdtApprovalFrameMonth(ApprovalFrame approvalFrame){
		WwfdtApprovalFrameMonth wwfdtApprovalFrameMonth = this.queryProxy().find(
				new WwfdpApprovalFrameMonthPK(
						approvalFrame.getRootStateID(), 
						approvalFrame.getPhaseOrder(), 
						approvalFrame.getFrameOrder()), WwfdtApprovalFrameMonth.class).get();
		wwfdtApprovalFrameMonth.approvalAtr = approvalFrame.getApprovalAtr().value;
		wwfdtApprovalFrameMonth.confirmAtr = approvalFrame.getConfirmAtr().value;
		wwfdtApprovalFrameMonth.approverID = approvalFrame.getApproverID();
		wwfdtApprovalFrameMonth.representerID = approvalFrame.getRepresenterID();
		wwfdtApprovalFrameMonth.approvalDate = approvalFrame.getApprovalDate();
		wwfdtApprovalFrameMonth.approvalReason = approvalFrame.getApprovalReason();
		wwfdtApprovalFrameMonth.listWwfdtApproverMonth = approvalFrame.getListApproverState().stream()
				.map(x -> updateEntityWwfdtApproverMonth(x)).collect(Collectors.toList());
		return wwfdtApprovalFrameMonth;
	}

	private WwfdtApproverMonth updateEntityWwfdtApproverMonth(ApproverState approverState) {
		WwfdtApproverMonth wwfdtApproverMonth = this.queryProxy().find(
				new WwfdpApproverMonthPK(
						approverState.getRootStateID(), 
						approverState.getPhaseOrder(), 
						approverState.getFrameOrder(), 
						approverState.getApproverID()), WwfdtApproverMonth.class).get();
		return wwfdtApproverMonth;
	}

	@Override
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDate(GeneralDate startDate, GeneralDate endDate,
			String approverID, Integer rootType) {
		List<ApprovalRootState> result  = new ArrayList<>();
		switch (rootType) {
		case 1:
			result = this.queryProxy().query(SELECT_CF_DAY_BY_DATE, WwfdtApprovalRootDay.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(x -> x.toDomain());
			break;
		case 2:
			result = this.queryProxy().query(SELECT_CF_MONTH_BY_DATE, WwfdtApprovalRootMonth.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(x -> x.toDomain());
			break;
		default:
			result = this.queryProxy().query(SELECT_APP_BY_DATE, WwfdtApprovalRootState.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(x -> x.toDomain());
		}
		return result;
	}

	@Override
	public List<ApprovalRootState> findAppByEmployeeIDRecordDate(GeneralDate startDate, GeneralDate endDate,
			String employeeID, Integer rootType) {
		switch (rootType) {
		case 1:
			return this.queryProxy().query(SELECT_CF_DAY_BY_EMP_DATE, WwfdtApprovalRootDay.class)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("employeeID", employeeID).getList(x -> x.toDomain());
		case 2:
			return this.queryProxy().query(SELECT_CF_MONTH_BY_EMP_DATE, WwfdtApprovalRootMonth.class)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("employeeID", employeeID).getList(x -> x.toDomain());
		default:
			return this.queryProxy().query(SELECT_APP_BY_EMP_DATE, WwfdtApprovalRootState.class)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("employeeID", employeeID).getList(x -> x.toDomain());
		}
	}

	@Override
	public List<ApprovalRootState> getRootStateByDateAndType(GeneralDate date, Integer rootType) {
		switch (rootType) {
		case 1:
			return this.queryProxy().query(SELECT_CF_DAY_BY_DATE, WwfdtApprovalRootDay.class)
					.setParameter("startDate", date)
					.setParameter("endDate", date).getList(x -> x.toDomain());
		case 2:
			return this.queryProxy().query(SELECT_CF_MONTH_BY_DATE, WwfdtApprovalRootMonth.class)
					.setParameter("startDate", date)
					.setParameter("endDate", date).getList(x -> x.toDomain());
		default:
			return this.queryProxy().query(SELECT_APP_BY_DATE, WwfdtApprovalRootState.class)
					.setParameter("startDate", date)
					.setParameter("endDate", date).getList(x -> x.toDomain());
		}
	}

	@Override
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDateAndNoRootType(String companyID, 
			GeneralDate startDate, GeneralDate endDate, String approverID) {
		List<ApprovalRootState> result  = new ArrayList<>();
		result.addAll(this.queryProxy().query(SELECT_APPS_BY_APPROVER, WwfdtApprovalRootState.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain()));
		result.addAll(this.queryProxy().query(SELECT_CFS_DAY_BY_APPROVER, WwfdtApprovalRootDay.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain()));
		result.addAll(this.queryProxy().query(SELECT_CFS_MONTH_BY_APPROVER, WwfdtApprovalRootMonth.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain()));
		return result;
	}

	@Override
	public List<ApprovalRootState> findAppByListEmployeeIDRecordDate(GeneralDate startDate, GeneralDate endDate,
			List<String> employeeIDs, Integer rootType) {
		return this.queryProxy().query(SELECT_BY_LIST_EMP_DATE, WwfdtApprovalRootState.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("rootType", rootType)
				.setParameter("employeeID", employeeIDs).getList(x -> x.toDomain());
	}

	@Override
	public List<ApprovalRootState> findAppByListEmployeeIDAndListRecordDate(List<GeneralDate> approvalRecordDates,
			List<String> employeeIDs, Integer rootType) {
		switch (rootType) {
		case 1:
			return this.queryProxy().query(SELECT_CFS_DAY_BY_EMP_AND_DATES, WwfdtApprovalRootDay.class)
				.setParameter("recordDate", approvalRecordDates)
				.setParameter("employeeID", employeeIDs)
				.getList(x -> x.toDomain());
		case 2:
			return this.queryProxy().query(SELECT_CFS_MONTH_BY_EMP_AND_DATES, WwfdtApprovalRootMonth.class)
					.setParameter("recordDate", approvalRecordDates)
					.setParameter("employeeID", employeeIDs)
					.getList(x -> x.toDomain());
		default:
			return this.queryProxy().query(SELECT_APPS_BY_EMP_AND_DATES, WwfdtApprovalRootState.class)
					.setParameter("recordDate", approvalRecordDates)
					.setParameter("employeeID", employeeIDs)
					.getList(x -> x.toDomain());
		}
	}

	@Override
	public List<ApprovalRootState> findByApprover(String companyID, GeneralDate startDate, GeneralDate endDate, 
			String approverID, Integer rootType) {
		List<ApprovalRootState> result  = new ArrayList<>();
		switch (rootType) {
		case 1:
			result = this.queryProxy().query(SELECT_CFS_DAY_BY_APPROVER, WwfdtApprovalRootDay.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain());
			break;
		case 2:
			result = this.queryProxy().query(SELECT_CFS_MONTH_BY_APPROVER, WwfdtApprovalRootMonth.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain());
			break;
		default:
			result = this.queryProxy().query(SELECT_APPS_BY_APPROVER, WwfdtApprovalRootState.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("approverID", approverID)
				.getList(x -> x.toDomain());
		}
		return result;
	}

	@Override
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDateNew(GeneralDate startDate, GeneralDate endDate,
			Integer rootType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteConfirmDay(String employeeID, GeneralDate date) {
		List<WwfdpApprovalRootDayPK> rootDayKeyList = this.queryProxy().query(SELECT_CF_DAY_BY_EMP_DATE, WwfdtApprovalRootDay.class)
				.setParameter("startDate", date)
				.setParameter("endDate", date)
				.setParameter("employeeID", employeeID)
				.getList(x -> new WwfdpApprovalRootDayPK(x.wwfdpApprovalRootDayPK.rootStateID));
		this.commandProxy().removeAll(WwfdtApprovalRootDay.class, rootDayKeyList);
		this.getEntityManager().flush();
	}
}
