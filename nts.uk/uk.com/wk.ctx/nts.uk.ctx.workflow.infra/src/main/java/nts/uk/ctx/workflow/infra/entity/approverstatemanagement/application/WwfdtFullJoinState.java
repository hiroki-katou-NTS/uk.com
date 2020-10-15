package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;

@AllArgsConstructor
@Getter
public class WwfdtFullJoinState {
	private String rootStateID;
	private String employeeID;
	private GeneralDate recordDate;
	private Integer phaseOrder;
	private Integer approvalForm;
	private Integer appPhaseAtr;
	private Integer approverOrder;
	private String approverID;
	private Integer approvalAtr;
	private Integer confirmAtr;
	private String agentID;
	private GeneralDateTime approvalDate;
	private String approvalReason;
	private GeneralDate appDate;
	private Integer approverInListOrder;
	
	public static List<WwfdtFullJoinState> fromResultSet(ResultSet rs) {
		return new NtsResultSet(rs).getList(x -> {
			return new WwfdtFullJoinState(
					x.getString("ROOT_STATE_ID"), 
					x.getString("EMPLOYEE_ID"), 
					x.getGeneralDate("APPROVAL_RECORD_DATE"), 
					x.getInt("PHASE_ORDER"), 
					x.getInt("APPROVAL_FORM"), 
					x.getInt("APP_PHASE_ATR"), 
					x.getInt("APPROVER_ORDER"), 
					x.getString("APPROVER_ID"), 
					x.getInt("APPROVAL_ATR"), 
					x.getInt("CONFIRM_ATR"), 
					x.getString("AGENT_ID"), 
					x.getGeneralDateTime("APPROVAL_DATE"), 
					x.getString("APPROVAL_REASON"), 
					x.getGeneralDate("APP_DATE"),
					x.getInt("APPROVER_LIST_ORDER"));
		});
	}
	
	public static List<ApprovalRootState> toDomain(List<WwfdtFullJoinState> listFullData) {
		return listFullData.stream()
				.collect(Collectors.groupingBy(WwfdtFullJoinState::getRootStateID)).entrySet().stream().map(x -> {
					String rootStateID = x.getValue().get(0).getRootStateID();
					GeneralDate recordDate = x.getValue().get(0).getRecordDate();
					String employeeID = x.getValue().get(0).getEmployeeID();
					List<ApprovalPhaseState> listAppPhase = x.getValue().stream()
							.collect(Collectors.groupingBy(WwfdtFullJoinState::getPhaseOrder)).entrySet().stream()
							.map(y -> {
								List<ApprovalFrame> listAppFrame = y.getValue().stream()
										.collect(Collectors.groupingBy(WwfdtFullJoinState::getApproverOrder)).entrySet()
										.stream().map(z -> {
											List<ApproverInfor> listApprover = z.getValue().stream()
													.collect(Collectors
															.groupingBy(WwfdtFullJoinState::getApproverID))
													.entrySet().stream().map(t -> {
														return ApproverInfor.convert(
																t.getValue().get(0).getApproverID(), 
																t.getValue().get(0).getApprovalAtr(), 
																t.getValue().get(0).getAgentID(), 
																t.getValue().get(0).getApprovalDate(), 
																t.getValue().get(0).getApprovalReason(),
																t.getValue().get(0).getApproverInListOrder());
													}).sorted(Comparator.comparing(ApproverInfor::getApproverInListOrder))
													.collect(Collectors.toList());
											return ApprovalFrame.convert(
													z.getValue().get(0).getApproverOrder(), 
													z.getValue().get(0).getConfirmAtr(), 
													z.getValue().get(0).getAppDate(), 
													listApprover);
										}).collect(Collectors.toList());
								return ApprovalPhaseState.createFormTypeJava(
										y.getValue().get(0).getPhaseOrder(), 
										y.getValue().get(0).getAppPhaseAtr(), 
										y.getValue().get(0).getApprovalForm(), 
										listAppFrame);
							}).collect(Collectors.toList());
					return new ApprovalRootState(
							rootStateID, 
							RootType.EMPLOYMENT_APPLICATION, 
							recordDate,
							employeeID, 
							listAppPhase);
				}).collect(Collectors.toList());
	}
}
