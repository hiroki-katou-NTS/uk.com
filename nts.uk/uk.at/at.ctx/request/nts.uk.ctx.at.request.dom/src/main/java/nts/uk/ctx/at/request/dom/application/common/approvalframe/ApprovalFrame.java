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
	private int dispOrder ;
	
	/** 承認者 */
	private String authorizerSID ;
	
	/** 代行者 */
	private String substituteSID ; 
	
	/** 承認区分 */
	private ApprovalATR approvalATR;

	/** 日付 */
	private String approvalDate;
	
	/** 理由 */
	private Reason reason;
	
	/** 確定区分 */
	private ConfirmATR confirmATR;
	

	public static ApprovalFrame createFromJavaType( String companyID , String phaseID , int dispOrder , String authorizerSID , String substituteSID ,
			int approvalATR ,  String approvalDate ,String reason , int confirmATR  ){
		return new ApprovalFrame (companyID , phaseID , dispOrder , authorizerSID , substituteSID  ,
									EnumAdaptor.valueOf(approvalATR , ApprovalATR.class) , 
									approvalDate ,
									new Reason (reason),
									EnumAdaptor.valueOf(confirmATR, ConfirmATR.class));
	}
	
	
	
}
