package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
/**
 * 承認フェーズインスタンス
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApprovalPhaseState extends DomainObject {
	/**承認フェーズNo*/
	private Integer phaseOrder;
	/**承認区分*/
	@Setter
	private ApprovalBehaviorAtr approvalAtr;
	/**承認形態*/
	private ApprovalForm approvalForm;
	/**承認枠*/
	@Setter
	private List<ApprovalFrame> listApprovalFrame;
	
	public static ApprovalPhaseState createFromFirst(GeneralDate date, 
			ApprovalPhaseState approvalPhaseState){
			return ApprovalPhaseState.builder()
					.phaseOrder(approvalPhaseState.getPhaseOrder())
					.approvalAtr(approvalPhaseState.getApprovalAtr())
					.approvalForm(approvalPhaseState.getApprovalForm())
					.listApprovalFrame(approvalPhaseState.getListApprovalFrame().stream()
							.map(x -> ApprovalFrame.createFromFirst(date, x)).collect(Collectors.toList()))
					.build();
	}
	public static ApprovalPhaseState createFormTypeJava(Integer phaseOrder,
			int approvalAtr,int approvalForm, List<ApprovalFrame> listApprovalFrame){
		return new ApprovalPhaseState(phaseOrder,
				EnumAdaptor.valueOf(approvalAtr, ApprovalBehaviorAtr.class),
				EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
				listApprovalFrame);
	}
	
//	public boolean canRelease(String approverId) {
//		return hasApprovedBy(approverId) || isRepresenter(approverId);
//	}
	
	public boolean hasApprovedBy(String approverId) {
		return hasApproved() && isApprover(approverId);
	}
	
	private boolean hasApproved() {
		return approvalAtr == ApprovalBehaviorAtr.APPROVED;
	}
	
	private boolean isApprover(String employeeId) {
		return listApprovalFrame.stream()
				.anyMatch(f -> f.isApprover(employeeId));
	}
	
	public Optional<ApproverInfor> getNotApproved() {
		for(ApprovalFrame approvalFrame : listApprovalFrame) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.isNotApproved()) {
					return Optional.of(approverInfor);
				}
			}
		}
		return Optional.empty();
	}
	
	public Optional<ApproverInfor> getApproved() {
		for(ApprovalFrame approvalFrame : listApprovalFrame) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.isApproved()) {
					return Optional.of(approverInfor);
				}
			}
		}
		return Optional.empty();
	}
	
	public Optional<ApproverInfor> getNotUnApproved() {
		for(ApprovalFrame approvalFrame : listApprovalFrame) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.isNotUnApproved()) {
					return Optional.of(approverInfor);
				}
			}
		}
		return Optional.empty();
	}
	
//	private boolean isRepresenter(String employeeId) {
//		return listApprovalFrame.stream()
//				.anyMatch(f -> f.isRepresenter(employeeId));
//	}
}
