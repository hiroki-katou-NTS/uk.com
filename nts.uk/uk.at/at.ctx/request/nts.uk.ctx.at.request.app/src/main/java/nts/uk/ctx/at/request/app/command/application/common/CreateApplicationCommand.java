package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationCommand {
	
	
	/**
	 * 申請ID
	 */
	String applicationID;
	/**
	 * 申請理由ID
	 */
	String appReasonID;
	/**
	 * 事前事後区分
	 */
	private int prePostAtr; 

	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate; 
	
	/**
	 * 入力者
	 */
	private String enteredPersonSID;
	
	/**
	 * 差戻し理由
	 */
	private String reversionReason; 
	
	/**
	 * 申請日
	 */
	private GeneralDate applicationDate; 
	
	/**
	 * 申請理由
	 */
	private String applicationReason;
	/**
	 * 申請種類
	 */
	private int applicationType;
	
	/**
	 * 申請者
	 */
	private String  applicantSID;
	
	/**
	 * 予定反映不可理由
	 */
	private int reflectPlanScheReason;
	
    /**
     * 予定反映日時
     */
	private GeneralDate reflectPlanTime;
	
	/**
	 * 予定反映状態
	 */
	private int reflectPlanState;
	
	/**
	 * 予定強制反映
	 */
	private int reflectPlanEnforce;
	
	/**
	 * 実績反映不可理由
	 */
	private int  reflectPerScheReason;
	
	/**
	 * 実績反映日時
	 */
	private GeneralDate reflectPerTime;
	
	/**
	 * 予定反映状態
	 */
	private int reflectPerState;
	
	/**
	 * 実績強制反映
	 */
	private int reflectPerEnforce;
	
	/**
	 * 申請終了日
	 */
	private GeneralDate startDate;
	
	/**
	 * 申請開始日
	 */
	private GeneralDate endDate;
	/**
	 * list phase
	 */
	private List<AppApprovalPhaseDto> listPhase;
	
	public Application toDomain() {
		return Application.createFromJavaType(
				AppContexts.user().companyId(),
				this.prePostAtr, 
				this.inputDate,  
				this.enteredPersonSID,  
				this.reversionReason,  
				this.applicationDate,  
				this.applicationReason,
				this.applicationType,  
				this.applicantSID,  
				this.reflectPlanScheReason,  
				this.reflectPlanTime,  
				this.reflectPlanState,  
				this.reflectPlanEnforce,  
				this.reflectPerScheReason,  
				this.reflectPerTime,  
				this.reflectPerState,  
				this.reflectPerEnforce,
				this.startDate,
				this.endDate,
				null);
	}
}
