package nts.uk.ctx.at.request.dom.application.common.approveaccepted;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper= false)
public class ApproveAccepted extends DomainObject {

	/** 会社ID */
	private String companyID;
	/** フェーズID */
	@Setter
	private String appAcceptedID ;
	
	/** 承認者 */
	@Setter
	private String approverSID ;
	
	/** 承認区分 */
	@Setter
	private ApprovalAtr approvalATR;

	/** 確定区分 */
	private ConfirmAtr confirmATR;
	
	/** 日付 */
	private GeneralDate approvalDate;
	
	/** 理由 */
	private Reason reason;
	
	/** 代行者 */
	@Setter
	private String representerSID;
	
	
	public static ApproveAccepted createFromJavaType(String companyID, String appAcceptedID, String approverSID,
			int approvalATR, int confirmATR, GeneralDate approvalDate, String reason, String representerSID){
		return new ApproveAccepted (
				companyID , 
				appAcceptedID , 
				approverSID,
				EnumAdaptor.valueOf(approvalATR, ApprovalAtr.class),
				EnumAdaptor.valueOf(confirmATR, ConfirmAtr.class),
				approvalDate,
				new Reason(reason),
				representerSID);
	}	
	

	/**
	  * change value of approvalATR
	  * @param approvalATR
	  */
	 public void changeApprovalATR(ApprovalAtr approvalATR) {
		 this.approvalATR = approvalATR;
	 }
	 
	 /**
	  * change value of approverSID
	  * @param approverSID
	  */
	 public void changeApproverSID(String  approverSID) {
		 this.approverSID = approverSID;
	 }
	
	 /**
	  * change value of representerSID
	  * @param representerSID
	  */
	 public void changeRepresenterSID(String  representerSID) {
		 this.representerSID = representerSID;
	 }
	 
	 /**
	  * change value of reason
	  * @param reason
	  */
	 public void changeReason(Reason reason) {
		 this.reason = reason;
	 }
	 
	 /**
	  * change value of approvalDate
	  * @param approvalDate
	  */
	 public void changeApprovalDate(GeneralDate approvalDate) {
		 this.approvalDate = approvalDate;
	 }
}
