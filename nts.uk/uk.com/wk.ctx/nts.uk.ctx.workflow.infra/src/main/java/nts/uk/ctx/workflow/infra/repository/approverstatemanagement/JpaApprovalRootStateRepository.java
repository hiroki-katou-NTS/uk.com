package nts.uk.ctx.workflow.infra.repository.approverstatemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApprovalFramePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApprovalPhaseStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApprovalRootStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApproverStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalFrame;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalFrameNew;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalPhaseState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalPhaseStateNew;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalRootState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalRootStateNew;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApproverState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApproverStateNew;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalRootStateRepository extends JpaRepository implements ApprovalRootStateRepository {

	private static final String SELECT_BY_ID;

	private static final String SELECT_TYPE_APP;
	
	private static final String SELECT_BY_TYPE_APPS;
	
	private static final String SELECT_BY_DATE;
	
	private static final String SELECT_BY_EMP_DATE;
	
	private static final String SELECT_BY_DATE_NO_ROOTYPE;  

	private static final String SELECT_BY_DATE_AND_TYPE;
	
	private static final String SELECT_BY_LIST_EMP_DATE;
	
	private static final String SELECT_BY_LIST_EMP_AND_DATES;
	
	private static final String SELECT_APPROVALNEW_BY_DATE;
	
	private static final String SELECT_PHASE;
	
	private static final String SELECT_FRAME;
	
	private static final String SELECT_APPROVER_STATE;
	
	private static final String SELECT_CF_DAY_BY_EMP_DATE;

	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.wwfdpApprovalRootStatePK.rootStateID = :rootStateID");
		SELECT_BY_ID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT_BY_ID);
		builderString.append(" AND e.rootType = 0");
		SELECT_TYPE_APP = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.rootType = 0");
		builderString.append(" AND e.wwfdpApprovalRootStatePK.rootStateID IN :rootStateIDs");
		SELECT_BY_TYPE_APPS = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.rootType = :rootType");
		SELECT_BY_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		SELECT_BY_DATE_NO_ROOTYPE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.rootType = :rootType");
		builderString.append(" AND e.employeeID = :employeeID");
		SELECT_BY_EMP_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootStateNew e");
		builderString.append(" WHERE e.recordDate = :recordDate");
		builderString.append(" AND e.rootType = 1");
		builderString.append(" AND e.employeeID = :employeeID");
		SELECT_CF_DAY_BY_EMP_DATE = builderString.toString();
		
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
		builderString.append(" WHERE e.recordDate = :recordDate");
		builderString.append(" AND e.rootType = :rootType");
		SELECT_BY_DATE_AND_TYPE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.recordDate IN :recordDate");
		builderString.append(" AND e.rootType = :rootType");
		builderString.append(" AND e.employeeID IN :employeeID");
		SELECT_BY_LIST_EMP_AND_DATES = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT DISTINCT e");
		builderString.append(" FROM WwfdtApprovalRootStateNew e");
		builderString.append(" WHERE e.recordDate >= :startDate");
		builderString.append(" AND e.recordDate <= :endDate");
		builderString.append(" AND e.rootType = :rootType");
		SELECT_APPROVALNEW_BY_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT DISTINCT e");
		builderString.append(" FROM WwfdtApprovalPhaseStateNew e");
		builderString.append(" WHERE e.wwfdpApprovalPhaseStatePK.rootStateID IN :rootStateIDs");
		SELECT_PHASE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT DISTINCT e");
		builderString.append(" FROM WwfdtApprovalFrameNew e");
		builderString.append(" WHERE e.wwfdpApprovalFramePK.rootStateID IN :rootStateIDs");
		SELECT_FRAME = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT DISTINCT e");
		builderString.append(" FROM WwfdtApproverStateNew e");
		builderString.append(" WHERE e.wwfdpApproverStatePK.rootStateID IN :rootStateIDs");
		SELECT_APPROVER_STATE = builderString.toString();
		
	}
	
	@Override
	public Optional<ApprovalRootState> findByID(String rootStateID) {
		return this.queryProxy().query(SELECT_BY_ID, WwfdtApprovalRootState.class)
				.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
	}
	
	@Override
	public List<ApprovalRootState> findEmploymentApps(List<String> rootStateIDs) {
		return this.queryProxy().query(SELECT_BY_TYPE_APPS, WwfdtApprovalRootState.class)
				.setParameter("rootStateIDs", rootStateIDs).getList(x -> x.toDomain());
	}
	
	@Override
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID) {
		return this.queryProxy().query(SELECT_TYPE_APP, WwfdtApprovalRootState.class)
				.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
	}

	@Override
	public void insert(ApprovalRootState approvalRootState) {
		this.commandProxy().insert(WwfdtApprovalRootState.fromDomain(approvalRootState));
		this.getEntityManager().flush();
	}

	@Override
	public void update(ApprovalRootState approvalRootState) {
		WwfdtApprovalRootState wwfdtApprovalRootState = this.queryProxy()
				.find(new WwfdpApprovalRootStatePK(approvalRootState.getRootStateID()), WwfdtApprovalRootState.class).get();
		wwfdtApprovalRootState.listWwfdtApprovalPhaseState = approvalRootState.getListApprovalPhaseState().stream()
				.map(x -> updateEntityWwfdtApprovalPhaseState(x)).collect(Collectors.toList());
		this.commandProxy().update(wwfdtApprovalRootState);
		this.getEntityManager().flush();
	}

	@Override
	public void delete(String rootStateID) {
		this.commandProxy().remove(WwfdtApprovalRootState.class, new WwfdpApprovalRootStatePK(rootStateID));
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

	@Override
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDate(GeneralDate startDate, GeneralDate endDate,
			String approverID,Integer rootType) {
		List<ApprovalRootState> result  = new ArrayList<>();
		List<WwfdtApprovalRootState> aa = this.queryProxy().query(SELECT_BY_DATE, WwfdtApprovalRootState.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("rootType", rootType).getList();
		result = aa.stream().map(x -> x.toDomain()).collect(Collectors.toList());
		return result;
	}

	@Override
	public List<ApprovalRootState> findAppByEmployeeIDRecordDate(GeneralDate startDate, GeneralDate endDate,
			String employeeID, Integer rootType) {
		return this.queryProxy().query(SELECT_BY_EMP_DATE, WwfdtApprovalRootState.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("rootType", rootType)
				.setParameter("employeeID", employeeID).getList(x -> x.toDomain());
	}

	@Override
	public List<ApprovalRootState> getRootStateByDateAndType(GeneralDate date, Integer rootType) {
		return this.queryProxy().query(SELECT_BY_DATE_AND_TYPE, WwfdtApprovalRootState.class)
				.setParameter("recordDate", date)
				.setParameter("rootType", rootType)
				.getList(x -> x.toDomain());
	}

	@Override
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDateAndNoRootType(GeneralDate startDate,
			GeneralDate endDate, String approverID) {
		List<ApprovalRootState> result  = new ArrayList<>();
		result = this.queryProxy()
				.query(SELECT_BY_DATE_NO_ROOTYPE, WwfdtApprovalRootState.class).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList(x -> x.toDomain());
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
		return this.queryProxy().query(SELECT_BY_LIST_EMP_AND_DATES, WwfdtApprovalRootState.class)
				.setParameter("recordDate", approvalRecordDates)
				.setParameter("rootType", rootType)
				.setParameter("employeeID", employeeIDs).getList(x -> x.toDomain());
	}

	@Override
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDateNew(GeneralDate startDate, GeneralDate endDate,
			Integer rootType) {
		List<ApprovalRootState> result  = new ArrayList<>();
		result = this.queryProxy().query(SELECT_APPROVALNEW_BY_DATE, WwfdtApprovalRootStateNew.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("rootType", rootType).getList(x -> x.toDomain());
		
		List<String> rootStateIDs = result.stream().map(x -> x.getRootStateID()).collect(Collectors.toList());
		if(rootStateIDs != null){
			List<ApprovalPhaseState>  approvalPhases =  new ArrayList<>();
			CollectionUtil.split(rootStateIDs, 1000, subList -> {
				approvalPhases.addAll(this.queryProxy().query(SELECT_PHASE, WwfdtApprovalPhaseStateNew.class)
					.setParameter("rootStateIDs", subList).getList(x -> x.toDomain()));
				});
			List<ApprovalFrame>  approvalFrames = new ArrayList<>();
			CollectionUtil.split(rootStateIDs, 1000, subList -> {
				approvalFrames.addAll(this.queryProxy().query(SELECT_FRAME, WwfdtApprovalFrameNew.class)
					.setParameter("rootStateIDs", subList).getList(x -> x.toDomain()));
				});
			List<ApproverState>  approverStates = new ArrayList<>();
			CollectionUtil.split(rootStateIDs, 1000, subList -> {
				approverStates.addAll(this.queryProxy().query(SELECT_APPROVER_STATE, WwfdtApproverStateNew.class)
					.setParameter("rootStateIDs", subList).getList(x -> x.toDomain()));
				});
			for(ApprovalFrame frame : approvalFrames){
				List<ApproverState> listApproverState = new ArrayList<>();
				for(ApproverState approver : approverStates){
					if(frame.getRootStateID().equals(approver.getRootStateID()) 
							&& frame.getPhaseOrder().equals(approver.getPhaseOrder()) 
							&& frame.getFrameOrder().equals(approver.getFrameOrder())){
						listApproverState.add(approver);
					}
				}
				frame.setListApproverState(listApproverState);
			}
			for(ApprovalPhaseState approvalPhase : approvalPhases){
				List<ApprovalFrame> frames = new ArrayList<>();
				for(ApprovalFrame frame : approvalFrames){
					if(approvalPhase.getRootStateID().equals(frame.getRootStateID()) 
							&& approvalPhase.getPhaseOrder().equals(frame.getPhaseOrder())){
						frames.add(frame);
					}
				}
				approvalPhase.setListApprovalFrame(frames);
			}
			for(ApprovalRootState approvalRoot : result){
				List<ApprovalPhaseState> approvalPhaseStates = new ArrayList<>();
				for(ApprovalPhaseState approvalPhase : approvalPhases){
					if(approvalRoot.getRootStateID().equals(approvalPhase.getRootStateID())){
						approvalPhaseStates.add(approvalPhase);
					}
				}
				approvalRoot.setListApprovalPhaseState(approvalPhaseStates);
			}
		}
		return result;
	}

	@Override
	public void deleteConfirmDay(String employeeID, GeneralDate date) {
		List<WwfdpApprovalRootStatePK> rootStateKeyList = this.queryProxy().query(SELECT_CF_DAY_BY_EMP_DATE, WwfdtApprovalRootStateNew.class)
					.setParameter("recordDate", date)
					.setParameter("employeeID", employeeID)
					.getList(x -> new WwfdpApprovalRootStatePK(x.wwfdpApprovalRootStatePK.rootStateID));
		this.commandProxy().removeAll(WwfdtApprovalRootState.class, rootStateKeyList);
		this.getEntityManager().flush();
	}
}
