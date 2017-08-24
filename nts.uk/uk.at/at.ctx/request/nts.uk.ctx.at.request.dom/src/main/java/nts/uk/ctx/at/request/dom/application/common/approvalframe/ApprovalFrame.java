package nts.uk.ctx.at.request.dom.application.common.approvalframe;


import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalATR;

/**
 * 
 * @author hieult
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ApprovalFrame extends DomainObject {
	
	/** 会社ID */
	private String companyID;
	
	/** フェーズID */
	private String phaseID ;
	
	/** 順序 */
	private int dispOrder;
	
	/** 承認者 */
	private String approverSID;
	
	/** 承認区分 */
	private ApprovalATR approvalATR;

	/** 確定区分 */
	private ConfirmATR confirmATR;
	

	public static ApprovalFrame createFromJavaType( String companyID , String phaseID , int dispOrder , String approverSID ,
			int approvalATR  , int confirmATR  ){
		return new ApprovalFrame (companyID , phaseID , dispOrder , approverSID ,
									EnumAdaptor.valueOf(approvalATR , ApprovalATR.class) ,
									EnumAdaptor.valueOf(confirmATR, ConfirmATR.class));
	}
	
	
	
}
