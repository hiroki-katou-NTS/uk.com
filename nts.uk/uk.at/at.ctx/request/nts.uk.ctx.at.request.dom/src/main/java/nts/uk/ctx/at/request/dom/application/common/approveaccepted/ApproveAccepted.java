package nts.uk.ctx.at.request.dom.application.common.approveaccepted;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.DomainObject;

@Value
@EqualsAndHashCode(callSuper= false)
public class ApproveAccepted extends DomainObject {

	/** 会社ID */
	private String companyID;
	/** フェーズID */
	private String phaseID ;
	
	/** 順序 */
	private int dispOrder ;
	
	/** 承認者 */
	private String approverSID ;
	
	/** 代行者 */
	private String representerSID ;
	
	/** 日付 */
	private String approvalDate;
	
	/** 理由 */
	private Reason reason;
	
	public static ApproveAccepted createFromJavaType( String companyID , String phaseID , int dispOrder , 
			String approverSID , String representerSID, String approvalDate ,String reason){
		return new ApproveAccepted (companyID , 
				phaseID , 
				dispOrder , 
				approverSID , 
				representerSID  ,
				approvalDate ,
				new Reason(reason));
	}	
}
