package nts.uk.ctx.at.request.dom.application.common.approvalframe;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.Reason;

/**
 * 
 * @author hieult
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalFrame extends DomainObject {
	
	/** 会社ID */
	private String companyID;
	
	/** 承認枠ID */
	@Setter
	private String frameID ;
	
	/** 順序 */
	private int dispOrder;
	
	/**
	 * list approverAccepted
	 */
	@Setter
	private List<ApproveAccepted> listApproveAccepted;

	public static ApprovalFrame createFromJavaType( String companyID , String frameID , int dispOrder , List<ApproveAccepted> listApproveAccepted){
		return new ApprovalFrame (companyID , frameID , dispOrder, listApproveAccepted);
	}
	 
}
