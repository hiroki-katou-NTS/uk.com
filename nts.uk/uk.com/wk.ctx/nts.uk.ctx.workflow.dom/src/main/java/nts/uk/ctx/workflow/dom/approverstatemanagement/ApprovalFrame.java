package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;
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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
/**
 * 承認枠
 * @author hoatt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApprovalFrame extends DomainObject {
	/**承認枠No*/
	private int frameOrder;
	/**確定区分*/
	private ConfirmPerson confirmAtr;
	/**対象日*/
	private GeneralDate appDate;
	/**承認者情報*/
	@Setter
	private List<ApproverInfor> lstApproverInfo;
	
	public static ApprovalFrame firstCreate(String rootStateID, Integer phaseOrder, Integer frameOrder,
			ConfirmPerson confirmPerson, GeneralDate appDate, List<ApproverInfor> listApproverInfor){
		return ApprovalFrame.builder()
				.frameOrder(frameOrder)
				.confirmAtr(confirmPerson)
				.appDate(appDate)
				.lstApproverInfo(listApproverInfor)
				.build();
	}
	
	public static ApprovalFrame createFromFirst(GeneralDate date, ApprovalFrame approvalFrame){
			return ApprovalFrame.builder()
					.frameOrder(approvalFrame.getFrameOrder())
					.confirmAtr(approvalFrame.getConfirmAtr())
					.appDate(date)
					.lstApproverInfo(approvalFrame.getLstApproverInfo().stream()
							.map(x -> ApproverInfor.createFromFirst(x)).collect(Collectors.toList()))
					.build();
	}
	
	public boolean isApprover(String employeeId) {
		return lstApproverInfo.stream()
				.anyMatch(a -> a.getApproverID().equals(employeeId));
	}
	
//	public boolean isRepresenter(String employeeId) {
//		return representerID != null && representerID.equals(employeeId);
//	}
	public static ApprovalFrame convert(int frameOrder, int confirmAtr, GeneralDate appDate, List<ApproverInfor> lstApproverInfo){
		return new  ApprovalFrame (frameOrder,
				EnumAdaptor.valueOf(confirmAtr, ConfirmPerson.class),
				 appDate, lstApproverInfo);
	}
	
	public boolean isApproved(ApprovalForm approvalForm) {
		if(approvalForm == ApprovalForm.SINGLE_APPROVED) {
			for(ApproverInfor approverInfor : lstApproverInfo) {
				if(approverInfor.isApproved()) {
					return true;
				}
			}
			return false;
		}
		for(ApproverInfor approverInfor : lstApproverInfo) {
			if(approverInfor.isNotApproved()) {
				return false;
			}
		}
		return true;
	}
}
