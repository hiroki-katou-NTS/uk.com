package nts.uk.ctx.at.request.dom.application.common.appapprovalphase;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
/**
 * 
 * @author hieult
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class AppApprovalPhase extends DomainObject {

	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;

	/** フェーズID */
	private String phaseID;

	/** 承認形態 */
	private ApprovalForm approvalForm;

	/** 順序 */
	private int dispOrder;

	/** 承認区分 */
	private ApprovalATR approvalATR;

	public static AppApprovalPhase createFromJavaType(String companyID , String appID , String phaseID , int approvalForm , int dispOrder , int approvalATR ) 
							{return new AppApprovalPhase (companyID , appID , phaseID ,
															EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
															dispOrder,
															EnumAdaptor.valueOf(approvalATR, ApprovalATR.class)) ; 
								}									
}
