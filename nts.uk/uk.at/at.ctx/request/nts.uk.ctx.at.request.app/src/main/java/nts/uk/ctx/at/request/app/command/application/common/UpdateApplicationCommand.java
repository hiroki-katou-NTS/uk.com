package nts.uk.ctx.at.request.app.command.application.common;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationCommand {
	/**
	 * 申請ID
	 */
	private String applicationID;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr; 

	/**
	 * 入力日
	 */
	private BigDecimal inputDate; 
	
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
	private BigDecimal applicationDate; 
	
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
	private BigDecimal reflectPlanTime;
	
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
	private BigDecimal reflectPerTime;
	
	/**
	 * 予定反映状態
	 */
	private int reflectPerState;
	
	/**
	 * 実績強制反映
	 */
	private int reflectPerEnforce;
	
	public Application toDomain() {
		return Application.createFromJavaType(
				AppContexts.user().companyId(), 
				this.applicationID,  
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
				this.reflectPerEnforce);
	}
}
