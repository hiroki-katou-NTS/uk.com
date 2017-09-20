package nts.uk.ctx.at.request.dom.application.common.appapprovalphase;

import java.util.List;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
/**
 * 
 * @author hieult
 *
 */
@Getter
@AllArgsConstructor
public class AppApprovalPhase extends DomainObject {

	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;

	/** フェーズID */
	private String phaseID;

	/** 承認形態 */
	@Setter
	private ApprovalForm approvalForm;

	/** 順序 */
	private int dispOrder;

	/** 承認区分 */
	@Setter
	private ApprovalAtr approvalATR;
	
	/**
	 * list Frame
	 */
	
	private List<ApprovalFrame> listFrame;
	/**
	 * list approverAccepted
	 */
	private List<ApproveAccepted> listApproveAccepted;

	public static AppApprovalPhase createFromJavaType(
			String companyID , String appID , String phaseID ,
			int approvalForm , int dispOrder , int approvalATR,List<ApprovalFrame> listFrame,List<ApproveAccepted> listApproveAccepted ){
		return new AppApprovalPhase (
				companyID , appID , phaseID ,
				EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
				dispOrder,EnumAdaptor.valueOf(approvalATR, ApprovalAtr.class),
				listFrame,listApproveAccepted) ;
}
	
	/**
	  * change value of reversionReason
	  * @param reversionReason
	  */
	public void changeApprovalATR(ApprovalAtr approvalATR) {
		this.approvalATR = approvalATR;
	}
}
