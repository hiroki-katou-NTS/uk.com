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
	
	
	public static ApproveAccepted createFromJavaType( String companyID , String phaseID , int dispOrder , 
			String approverSID){
		return new ApproveAccepted (companyID , 
				phaseID , 
				dispOrder , 
				approverSID);
	}	
}
